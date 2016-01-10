package com.lot.exchange

import org.scalatest._
import com.lot.BaseTest
import scala.collection.mutable.Stack
import com.lot.order.model.Order
import com.lot.order.model.OrderType
import com.lot.order.dao.OrderDao
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._
import com.lot.generators.OrderGen

class UnfilledOrderManagerTest extends BaseTest {

  "OrderDao" should "load the unfilled buys given a security" in {

    val security_id = 10
    // Ensure one is already filled
    val o1 = OrderGen.gen(security_id = 10, buy_sell = OrderType.BUY, quantity=100, unfilled_qty=100)
    val o2 = OrderGen.gen(security_id = 10, buy_sell = OrderType.SELL)
    val o3 = OrderGen.gen(security_id = 10, buy_sell = OrderType.BUY)

    val fo1 = OrderDao.save(o1)
    val fo2 = OrderDao.save(o2)
    val fo3 = OrderDao.save(o3)

    wait(List(fo1, fo2, fo3))

    val orderList = wait(OrderDao.unfilled_buys(security_id))
    println(s" unfilled_buys = $orderList")
    assert(orderList.length == 1)

  }

  "An UnfilledOrderManager" should "load the unfilled buys and sells given a security" in {

    /*
     * Push some Orders into the DB
     */

    val security_id = 10
    val o1 = OrderGen.gen(security_id = 10, buy_sell = OrderType.BUY)
    val o2 = OrderGen.gen(security_id = 10, buy_sell = OrderType.SELL)
    val o3 = OrderGen.gen(security_id = 10, buy_sell = OrderType.BUY)

    val fo1 = OrderDao.save(o1)
    val fo2 = OrderDao.save(o2)
    val fo3 = OrderDao.save(o3)

    wait(List(fo1, fo2, fo3))

    /*
     * Start the UOM
     */
    val uom = UnfilledOrderManager(security_id)

    /*
     * Ensure UOM has the correct number of buys and sells
     */
    assert(uom.buys.length == 2)
    assert(uom.sells.length == 1)
  }

}