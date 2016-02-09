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
import com.lot.NewTest
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.TableDrivenPropertyChecks

class UOMMatchTest extends BaseTest with TableDrivenPropertyChecks {

  val oppositeTrades = Table(
    ("buy_sell", "order_type", "quantity", "unfilled_qty", "price", "OppositeBuySell", "OppositeType", "OppositeQuantity", "OppositeUnfilled_qty", "oprice", "MatchFound", "remaining_unfilled"),
    (OrderType.BUY, OrderType.MARKET, 100.0, 100.0, 0.0, OrderType.SELL, OrderType.MARKET, 100.0, 100.0, 0.0, true, 0.0),
    (OrderType.BUY, OrderType.MARKET, 100.0, 100.0, 0.0, OrderType.SELL, OrderType.MARKET, 100.0, 50.0, 0.0, true, 50.0),
    (OrderType.BUY, OrderType.MARKET, 100.0, 100.0, 0.0, OrderType.SELL, OrderType.MARKET, 100.0, 0.0, 0.0, false, 100.0),
    // Switch buys and sells
    (OrderType.SELL, OrderType.MARKET, 100.0, 100.0, 0.0, OrderType.BUY, OrderType.MARKET, 100.0, 100.0, 0.0, true, 0.0),
    (OrderType.SELL, OrderType.MARKET, 100.0, 100.0, 0.0, OrderType.BUY, OrderType.MARKET, 100.0, 50.0, 0.0, true, 50.0),
    // Test unfilled_qty = 0 - no match
    (OrderType.SELL, OrderType.MARKET, 100.0, 100.0, 0.0, OrderType.BUY, OrderType.MARKET, 100.0, 0.0, 0.0, false, 100.0),
    (OrderType.BUY, OrderType.MARKET, 100.0, 0.0, 0.0, OrderType.SELL, OrderType.MARKET, 100.0, 100.0, 0.0, false, 100.0),
    // Market vs Limit
    (OrderType.BUY, OrderType.MARKET, 100.0, 100.0, 0.0, OrderType.SELL, OrderType.LIMIT, 100.0, 100.0, 80.0, true, 0.0),
    (OrderType.BUY, OrderType.MARKET, 100.0, 100.0, 0.0, OrderType.SELL, OrderType.LIMIT, 100.0, 50.0, 80.0, true, 50.0),
    // Limit vs Market
    (OrderType.BUY, OrderType.LIMIT, 100.0, 100.0, 80.0, OrderType.SELL, OrderType.MARKET, 100.0, 100.0, 0.0, true, 0.0),
    (OrderType.BUY, OrderType.LIMIT, 100.0, 100.0, 80.0, OrderType.SELL, OrderType.MARKET, 100.0, 50.0, 0.0, true, 50.0),
    // Test unfilled_qty = 0 - no match
    (OrderType.BUY, OrderType.LIMIT, 100.0, 100.0, 80.0, OrderType.SELL, OrderType.MARKET, 100.0, 0.0, 0.0, false, 100.0),
    // Limit vs Limit
    (OrderType.BUY, OrderType.LIMIT, 100.0, 100.0, 80.0, OrderType.SELL, OrderType.LIMIT, 100.0, 100.0, 80.0, true, 0.0),
    (OrderType.BUY, OrderType.LIMIT, 100.0, 100.0, 80.0, OrderType.SELL, OrderType.LIMIT, 100.0, 50.0, 70.0, true, 50.0),
    // Test no price match
    (OrderType.BUY, OrderType.LIMIT, 100.0, 100.0, 80.0, OrderType.SELL, OrderType.LIMIT, 100.0, 100.0, 90.0, false, 100.0),
    // Limit vs Limit - switch buys and sells
    (OrderType.SELL, OrderType.LIMIT, 100.0, 100.0, 80.0, OrderType.BUY, OrderType.LIMIT, 100.0, 100.0, 80.0, true, 100.0),
    (OrderType.SELL, OrderType.LIMIT, 100.0, 100.0, 80.0, OrderType.BUY, OrderType.LIMIT, 100.0, 50.0, 90.0, true, 50.0),
    // Test no price match
    (OrderType.SELL, OrderType.LIMIT, 100.0, 100.0, 80.0, OrderType.BUY, OrderType.LIMIT, 100.0, 100.0, 70.0, false, 100.0))

  "An UnfilledOrderManager" should "find a match for an order, when there is an opposite order in the DB" in {

    forAll(oppositeTrades) { (buy_sell: String, order_type: String, quantity: Double, unfilled_qty: Double, price: Double,
      obuy_sell: String, oorder_type: String, oquantity: Double, ounfilled_qty: Double, oprice: Double,
      matchFound: Boolean, remaining_unfilled: Double) =>
      /*
       * Ensure DB is cleaned - otherwise tests will leave state in the DB and next test may not pass
       */
      wait(OrderDao.truncate)

      val security_id = 10

      /*
     * Generate a random order & save it
     */
      val o1 = OrderFactory.generate(security_id = security_id, buy_sell = buy_sell, order_type = order_type, quantity = quantity, unfilled_qty = unfilled_qty, price = price)
      val saved = wait(OrderDao.save(o1))

      /*
     * Start the UOM
     */
      val uom = UnfilledOrderManager(security_id)

      val opposite = OrderFactory.generate(security_id = security_id, buy_sell = obuy_sell, order_type = oorder_type, quantity = oquantity, unfilled_qty = ounfilled_qty, price = oprice)
      val matchedOrder = uom.findMatch(opposite)
      /*
     * Ensure UOM finds the match 
     */
      if (matchFound) {
        matchedOrder shouldBe Some(saved)
      } else {
        matchedOrder shouldBe None
      }
    }

  }

  "An UnfilledOrderManager" should "adjust matched orders, and remove them when required"  in {

    forAll(oppositeTrades) { (buy_sell: String, order_type: String, quantity: Double, unfilled_qty: Double, price: Double,
      obuy_sell: String, oorder_type: String, oquantity: Double, ounfilled_qty: Double, oprice: Double,
      matchFound: Boolean, remaining_unfilled: Double) =>
      /*
       * Ensure DB is cleaned - otherwise tests will leave state in the DB and next test may not pass
       */
      wait(OrderDao.truncate)

      val security_id = 10

      /*
     * Generate a random order & save it
     */
      val o1 = OrderFactory.generate(security_id = security_id, buy_sell = buy_sell, order_type = order_type, quantity = quantity, unfilled_qty = unfilled_qty, price = price)
      val saved = wait(OrderDao.save(o1))
      val opposite = OrderFactory.generate(security_id = security_id, buy_sell = obuy_sell, order_type = oorder_type, quantity = oquantity, unfilled_qty = ounfilled_qty, price = oprice)
      val savedOpposite = wait(OrderDao.save(opposite))

      if (matchFound) {
        /*
       * Start the UOM
       */
        val uom = UnfilledOrderManager(security_id)

        /*
      	 * Adjust the orders
       	 */
        val buyOrder = uom.buys(0)
        val sellOrder = uom.sells(0)
        uom.adjustOrders(buyOrder, sellOrder)

        /*
		     * load the DB versions post adjustment 
    		 */
        val dbBuyOrder = wait(OrderDao.get(buyOrder.id.get)).get
        val dbSellOrder = wait(OrderDao.get(sellOrder.id.get)).get

        
        buyOrder.unfilled_qty - sellOrder.unfilled_qty match {
          case x if x == 0 => {
            // UOM queues are updated
            assert(uom.buys.length == 0)
            assert(uom.sells.length == 0)
            // unfilled_qty is updated
            assert(buyOrder.unfilled_qty == 0)
            assert(sellOrder.unfilled_qty == 0)
            // DB unfilled_qty is updated
            assert(dbBuyOrder.unfilled_qty == 0)
            assert(dbSellOrder.unfilled_qty == 0)
          }
          case x if x > 0 => {
            // UOM queues are updated
            assert(uom.buys.length == 1)
            assert(uom.sells.length == 0)
            // unfilled_qty is updated
            assert(buyOrder.unfilled_qty == remaining_unfilled)
            assert(sellOrder.unfilled_qty == 0)
            // DB unfilled_qty is updated
            assert(dbBuyOrder.unfilled_qty == remaining_unfilled)
            assert(dbSellOrder.unfilled_qty == 0)
          }
          case x if x < 0 => {
            // UOM queues are updated
            assert(uom.buys.length == 0)
            assert(uom.sells.length == 1)
            // unfilled_qty is updated
            assert(buyOrder.unfilled_qty == 0)
            assert(sellOrder.unfilled_qty == remaining_unfilled)
            // DB unfilled_qty is updated
            assert(dbBuyOrder.unfilled_qty == 0)
            assert(dbSellOrder.unfilled_qty == remaining_unfilled)
          }

        }

      }
    }

  }
}