package com.lot.order.test

import com.lot.test.BaseTest
import scala.collection.mutable.Stack
import com.lot.order.model.OrderType
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._
import com.lot.generators.OrderFactory
import com.lot.order.model.Order
import com.lot.order.dao.OrderDao
import scala.collection.mutable.ListBuffer
import com.lot.generators.OrderFactory
import com.lot.test.FailingTest
import com.lot.security.dao.SecurityDao


class OrderDaoTest extends BaseTest {

  val sec = Await.result(SecurityDao.first(), Duration.Inf)
  val security_id = sec.get.id.get
  

  "OrderDao" should "load the unfilled buys given a security" in {

    
    // Ensure one is already filled
    val o1 = OrderFactory.generate(security_id = security_id, buy_sell = OrderType.BUY, quantity = 100, unfilled_qty = 100.0)
    val o2 = OrderFactory.generate(security_id = security_id, buy_sell = OrderType.SELL)
    val o3 = OrderFactory.generate(security_id = security_id, buy_sell = OrderType.BUY)

    val fo1 = OrderDao.save(o1)
    val fo2 = OrderDao.save(o2)
    val fo3 = OrderDao.save(o3)

    val f = for {
      f1 <- fo1
      f2 <- fo2
      f3 <- fo3
    } yield (f1, f2, f3)

    val orderList = wait(OrderDao.unfilled_buys(security_id))
    println(s" unfilled_buys = $orderList")
    assert(orderList.length == 1)

  }


  "OrderDao" should "save Order correctly" in {

    /*
     * Create an entity
     */
    val order = OrderFactory.generate(security_id=security_id)
    /*
     * Save it
     */
    val fSaved = OrderDao.save(order)
    val saved = wait(fSaved)
    /*
     * Get it back from the DB
     */
    val dbOrder = wait(OrderDao.get(saved.id.get)).get
    /*
     * They should be the same
     */
    assert(saved == dbOrder)

  }

  "OrderDao" should "list Orders correctly" in   {

    /*
     * Create some entities and save
     */
    val orderList = new ListBuffer[Order]
    for (i <- 1 to 10) {
      val b = OrderFactory.generate(security_id=security_id)
      orderList += wait(OrderDao.save(b))
    }

    //println(orderList)
    
    /*
     * Get it back from the DB
     */
    val dbList = wait(OrderDao.listOrders)
    
    // println(dbList)
    
    /*
     * They should be the same
     */
    assert(dbList.length == orderList.length)
    val mixed = orderList zip dbList
    for {
      (order, dbOrder) <- mixed
      x = println(s"comparing order = $order with dbOrder = $dbOrder")
    } yield (assert(order == dbOrder))

  }

  "OrderDao" should "update Order correctly" in {

    /*
     * Create an entity
     */
    val order = OrderFactory.generate(security_id=security_id)
    /*
     * Save it
     */
    val fSaved = OrderDao.save(order)
    val saved = wait(fSaved)

    val modified = OrderFactory.generate(security_id=security_id).copy(id=saved.id, created_at=saved.created_at, updated_at=saved.updated_at)
    wait(OrderDao.update(modified))
    /*
     * Get it back from the DB
     */
    val dbOrder = wait(OrderDao.get(saved.id.get)).get
    /*
     * They should be the same. We need to copy the updated_at
     */
    assert(modified.copy(updated_at = dbOrder.updated_at) == dbOrder)

  }

  "OrderDao" should "updateWithOptimisticLocking Order correctly" in {

    /*
     * Create an entity
     */
    val order = OrderFactory.generate(security_id=security_id)
    /*
     * Save it
     */
    val fSaved = OrderDao.save(order)
    val saved = wait(fSaved)

    val modified1 = OrderFactory.generate(security_id=security_id).copy(id=saved.id, created_at=saved.created_at, updated_at=saved.updated_at)    
    val modified2 = OrderFactory.generate(security_id=security_id).copy(id=saved.id, created_at=saved.created_at, updated_at=saved.updated_at)
    val rowCount1 = wait(OrderDao.updateWithOptimisticLocking(modified1))
    val rowCount2 = wait(OrderDao.updateWithOptimisticLocking(modified1))
    
    assert(rowCount1 == 1)
    assert(rowCount2 == 0)

  }

  "OrderDao" should "delete Order correctly" in {

    /*
     * Create an entity
     */
    val order = OrderFactory.generate(security_id=security_id)
    /*
     * Save it
     */
    val fSaved = OrderDao.save(order)
    val saved = wait(fSaved)
    /*
     * Delete it
     */
    wait(OrderDao.delete(saved.id.get))
    /*
     * Get it back from the DB
     */
    val dbOrder = wait(OrderDao.get(saved.id.get))
    /*
     * They should be None
     */
    assert(dbOrder == None)

  }
}