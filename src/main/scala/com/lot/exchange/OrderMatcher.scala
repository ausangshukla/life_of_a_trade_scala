package com.lot.exchange

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import com.lot.order.model.Order
import com.lot.order.model.Order
import com.lot.order.model.OrderType
import akka.actor.ActorLogging

class OrderMatcher(security_id: Long) extends Actor with ActorLogging {

  import com.lot.exchange.Message._

  def receive = {
    case NewOrder(order, at)    => {handleNewOrder(order)}
    case ModifyOrder(order, at) => {handleNewOrder(order)}
    case CancelOrder(order, at) => {handleNewOrder(order)}
    case _                      => {}
  }

  def handleNewOrder(order: Order) = {
    log.info(s"Handling order $order")
    order match {
      case Order(id, _, OrderType.BUY, OrderType.MARKET, user_id, _, _, _, _, _) =>
      case Order(id, _, OrderType.SELL, OrderType.MARKET, user_id, _, _, _, _, _) =>
      case Order(id, _, OrderType.BUY, OrderType.LIMIT, user_id, _, _, _, _, _) =>
      case Order(id, _, OrderType.SELL, OrderType.LIMIT, user_id, _, _, _, _, _) =>
    }
  }

}