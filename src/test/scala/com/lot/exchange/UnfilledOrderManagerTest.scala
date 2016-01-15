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
import com.lot.generators.OrderFactory
import scala.Option
import com.lot.FailingTest

class UnfilledOrderManagerTest extends BaseTest {

  "An UnfilledOrderManager" should "when created, load the unfilled buys and sells given a security" in {

    /*
     * Push some Orders into the DB
     */

    val security_id = 10
    val o1 = OrderFactory.generate(security_id = 10, buy_sell = OrderType.BUY, unfilled_qty = 10)
    val o2 = OrderFactory.generate(security_id = 10, buy_sell = OrderType.SELL, unfilled_qty = 10)
    val o3 = OrderFactory.generate(security_id = 10, buy_sell = OrderType.BUY, unfilled_qty = 10)

    val fo1 = OrderDao.save(o1)
    val fo2 = OrderDao.save(o2)
    val fo3 = OrderDao.save(o3)

    val f = for {
      f1 <- fo1
      f2 <- fo2
      f3 <- fo3
    } yield (f1, f2, f3)

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

  "An UnfilledOrderManager" should "find no match for a market order, when there are no orders in the DB" in {

    val security_id = 10

    /*
     * Start the UOM
     */
    val uom = UnfilledOrderManager(security_id)
    /*
     * Generate a random order
     */
    val o1 = OrderFactory.generate(security_id = 10, quantity = 100, unfilled_qty = 100)
    val matchedOrder = uom.findMatch(o1)
    /*
     * Ensure UOM finds no match
     */
    matchedOrder shouldBe None
  }

  "An UnfilledOrderManager" should "find a match for a market order, when there is an exact opposite order in the DB" in {

    val security_id = 10

    /*
     * Generate a random order & save it
     */
    val o1 = OrderFactory.generate(security_id = 10, buy_sell = OrderType.SELL, order_type = OrderType.MARKET, quantity = 100, unfilled_qty = 100)
    val saved = wait(OrderDao.save(o1))

    /*
     * Start the UOM
     */
    val uom = UnfilledOrderManager(security_id)

    val opposite = o1.copy(buy_sell = OrderType.BUY)
    val matchedOrder = uom.findMatch(opposite)
    /*
     * Ensure UOM finds the match (we need to copy timestamps to ensure equality)
     */
    matchedOrder shouldBe Some(saved)
  }

  "An UnfilledOrderManager" should "find a match for a market order, when there is an partial filled opposite order in the DB" in {

    val security_id = 10

    /*
     * Generate a random order & save it
     */
    val o1 = OrderFactory.generate(security_id = 10, buy_sell = OrderType.SELL, order_type = OrderType.MARKET, quantity = 100, unfilled_qty = 10)
    val saved = wait(OrderDao.save(o1))

    /*
     * Start the UOM
     */
    val uom = UnfilledOrderManager(security_id)

    val opposite = o1.copy(buy_sell = OrderType.BUY)
    val matchedOrder = uom.findMatch(opposite)
    /*
     * Ensure UOM finds the match (we need to copy timestamps to ensure equality)
     */
    matchedOrder shouldBe Some(saved)
  }

  "An UnfilledOrderManager" should "NOT find a match for a market order, when there is an exact opposite but filled order in the DB" in {

    val security_id = 10

    /*
     * Generate a random order & save it
     */
    val o1 = OrderFactory.generate(security_id = 10, buy_sell = OrderType.SELL, order_type = OrderType.MARKET, unfilled_qty = 0)
    val saved = wait(OrderDao.save(o1))

    /*
     * Start the UOM
     */
    val uom = UnfilledOrderManager(security_id)

    val opposite = o1.copy(buy_sell = OrderType.BUY, unfilled_qty = o1.quantity)
    val matchedOrder = uom.findMatch(opposite)
    /*
     * Ensure UOM finds no match
     */
    matchedOrder shouldBe None
  }
  
  
  "An UnfilledOrderManager" should "adjust both orders and remove both orders when there is an exact match" taggedAs(FailingTest) in {

    val security_id = 10

    /*
     * Generate a random order & save it
     */
    val o1 = OrderFactory.generate(security_id = 10, buy_sell = OrderType.SELL, order_type = OrderType.MARKET, quantity = 100, unfilled_qty = 100)
    val s1 = wait(OrderDao.save(o1))
    val dbO1 = wait(OrderDao.get(s1.id.get)).get
    val o2 = o1.copy(buy_sell = OrderType.BUY)
    val s2 = wait(OrderDao.save(o2))
    val dbO2 = wait(OrderDao.get(s2.id.get)).get

    /*
     * Start the UOM
     */
    val uom = UnfilledOrderManager(security_id)
    uom.adjustOrders(dbO1, dbO2)
    /*
     * Ensure UOM finds no match
     */
    assert(uom.buys.length == 0)
    assert(uom.sells.length == 0)
  }

}