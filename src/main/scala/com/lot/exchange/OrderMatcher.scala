package com.lot.exchange

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import com.lot.order.model.Order
import com.lot.order.model.Order
import com.lot.order.model.OrderType
import akka.actor.ActorLogging
import scala.collection.mutable.MutableList
import scala.collection.mutable.ListBuffer

/**
 * The matcher for a particular security
 * @security_id : The security_id for the security whose orders are handled by this matcher instance.
 * Orders of same security are handled serially, but of different securities are handled in parallel
 * @buys : The list of buy orders sorted by price / time priority
 * @sells : The list of sell orders sorted by price / time priority
 */
class OrderMatcher(security_id: Long, buys: ListBuffer[Order], sells: ListBuffer[Order]) extends Actor with ActorLogging {

  import com.lot.exchange.Message._

  def receive = {
    case NewOrder(order, at)    => { handleNewOrder(order) }
    case ModifyOrder(order, at) => { handleNewOrder(order) }
    case CancelOrder(order, at) => { handleNewOrder(order) }
    case _                      => {}
  }

  def handleNewOrder(order: Order) = {
    log.info(s"Handling order $order")
    
    /*
     * Find a match
     */
    val matchedOrder = findMatch(order)
    matchedOrder match {
      case Some(mo) => {
        /*
         * Generate a trade 
         */
        generateTrade(order, mo)
        /*
         * Ensure the unfilled_qty is adjusted for both orders
         */
        adjustOrders(order, mo)
      }
      case None => {
        /*
         * Nothing matches - so enqueue
         */
        enqueOrder(order)
      }
    }
  }
  
  /**
   * Modifies the unfilled quantity for the matched trades
   */
  private def adjustOrders(order: Order, matchedOrder: Order) = {
    if(order.unfilled_qty >= matchedOrder.unfilled_qty) {
      /*
       * Remove the matchedOrder from the appropriate queue, so it does not match up with new incoming orders
       */
      matchedOrder.buy_sell match {
        case OrderType.BUY => buys -= matchedOrder
        case OrderType.SELL => sells -= matchedOrder        
      }
      
      order.unfilled_qty = order.unfilled_qty - matchedOrder.unfilled_qty
      matchedOrder.unfilled_qty = 0
      
    } else {
      matchedOrder.unfilled_qty = matchedOrder.unfilled_qty - order.unfilled_qty
      order.unfilled_qty = 0
    }
  }
  
  /**
   * Generates a trade and sends it off for booking
   */
  private def generateTrade(order: Order, matchedOrder: Order) = {
    /*
     * Send to trade booking actor
     */
  }

  /**
   * Enqueues the order into the buys or sells and ensures they are sorted 
   */
  private def enqueOrder(order: Order) = {
    order match {
      case Order(id, _, OrderType.BUY, _, user_id, _, _, _, _, _, _) => {
        buys += order
      }
      case Order(id, _, OrderType.SELL, _, user_id, _, _, _, _, _, _) => {
        sells += order
      }
    }

  }

  /**
   * Finds an order that matches the given order
   */
  def findMatch(order: Order) : Option[Order] = {
    order match {
      case Order(id, _, OrderType.BUY, OrderType.MARKET, user_id, _, _, _, _, _, _) =>
        sells.headOption match {
          case Some(head) => Some(head)
          case None       => None
        }
      case Order(id, _, OrderType.SELL, OrderType.MARKET, user_id, _, _, _, _, _, _) =>
        buys.headOption match {
          case Some(head) => Some(head)
          case None       => None
        }
      case Order(id, _, OrderType.BUY, OrderType.LIMIT, user_id, _, _, _, _, _, _) =>
        sells.headOption match {
          case Some(head) if head.price < order.price => Some(head)
          case None                                   => None
        }
      case Order(id, _, OrderType.SELL, OrderType.LIMIT, user_id, _, _, _, _, _, _) =>
        buys.headOption match {
          case Some(head) if head.price > order.price => Some(head)
          case None                                   => None
        }
    }
  }

}