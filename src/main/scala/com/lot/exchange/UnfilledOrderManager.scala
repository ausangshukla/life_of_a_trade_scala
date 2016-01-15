package com.lot.exchange

import scala.collection.mutable.ListBuffer
import com.lot.order.model.Order
import com.lot.order.model.OrderType
import com.lot.order.dao.OrderDao
import com.typesafe.scalalogging.LazyLogging

/**
 * Manages all the unfilled orders for a given security
 */
class UnfilledOrderManager(val security_id: Long,
                           val buys: ListBuffer[Order],
                           val sells: ListBuffer[Order]) extends LazyLogging {

  /**
   * Finds an order that matches the given order
   */
  def findMatch(order: Order): Option[Order] = {

    checkOrder(order)

    order match {
      case Order(id, _, OrderType.BUY, OrderType.MARKET, user_id, _, _, _, _, _, _) =>
        sells.headOption match {
          case Some(head) => Some(head)
          case _          => None
        }
      case Order(id, _, OrderType.SELL, OrderType.MARKET, user_id, _, _, _, _, _, _) =>
        buys.headOption match {
          case Some(head) => Some(head)
          case _          => None
        }
      case Order(id, _, OrderType.BUY, OrderType.LIMIT, user_id, _, _, _, _, _, _) =>
        sells.headOption match {
          case Some(head) if head.price < order.price => Some(head)
          case _                                      => None
        }
      case Order(id, _, OrderType.SELL, OrderType.LIMIT, user_id, _, _, _, _, _, _) =>
        buys.headOption match {
          case Some(head) if head.price > order.price => Some(head)
          case _                                      => None
        }
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
      order.unfilled_qty = order.unfilled_qty - matchedOrder.unfilled_qty
      matchedOrder.unfilled_qty = 0

      /*
       * Remove the matchedOrder from the appropriate queue, so it does not match up with new incoming orders
       */
      dequeueOrder(matchedOrder)
      dequeueOrder(order)

    } else {
      /*
       * Fill the entire order. Mark the matched order as partially filled
       */
      logger.info(s"order id ${order.id} filled with ${order.unfilled_qty} from matchedOrder ${matchedOrder.id}")
      matchedOrder.unfilled_qty = matchedOrder.unfilled_qty - order.unfilled_qty
      order.unfilled_qty = 0

      /*
       * Remove the order from the appropriate queue, so it does not match up with new incoming orders
       */
      dequeueOrder(order)

    }

    /*
     * Save the state. TODO - ensure transactions
     */
    OrderDao.update(order)
    OrderDao.update(matchedOrder)

  }

  def dequeueOrder(order: Order) = {
    logger.info(s"De-queuing order $order")

    checkOrder(order)
    if (order.unfilled_qty == 0) {
      /*
     * Dequeue order which matches the id and ensure they are sorted
     */
      order match {
        case Order(id, _, OrderType.BUY, _, user_id, _, _, _, _, _, _) => {
          buys --= buys.filter(_.id == order.id)
          buys.sortWith((left, right) => { left.price > right.price && left.id.get < right.id.get })
        }
        case Order(id, _, OrderType.SELL, _, user_id, _, _, _, _, _, _) => {
          sells --= sells.filter(_.id == order.id)
          sells.sortWith((left, right) => { left.price < right.price && left.id.get < right.id.get })
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
      case Order(id, _, OrderType.BUY, _, user_id, _, _, _, _, _, _) => {
        buys += order
        buys.sortWith((left, right) => { left.price > right.price && left.id.get < right.id.get })
      }
      case Order(id, _, OrderType.SELL, _, user_id, _, _, _, _, _, _) => {
        sells += order
        sells.sortWith((left, right) => { left.price < right.price && left.id.get < right.id.get })
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
   * Creates an instance of the UnfilledOrderManager with buys and sells loaded from the DB
   */
  def apply(security_id: Long) = {
    val buys = new ListBuffer[Order]()
    val sells = new ListBuffer[Order]()

    /*
     * TODO - re-examine if there is a non blocking way of doing this!
     * Load the unfilled orders from the DB. Note we need to block here, else the OrderMatcher 
     * will not be in a state to match the incoming orders
     */
    buys ++= Await.result(OrderDao.unfilled_buys(security_id), 5 seconds)
    sells ++= Await.result(OrderDao.unfilled_sells(security_id), 5 seconds)

    new UnfilledOrderManager(security_id, buys, sells)
  }

}