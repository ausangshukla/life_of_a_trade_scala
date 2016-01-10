package com.lot.order

import com.lot.BaseTest
import scala.collection.mutable.Stack
import com.lot.order.model.Order
import com.lot.order.model.OrderType
import com.lot.order.dao.OrderDao
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._
import com.lot.generators.OrderFactory

class OrderDaoTest extends BaseTest {

  "OrderDao" should "save order correctly" in {

    val security_id = 10
    // Ensure one is already filled
    val o1 = OrderFactory.generate(security_id = 10)
    val fo1 = OrderDao.save(o1)

   val saved = wait(fo1)

    val o2 = wait(OrderDao.get(saved.id.get)).get
    // Compare the input and output, but the id, created_at and updated_at have to be cloned
    assert(o1.copy(id=saved.id).copyWithTS(o2) == o2)

  }
  
  "OrderDao" should "load the unfilled buys given a security" in {

    val security_id = 10
    // Ensure one is already filled
    val o1 = OrderFactory.generate(security_id = 10, buy_sell = OrderType.BUY, quantity=100, unfilled_qty=100.0)
    val o2 = OrderFactory.generate(security_id = 10, buy_sell = OrderType.SELL)
    val o3 = OrderFactory.generate(security_id = 10, buy_sell = OrderType.BUY)

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