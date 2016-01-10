package com.lot.generators

import com.lot.order.model.Order
import com.lot.order.model.OrderType
import com.lot.exchange.Exchange
import org.scalacheck.Gen._

object OrderGen {
  
  def gen(exchange: String = oneOf(Exchange.NASDAQ, Exchange.NYSE).sample.get,
          buy_sell: String = oneOf(OrderType.BUY, OrderType.SELL).sample.get,
          order_type: String = oneOf(OrderType.MARKET, OrderType.LIMIT).sample.get, 
          user_id: Long = choose(1L, 50L).sample.get,
          security_id: Long,
          quantity: Double = choose(1, 10).sample.get * 1000.0,
          unfilled_qty: Double = 0.0,
          price: Double = choose(1, 10).sample.get * 100.0) = {

    Order(None, exchange, buy_sell, order_type, user_id, security_id, quantity, unfilled_qty, price, None, None)
  }
  
  
}