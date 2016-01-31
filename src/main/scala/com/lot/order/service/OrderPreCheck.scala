package com.lot.order.service

import akka.actor.Actor
import akka.actor.ActorLogging
import com.lot.exchange.Message.NewOrder
import com.lot.exchange.Message.ModifyOrder
import com.lot.exchange.Message.CancelOrder
import com.lot.order.model.Order
import scala.concurrent.duration._
import akka.util.Timeout
import scala.concurrent.Await
import akka.actor.ActorRef
import com.lot.security.model.PriceMessage
import com.lot.security.model.Price
import akka.pattern.ask
import scala.concurrent.ExecutionContext.Implicits.global
import com.lot.user.dao.UserDao
import com.lot.user.service.UserManager.BlockAmount
import com.lot.utils.GenericMessages.Success
import com.lot.utils.GenericMessages.Failure
import com.lot.exchange.Exchange
import org.joda.time.DateTime
import com.lot.order.dao.OrderDao
import akka.actor.ActorSystem
import com.lot.utils.ConfigurationModuleImpl
import akka.routing.FromConfig
import akka.actor.Props
import com.lot.user.service.UserManager
import com.lot.trade.service.SecurityManager

/**
 * This is where all the checks are made before the order is sent to the exchange
 * 1. User has sufficient account_balance
 *
 */
class OrderPreCheck(securityManager: ActorRef, userManager: ActorRef) extends Actor with ActorLogging {

  def receive = {
    case NewOrder(order, at)    => { handleNewOrder(order) }
    case CancelOrder(order, at) => { handleNewOrder(order) }
    case _                      => {}
  }

  /**
   * Ensure that the amount required for the order is blocked in the user account
   */
  def handleNewOrder(order: Order) = {
    /*
     * Load the price of the security from the securityManager
     */
    implicit val timeout = Timeout(15 second)
    /*
     * Ask for the price
     */
    val futurePrice = securityManager ? PriceMessage.Get(Price(order.security_id, 0.0))
    futurePrice.map { priceMsg =>
      /*
       * We get a PriceMessage.Value which has a Price in it
       */
      val price = priceMsg.asInstanceOf[PriceMessage.Value].price
      val amount = price.price * order.quantity

      val result = userManager ? BlockAmount(order.user_id, amount)
      result.map { reply =>
        log.debug(s"UserManager responded with $reply")
        reply match {
          case Success => {
            /*
             * Send it to the exchange for execution
             */
            Exchange.exchanges.get(order.exchange).map { exchange =>
              exchange ! NewOrder(order, new DateTime())
            }
            
            OrderDao.update(order.copy(pre_trade_check_status="BlockAmountSuccess")).map { count =>
              log.debug(s"Order updated $count")
            }
          }
          case Failure => {
            /*
             * Mark order as BlockAmount failed
             */
            OrderDao.update(order.copy(pre_trade_check_status="BlockAmountFailed")).map { count =>
              log.debug(s"Order updated $count")
            }
          }
        }
      }

    }
  }

}

/**
 * Factory for creating OrderPreCheck actors with references to the securityManager and userManager
 */
object OrderPreCheck extends ConfigurationModuleImpl {
  
  val system = ActorSystem("lot-om", config)
  /*
   * The actor that handles price update and broadcasting of prices
   */
  val securityManager = system.actorOf(FromConfig.props(Props[SecurityManager]), "securityManagerRouter")

  val userManager = system.actorOf(Props(classOf[UserManager]), "UserManager")

  val orderPreCheckRouter = system.actorOf(Props(classOf[OrderPreCheck], securityManager, userManager), "orderPreCheckRouter")
  /**
   * Factory method
   */
  def apply() = {
    orderPreCheckRouter
  }
}
