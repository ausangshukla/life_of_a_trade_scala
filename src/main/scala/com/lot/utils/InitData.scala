package com.lot.utils

import com.lot.order.dao.OrderDao
import com.lot.order.model.Order
import com.lot.order.model.OrderType
import com.lot.exchange.Exchange

object InitData {

  def init() = {
    val dao = OrderDao
    dao.createTables()
    
    val o1 = Order(Some(1), Exchange.NASDAQ, OrderType.BUY, OrderType.LIMIT, 1, 100, "Tick", 100, 100, 102.3, "", "", "", None, None)
    val o2 = Order(Some(2), Exchange.NASDAQ, OrderType.SELL, OrderType.LIMIT, 1, 100, "Tick", 120, 120, 103.3, "", "", "", None, None)
    val o3 = Order(Some(3), Exchange.NYSE, OrderType.BUY, OrderType.LIMIT, 1, 100, "Tick", 150, 150, 98.3,  "", "", "", None, None)
    dao.save(o1)
    dao.save(o2)
    dao.save(o3)
  }
}