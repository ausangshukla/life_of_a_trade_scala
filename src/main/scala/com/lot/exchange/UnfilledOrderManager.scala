package com.lot.exchange

import scala.collection.mutable.ListBuffer
import com.lot.order.model.Order
import com.lot.order.model.OrderType
import com.lot.order.dao.OrderDao
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.duration.Duration
import scala.concurrent.Await


/**
 * Manages all the unfilled orders for a given security
 */
class UnfilledOrderManager(val security_id: Long,
                           val buys: ListBuffer[Order],
                           val sells: ListBuffer[Order]) extends LazyLogging {
  
  /**
   * Ensures sells are sorted by price and time with market orders on top of limit order. 
   * Note id is used instead of time for performance
   */
  private def sortSells(left: Order, right: Order) = { left.order_type > right.order_type && left.price <= right.price && left.id.get < right.id.get }
  /**
   * Ensures buys are sorted by price and time. Note id is used instead of time for performance
   */
  private def sortBuys(left: Order, right: Order) = { left.order_type > right.order_type && left.price >= right.price && left.id.get < right.id.get }
  
  /*
   * Ensure the orders from the DB are sorted properly
   */
  buys.sortWith(sortBuys)
  sells.sortWith(sortSells)
  
  logger.debug(s"buys = $buys sells = $sells")
  /**
   * Finds an order that matches the given order
   */
  def findMatch(order: Order): Option[Order] = {

    checkOrder(order)
    /*
     * Sometimes the order gets loaded from the DB when the OM starts
     * In which case the order should be dequeued before we proceed
     */
    dequeueOrder(order, true)
    
    logger.debug(s"findMatch: $order buys: $buys sells: $sells")

    if (order.unfilled_qty > 0) {
      order match {
        case Order(id, _, OrderType.BUY, OrderType.MARKET, user_id, _, _, _, _, _, _, _, _, _) =>
          sells.headOption match {
            case Some(head) => sells.headOption
            case _          => None
          }
        case Order(id, _, OrderType.SELL, OrderType.MARKET, user_id, _, _, _, _, _, _, _, _, _) =>
          buys.headOption match {
            case Some(head) => buys.headOption
            case _          => None
          }
        case Order(id, _, OrderType.BUY, OrderType.LIMIT, user_id, _, _, _, _, _, _, _, _, _) =>
          sells.headOption match {
            case Some(head) if head.order_type == OrderType.LIMIT && head.price <= order.price => sells.headOption
            case Some(head) if head.order_type == OrderType.MARKET => sells.headOption
            case _ => None
          }
        case Order(id, _, OrderType.SELL, OrderType.LIMIT, user_id, _, _, _, _, _, _, _, _, _) =>
          buys.headOption match {
            case Some(head) if head.order_type == OrderType.LIMIT && head.price >= order.price => buys.headOption
            case Some(head) if head.order_type == OrderType.MARKET => buys.headOption
            case _ => None
          }
      }
    } else {
      logger.debug(s"findMatch: Order with unfilled_qty = 0 not matched")
      None
    }

  }

  /**
   * Modifies the unfilled quantity for the matched trades
   */
  def adjustOrders(order: Order, matchedOrder: Order) = {

    checkOrder(order)
    checkOrder(matchedOrder)

    if (order.unfilled_qty >= matchedOrder.unfilled_qty) {

      /*
       * Fill the entire matchedOrder. Mark the order as partially filled
       */
      logger.info(s"order id ${order.id} filled with ${matchedOrder.unfilled_qty} from matchedOrder ${matchedOrder.id}")
      order.setUnfilledQty(order.unfilled_qty - matchedOrder.unfilled_qty)
      matchedOrder.setUnfilledQty(0)
      /*
       * Remove the matchedOrder from the appropriate queue, so it does not match up with new incoming orders
       */
      dequeueOrder(matchedOrder)
      if (order.unfilled_qty == 0) {
        dequeueOrder(order)
      }

    } else {
      /*
       * Fill the entire order. Mark the matched order as partially filled
       */
      logger.info(s"order id ${order.id} filled with ${order.unfilled_qty} from matchedOrder ${matchedOrder.id}")
      matchedOrder.setUnfilledQty(matchedOrder.unfilled_qty - order.unfilled_qty)
      order.setUnfilledQty(0)

      /*
       * Remove the order from the appropriate queue, so it does not match up with new incoming orders
       */
      dequeueOrder(order)
      if (matchedOrder.unfilled_qty == 0) {
        dequeueOrder(matchedOrder)
      }

    }

    /*
     * Save the state
     */
    Await.result(OrderDao.updateMatchStatus(order, matchedOrder), Duration.Inf)
    logger.debug(s"buys = $buys \nsells=$sells")
  }

  def dequeueOrder(order: Order, force: Boolean = false) = {
    logger.info(s"De-queuing order $order")

    checkOrder(order)
    if (order.unfilled_qty == 0 || force) {
    /*
     * Dequeue order which matches the id and ensure they are sorted
     */
      order match {
        case Order(id, _, OrderType.BUY, _, user_id, _, _, _, _, _, _, _, _, _) => {
          val filtered = buys.filter(_.id == order.id)
          buys --= filtered
          buys.sortWith(sortBuys)
          logger.debug(s"filtered out $filtered")
        }
        case Order(id, _, OrderType.SELL, _, user_id, _, _, _, _, _, _, _, _, _) => {
          val filtered = sells.filter(_.id == order.id)
          sells --= filtered
          sells.sortWith(sortSells)
          logger.debug(s"filtered out $filtered")
        }
      }
      true
    } else {
      logger.warn(s"De-queuing order $order failed. This order cannot be dequeued")
      false
    }

  }
  /**
   * Enqueues the order into the buys or sells and ensures they are sorted
   */
  def enqueOrder(order: Order) = {
    logger.info(s"En-queuing order $order")

    checkOrder(order)
    /*
     * Enqueue order and ensure they are sorted
     */
    order match {
      case Order(id, _, OrderType.BUY, _, user_id, _, _, _, _, _, _, _, _, _) => {
        buys += order
        buys.sortWith(sortBuys)
      }
      case Order(id, _, OrderType.SELL, _, user_id, _, _, _, _, _, _, _, _, _) => {
        sells += order
        sells.sortWith(sortSells)
      }
    }

  }

  /**
   * Defensive programming - change later.
   * Check if this order can be handled by this UOM
   */
  def checkOrder(order: Order) = {

    if (order.security_id != security_id) {
      throw new IllegalArgumentException(s"Cannot handle $order with UnfilledOrderManager of security_id = $security_id")
    }

  }

}

import scala.concurrent.Await
import scala.concurrent.duration._

object UnfilledOrderManager {

  /**
   * Creates an instance of the UnfilledOrderManager with buys and sells lists
   */
  def apply(security_id: Long) = {
    val buys = new ListBuffer[Order]()
    val sells = new ListBuffer[Order]()

    new UnfilledOrderManager(security_id, buys, sells)
  }

}