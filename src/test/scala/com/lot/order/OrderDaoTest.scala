package com.lot.order

import com.lot.BaseTest
import scala.collection.mutable.Stack
import com.lot.order.model.Order
import com.lot.order.model.OrderType
import com.lot.order.dao.OrderDao
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._
import com.lot.generators.OrderGen

class OrderDaoTest extends BaseTest {

  "OrderDao" should "load the unfilled buys given a security" in {

    val security_id = 10
    // Ensure one is already filled
    val o1 = OrderGen.gen(security_id = 10, buy_sell = OrderType.BUY, quantity=100, unfilled_qty=100)
    val o2 = OrderGen.gen(security_id = 10, buy_sell = OrderType.SELL)
    val o3 = OrderGen.gen(security_id = 10, buy_sell = OrderType.BUY)

    val fo1 = OrderDao.save(o1)
    val fo2 = OrderDao.save(o2)
    val fo3 = OrderDao.save(o3)

   val f = for {
      f1 <- fo1
      f2 <- fo2
      f3 <- fo3
    } yield (f1,f2,f3)

    val orderList = wait(OrderDao.unfilled_buys(security_id))
    println(s" unfilled_buys = $orderList")
    assert(orderList.length == 1)

  }

  
}