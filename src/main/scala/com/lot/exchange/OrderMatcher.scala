package com.lot.exchange

import akka.pattern.ask
import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import com.lot.order.model.Order
import com.lot.order.model.Order
import com.lot.order.model.OrderType
import akka.actor.ActorLogging
import scala.collection.mutable.MutableList
import scala.collection.mutable.ListBuffer
import com.lot.order.dao.OrderDao
import com.lot.exchange.Message._
import akka.routing.FromConfig
import com.lot.trade.service.TradeGenerator
import com.lot.trade.service.TradeGenerator
import akka.actor.Props
import com.lot.trade.model.Trade
import org.joda.time.DateTime
import com.lot.trade.model.TradeMessage
import com.lot.trade.model.TradeState
import akka.actor.ActorRef
import com.lot.security.model.Price
import com.lot.security.model.PriceMessage
import scala.concurrent.duration._
import akka.util.Timeout
import scala.concurrent.Await
import akka.actor.ReceiveTimeout
import scala.annotation.tailrec

/**
 * The matcher for a particular security
 * @security_id : The security_id for the security whose orders are handled by this matcher instance.
 * Orders of same security are handled serially, but of different securities are handled in parallel
 * @buys : The list of buy orders sorted by price / time priority
 * @sells : The list of sell orders sorted by price / time priority
 */
class OrderMatcher(val security_id: Long, unfilledOM: UnfilledOrderManager,
                   tradeGenerator: ActorRef, securityManager: ActorRef) extends Actor with ActorLogging {

  var current_price: Double = 0.0

  var pre_start_last_processed_order_id = 0L
  
  override def preStart = {
    log.info("prestart: Started")
    /*
     * Load the price of the security from the securityManager
     */
    implicit val timeout = Timeout(5 second)
    /*
     * Ask for the price
     */
    val futurePrice = securityManager ? PriceMessage.Get(Price(security_id, current_price))
    /*
     * Wait till we get it
     */
    val value = Await.result(futurePrice, 5 seconds).asInstanceOf[PriceMessage.Value]
    current_price = value.price.price
    
    /*
     * Ensure this actor times out and does not hog resources
     */
    context.setReceiveTimeout(600 seconds)
    
    /*
     * Now play all the unprocessed orders in the DB 
     */
    val orders = Await.result(OrderDao.getUnprocessedOrders(security_id), 5 seconds)
    for {
      order <- orders
    } yield (handleNewOrder(order))

    /**
     * The last Id processed during pre start
     */
    pre_start_last_processed_order_id = if (orders.length > 0) orders.last.id.get else 0L
    
    log.info("prestart: Completed")
    
  }

  def receive = {
    case NewOrder(order, at)    => {
      /*
       * We do this check because its possible this trade was picked up 
       * during prestart and already processed.
       * 1. In the OrderService the Order comes in from the web and is saved to DB
       * 2. Then its forwarded here
       * 3. This actor starts up and replays everying in the DB - includes old unprocessed orders
       * 4. This may cause the current order to have already been replayed
       */
      if(order.id.get > pre_start_last_processed_order_id) {
        handleNewOrder(order)
      } else {
        log.debug(s"Order processed during prestart. skipping $order")
      }
    }
    case CancelOrder(order, at) => { handleCancelOrder(order) }
    case ReceiveTimeout => {
      // To turn it off
      log.warning(s"Stopping $self due to timeout")
      context.setReceiveTimeout(Duration.Undefined)
      context.stop(self)
    }
  }

  /**
   * Simply dequeue it - its already marked as inactive in the DB
   */
  def handleCancelOrder(order: Order): Unit = {
    unfilledOM.dequeueOrder(order, true)
  }
  /**
   * @order : The new order to be handled
   */
  @tailrec
  private def handleNewOrder(order: Order): Unit = {
    log.info(s"Handling unfilled_qty  = ${order.unfilled_qty} for order $order")

    /*
     * Find a match
     */
    val matchedOrder: Option[Order] = unfilledOM.findMatch(order)
    matchedOrder match {
      case Some(mo) => {
        /*
         * Update price of the security
         */
        current_price = generatePrice(order, mo)

        /*
         * Generate the trades 
         */
        val(trade, counter_trade) = generateTrades(order, mo)
        
        /*
         * Send it off to be booked
         */        
        bookTrades(trade,counter_trade)
        
        /*
         * Ensure the unfilled_qty is adjusted for both orders
         */
        unfilledOM.adjustOrders(order, mo)

        /*
         * Recursively call handleNewOrder until all the quantity is filled or the order is enqueued
         */
        if (order.unfilled_qty > 0) {
          handleNewOrder(order)
        }
      }
      case None => {
        /*
         * Nothing matches - so enqueue
         */
        unfilledOM.enqueOrder(order)
      }
    }
  }

  /**
   * Generates a price sends it off for saving in the DB to the security service
   */
  private def generatePrice(order: Order, matchedOrder: Order) = {
    val orderPrices = (order.order_type, order.price, matchedOrder.order_type, matchedOrder.price)

    /*
     * Set the current price, based on the order and matchedOrder
     */
    val price = orderPrices match {
      case (OrderType.MARKET, mop, OrderType.MARKET, mop2) => current_price // Its the market price
      case (OrderType.MARKET, mop, OrderType.LIMIT, lop)   => lop // Its the limit price 
      case (OrderType.LIMIT, lop, OrderType.MARKET, mop)   => lop // Its the limit price
      case (OrderType.LIMIT, lop, OrderType.LIMIT, lop2)   => lop // Its the limit of the incoming order     
    }

    /*
     * Broadcast the price
     */
    securityManager ! PriceMessage.Set(Price(order.security_id, price))
    
    /*
     * Return the generated price
     */
    price
  }
  /**
   * Generates a trade and sends it off for booking
   */
  private def generateTrades(order: Order, matchedOrder: Order) = {
    /*
     * Send to trade booking actor
     */
    log.info(s"Generating trade for order $order with matchedOrder $matchedOrder")

    /*
     * We need to figure out the quantity to generate the trades for
     */
    val quantity = if (order.unfilled_qty > matchedOrder.unfilled_qty) matchedOrder.unfilled_qty else order.unfilled_qty

    /*
     * There are 2 trades generated for the 2 orders that got matched, 
     * one for each user whose order was matched
     */
    val trade = Trade(id = None, trade_date = new DateTime(), settlement_date = new DateTime(),
      security_id = order.security_id, ticker = order.ticker,
      quantity = quantity, price = current_price, buy_sell = order.buy_sell,
      user_id = order.user_id, order_id = order.id.get,
      matched_order_id = matchedOrder.id.get, state = TradeState.ACTIVE, exchange = order.exchange)

    val counter_trade = Trade(id = None, trade_date = new DateTime(), settlement_date = new DateTime(),
      security_id = order.security_id, ticker = order.ticker,
      quantity = quantity, price = current_price, buy_sell = matchedOrder.buy_sell,
      user_id = matchedOrder.user_id, order_id = matchedOrder.id.get,
      matched_order_id = order.id.get, state = TradeState.ACTIVE, exchange = matchedOrder.exchange)

      
    /*
     * return the generated trades
     */
    (trade, counter_trade)
  }
  
  
  private def bookTrades(trade: Trade, counter_trade: Trade) = {
    /*
     * Send it
     */
    tradeGenerator ! TradeMessage.New(trade)
    tradeGenerator ! TradeMessage.New(counter_trade)
  
  }
}