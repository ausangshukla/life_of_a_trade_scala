package com.lot

import com.lot.order.model.Order
import com.lot.exchange.Exchange
import com.lot.order.model.OrderType

object Scratch {
	
	def m(ref:Option[Any]) = ref match {
		case Some(Order) => "Got class Order"
		case Some(o:Order) => s"Got $o"
	}
	
	m(Some(Order))
	val o1 = Order(Some(1), Exchange.NASDAQ, OrderType.SELL, OrderType.LIMIT, 1, 100, "Tick", 100, 100, 102.3, "", "", "", None, None)
	m(Some(o1))

}