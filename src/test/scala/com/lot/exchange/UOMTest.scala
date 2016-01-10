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
import scala.Option

class UnfilledOrderManagerTest extends BaseTest {

 
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

    val f = for {
      f1 <- fo1
      f2 <- fo2
      f3 <- fo3
    } yield (f1,f2,f3)

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

  
  "An UnfilledOrderManager" should "should find no match when there are no orders in the DB" in {

    val security_id = 10

    /*
     * Start the UOM
     */
    val uom = UnfilledOrderManager(security_id)
    /*
     * Generate a random order
     */
    val o1 = OrderGen.gen(security_id = 10)
    val matchedOrder = uom.findMatch(o1)
    /*
     * Ensure UOM finds no match
     */
    matchedOrder shouldBe None
  }

}