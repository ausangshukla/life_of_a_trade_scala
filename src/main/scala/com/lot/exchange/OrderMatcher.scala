package com.lot.exchange

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import com.lot.order.model.Order
import com.lot.order.model.Order
import com.lot.order.model.OrderType
import akka.actor.ActorLogging
import scala.collection.mutable.MutableList
import scala.collection.mutable.ListBuffer
import com.lot.order.dao.OrderDao
import com.lot.exchange.Message._
import akka.routing.FromConfig
import com.lot.trade.service.TradeGenerator
import com.lot.trade.service.TradeGenerator
import akka.actor.Props
import com.lot.trade.model.Trade
import org.joda.time.DateTime
import com.lot.trade.model.TradeMessage

/**
 * The matcher for a particular security
 * @security_id : The security_id for the security whose orders are handled by this matcher instance.
 * Orders of same security are handled serially, but of different securities are handled in parallel
 * @buys : The list of buy orders sorted by price / time priority
 * @sells : The list of sell orders sorted by price / time priority
 */
class OrderMatcher(security_id: Long, unfilledOM: UnfilledOrderManager) extends Actor with ActorLogging {

  /**
   * The actor that handles trade creation / enrichment
   */
  val tradeGenerator = context.actorOf(Props(classOf[TradeGenerator]), "tradeRouter")

  override def preStart = {
  }

  def receive = {
    case NewOrder(order, at)    => { handleNewOrder(order) }
    case ModifyOrder(order, at) => { handleNewOrder(order) }
    case CancelOrder(order, at) => { handleNewOrder(order) }
    case _                      => {}
  }

  /**
   * @order : The new order to be handled
   * @tailrec
   */
  def handleNewOrder(order: Order): Unit = {
    log.info(s"Handling unfilled_qty  = ${order.unfilled_qty} for order $order")

    /*
     * Find a match
     */
    val matchedOrder = unfilledOM.findMatch(order)
    matchedOrder match {
      case Some(mo) => {
        /*
         * Generate a trade 
         */
        generateTrade(order, mo)
        /*
         * Ensure the unfilled_qty is adjusted for both orders
         */
        unfilledOM.adjustOrders(order, mo)
        /*
         * Recursively call handleNewOrder until all the quantity is filled or the order is enqueued
         */
        if (order.unfilled_qty > 0) {
          handleNewOrder(order)
        }
      }
      case None => {
        /*
         * Nothing matches - so enqueue
         */
        unfilledOM.enqueOrder(order)
      }
    }
  }

  /**
   * Generates a trade and sends it off for booking
   */
  private def generateTrade(order: Order, matchedOrder: Order) = {
    /*
     * Send to trade booking actor
     */
    log.info(s"Generating trade for order $order with matchedOrder $matchedOrder")

    /*
     * We need to figure out the quantity to generate the trades for
     */
    val quantity = if (order.unfilled_qty > matchedOrder.unfilled_qty) matchedOrder.unfilled_qty else order.unfilled_qty

    /*
     * There are 2 trades generated for the 2 orders that got matched, 
     * one for each user whose order was matched
     */
    val trade = Trade(id = None, trade_date = new DateTime(), settlement_date = new DateTime(),
      security_id = order.security_id,
      quantity = quantity, price = 0.0,
      user_id = order.user_id, order_id = order.id.get,
      matched_order_id = matchedOrder.id.get, None, None)

    val counter_trade = Trade(id = None, trade_date = new DateTime(), settlement_date = new DateTime(),
      security_id = order.security_id,
      quantity = quantity, price = 0.0,
      user_id = matchedOrder.user_id, order_id = matchedOrder.id.get,
      matched_order_id = order.id.get, None, None)

    /*
     * Send it
     */
    tradeGenerator ! TradeMessage.New(trade)
    tradeGenerator ! TradeMessage.New(counter_trade)

  }

}