package com.lot.trade.service

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import com.lot.trade.model.Trade
import akka.actor.ActorLogging
import com.lot.trade.dao.TradeDao
import com.lot.trade.model.TradeMessage
import com.lot.trade.model.TradeState
import com.lot.security.model.Price
import com.lot.security.dao.SecurityDao
import com.lot.security.model.PriceMessage
import scala.concurrent.ExecutionContext.Implicits.global
import com.lot.utils.ConfigurationModuleImpl
import akka.actor.ActorSystem
import akka.actor.Props
import akka.routing.FromConfig

class SecurityManager extends Actor with ActorLogging {

  override def preStart = {

  }

  def receive = {
    case PriceMessage.Set(p) => { handleSetPrice(p) }
    case PriceMessage.Get(p) => { handleGetPrice(p) }
    case msg      => { log.error(s"Received unknown msg $msg") }
  }

  /**
   * Update the price in the DB for this security
   */
  private def handleSetPrice(price: Price) = {
    log.info(s"handleSetPrice: $price")
    SecurityDao.updatePrice(price.security_id, price.price)
  }
  
  /**
   * Return the price in the DB for this security to the sender of this message
   */
  private def handleGetPrice(price: Price) = {
    log.info(s"handleGetPrice: $price")
    val recipient = sender
    SecurityDao.get(price.security_id).map { dbSec =>
      dbSec match {
        case Some(sec) => recipient ! PriceMessage.Value(price.copy(price=sec.price))
        case None =>
      }
    }
  }

}

object SecurityManager extends ConfigurationModuleImpl {

  val system = ActorSystem("lot-om", config)

  val securityManager = system.actorOf(FromConfig.props(Props[SecurityManager]), "securityManagerRouter")

}