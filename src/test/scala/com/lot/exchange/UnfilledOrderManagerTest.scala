package com.lot.exchange

import org.scalatest._
import com.lot.test.BaseTest
import scala.collection.mutable.Stack
import com.lot.order.model.Order
import com.lot.order.model.OrderType
import com.lot.order.dao.OrderDao
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._
import com.lot.generators.OrderFactory
import scala.Option
import com.lot.test.FailingTest
import com.lot.test.NewTest

class UnfilledOrderManagerTest extends BaseTest {

  "An UnfilledOrderManager" should "when created, load the unfilled buys and sells given a security" in {

    /*
     * Push some Orders into the DB
     */

    val security_id = 10
    val o1 = OrderFactory.generate(security_id = 10, buy_sell = OrderType.BUY, unfilled_qty = 10)
    val o2 = OrderFactory.generate(security_id = 10, buy_sell = OrderType.SELL, unfilled_qty = 10)
    val o3 = OrderFactory.generate(security_id = 10, buy_sell = OrderType.BUY, unfilled_qty = 10)

    val fo1 = wait(OrderDao.save(o1))
    val fo2 = wait(OrderDao.save(o2))
    val fo3 = wait(OrderDao.save(o3))

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

  "An UnfilledOrderManager" should "match the oldest market order sell to ensure time priority with an incoming buy" in {

    /*
     * Push some Orders into the DB
     */

    val security_id = 10
    val o0 = OrderFactory.generate(security_id = 10, order_type = OrderType.LIMIT, buy_sell = OrderType.SELL, unfilled_qty = 10)
    val o1 = OrderFactory.generate(security_id = 10, order_type = OrderType.MARKET, buy_sell = OrderType.SELL, unfilled_qty = 10)
    val o2 = OrderFactory.generate(security_id = 10, order_type = OrderType.MARKET, buy_sell = OrderType.SELL, unfilled_qty = 10)
    val o3 = OrderFactory.generate(security_id = 10, order_type = OrderType.MARKET, buy_sell = OrderType.SELL, unfilled_qty = 10)

    val fo0 = wait(OrderDao.save(o0))
    val fo1 = wait(OrderDao.save(o1))
    val fo2 = wait(OrderDao.save(o2))
    val fo3 = wait(OrderDao.save(o3))

    /*
     * Start the UOM
     */
    val uom = UnfilledOrderManager(security_id)
    

    val o4 = OrderFactory.generate(security_id = 10, order_type = OrderType.MARKET, buy_sell = OrderType.BUY, unfilled_qty = 10)
    val matchedOrder = uom.findMatch(o4)
    /*
     * Ensure UOM matches the oldest order
     */
    assert(matchedOrder.get.id == fo1.id)
  }
 
   "An UnfilledOrderManager" should "match the lowest priced sell limit order to ensure price priority with an incoming buy" in {

    /*
     * Push some Orders into the DB
     */

    val security_id = 10
    val o0 = OrderFactory.generate(security_id = 10, order_type = OrderType.LIMIT, buy_sell = OrderType.SELL, unfilled_qty = 10, price=104)
    val o1 = OrderFactory.generate(security_id = 10, order_type = OrderType.LIMIT, buy_sell = OrderType.SELL, unfilled_qty = 10, price=103)
    val o2 = OrderFactory.generate(security_id = 10, order_type = OrderType.LIMIT, buy_sell = OrderType.SELL, unfilled_qty = 10, price=102)
    val o3 = OrderFactory.generate(security_id = 10, order_type = OrderType.LIMIT, buy_sell = OrderType.SELL, unfilled_qty = 10, price=101)

    val fo0 = wait(OrderDao.save(o0))
    val fo1 = wait(OrderDao.save(o1))
    val fo2 = wait(OrderDao.save(o2))
    val fo3 = wait(OrderDao.save(o3))

    /*
     * Start the UOM
     */
    val uom = UnfilledOrderManager(security_id)
    

    val o4 = OrderFactory.generate(security_id = 10, order_type = OrderType.MARKET, buy_sell = OrderType.BUY, unfilled_qty = 10)
    val matchedOrder = uom.findMatch(o4)
    /*
     * Ensure UOM matches the oldest order
     */
    assert(matchedOrder.get.id == fo3.id)
  }
   
  "An UnfilledOrderManager" should "match the oldest market order buy to ensure time priority with an incoming sell" in {

    /*
     * Push some Orders into the DB
     */

    val security_id = 10
    val o0 = OrderFactory.generate(security_id = 10, order_type = OrderType.LIMIT, buy_sell = OrderType.BUY, unfilled_qty = 10)
    val o1 = OrderFactory.generate(security_id = 10, order_type = OrderType.MARKET, buy_sell = OrderType.BUY, unfilled_qty = 10)
    val o2 = OrderFactory.generate(security_id = 10, order_type = OrderType.MARKET, buy_sell = OrderType.BUY, unfilled_qty = 10)
    val o3 = OrderFactory.generate(security_id = 10, order_type = OrderType.MARKET, buy_sell = OrderType.BUY, unfilled_qty = 10)

    val fo0 = wait(OrderDao.save(o0))
    val fo1 = wait(OrderDao.save(o1))
    val fo2 = wait(OrderDao.save(o2))
    val fo3 = wait(OrderDao.save(o3))

    /*
     * Start the UOM
     */
    val uom = UnfilledOrderManager(security_id)
    

    val o4 = OrderFactory.generate(security_id = 10, order_type = OrderType.MARKET, buy_sell = OrderType.SELL, unfilled_qty = 10)
    val matchedOrder = uom.findMatch(o4)
    /*
     * Ensure UOM matches the oldest order
     */
    assert(matchedOrder.get.id == fo1.id)
  }
  
   "An UnfilledOrderManager" should "match the highest priced buy limit order to ensure price priority with an incoming sell" in {

    /*
     * Push some Orders into the DB
     */

    val security_id = 10
    val o0 = OrderFactory.generate(security_id = 10, order_type = OrderType.LIMIT, buy_sell = OrderType.BUY, unfilled_qty = 10, price = 100)
    val o1 = OrderFactory.generate(security_id = 10, order_type = OrderType.LIMIT, buy_sell = OrderType.BUY, unfilled_qty = 10, price = 101)
    val o2 = OrderFactory.generate(security_id = 10, order_type = OrderType.LIMIT, buy_sell = OrderType.BUY, unfilled_qty = 10, price = 102)
    val o3 = OrderFactory.generate(security_id = 10, order_type = OrderType.LIMIT, buy_sell = OrderType.BUY, unfilled_qty = 10, price = 101)

    val fo0 = wait(OrderDao.save(o0))
    val fo1 = wait(OrderDao.save(o1))
    val fo2 = wait(OrderDao.save(o2))
    val fo3 = wait(OrderDao.save(o3))

    /*
     * Start the UOM
     */
    val uom = UnfilledOrderManager(security_id)
    

    val o4 = OrderFactory.generate(security_id = 10, order_type = OrderType.MARKET, buy_sell = OrderType.SELL, unfilled_qty = 10)
    val matchedOrder = uom.findMatch(o4)
    /*
     * Ensure UOM matches the oldest order
     */
    assert(matchedOrder.get.id == fo2.id)
  }

}