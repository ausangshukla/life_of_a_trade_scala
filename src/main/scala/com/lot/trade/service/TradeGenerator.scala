package com.lot.trade.service

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import com.lot.trade.model.Trade
import akka.actor.ActorLogging
import com.lot.trade.dao.TradeDao
import com.lot.trade.model.TradeMessage
import com.lot.trade.model.TradeState
import akka.actor.ActorRef
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.immutable.HashMap
import com.typesafe.config.ConfigValue
import com.lot.utils.ConfigurationModuleImpl
import com.typesafe.config.Config
import com.lot.user.service.UserManager
import com.lot.user.service.UserManagerMessages.DeductBlockedAmount
import akka.pattern.ask
import com.lot.user.service.UserManagerMessages.DeductBlockedAmount

class TradeGenerator(positionManager: ActorRef) extends Actor with ConfigurationModuleImpl with ActorLogging {

  var exchangeProps = new HashMap[String, Config]()
  /**
   * Actor that handles blocking of the amounts to be charged
   */
  val userManager = UserManager.userManager

  override def preStart = {

    /*
   * Create all the exchanges specified in the config
   */
    val exchangeConfig = config.getConfig("exchanges")
    val venues = exchangeConfig.getStringList("venues").listIterator()
    while (venues.hasNext()) {
      val venue = venues.next()
      exchangeProps += (venue -> exchangeConfig.getConfig(venue))
    }
  }

  def receive = {
    case TradeMessage.New(trade)    => { handleNewTrade(trade) }
    case TradeMessage.Cancel(trade) => { handleCancelTrade(trade) }
    case msg                        => { log.error(s"Received unknown msg $msg") }
  }

  def handleNewTrade(trade: Trade) = {
    log.info(s"handleNewTrade: $trade")

    exchangeProps.get(trade.exchange).map { props =>
      /*
       * Compute the various charges based on the exchange configs
       * TODO - improve this to reflect true calcs
       */
      val percent_charges = props.asInstanceOf[Config].getDouble("percent_charges")
      val tax_rate = props.asInstanceOf[Config].getDouble("tax_rate")
      val value = trade.quantity * trade.price
      val commissions = (percent_charges / 100.0) * value
      val taxes = (value + commissions) * tax_rate / 100
      val total_amount = value + commissions + taxes
      log.debug(s"percent_charges = $percent_charges , tax_rate = $tax_rate , value = $value , commissions = $commissions , taxes = $taxes")
      
      val tradeWithAmounts = trade.copy(commissions = commissions, taxes = taxes, total_amount = total_amount)
      /*
       * Deduct the total_amount from the users blocked amount
       */
      userManager ! DeductBlockedAmount(tradeWithAmounts.user_id, trade.order_id, tradeWithAmounts.total_amount)
      /*
       * Save the trade
       */
      TradeDao.save(tradeWithAmounts).map { t =>
        /*
         * Update the position
         */
        positionManager ! TradeMessage.New(t)
      }
    }

  }

  def handleCancelTrade(trade: Trade) = {
    log.info(s"handleCancelTrade: $trade")
    TradeDao.update(trade.copy(state = TradeState.CANCELLED)).map { rowCount =>
      rowCount match {
        case 1 => positionManager ! TradeMessage.Cancel(trade)
        case 0 => log.error(s"Manual intervention required, Could not cancel trade $trade")
      }
    }
  }
}