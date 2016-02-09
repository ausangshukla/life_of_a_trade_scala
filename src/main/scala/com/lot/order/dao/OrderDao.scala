package com.lot.order.dao

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import slick.driver.JdbcProfile
import com.lot.utils.DbModule
import scala.concurrent.Future
import com.lot.order.model.OrderTable
import slick.driver.MySQLDriver.api._
import com.lot.order.model.Order
import com.lot.utils.DB._
import scala.concurrent.ExecutionContext.Implicits.global
import org.joda.time.DateTime
import com.lot.order.model.OrderType
import com.lot.security.model.SecurityTable
import com.lot.security.model.Security

object OrderDao extends TableQuery(new OrderTable(_)) with LazyLogging {

  import com.lot.utils.CustomDBColMappers._

  /**
   * This is used to return the id of the inserted object
   * http://stackoverflow.com/questions/31443505/slick-3-0-insert-and-then-get-auto-increment-value
   */
  val insertQuery = this returning this.map(o => o.id) into ((order, id) => order.copy(id = Some(id)))

  def save(order: Order): Future[Order] = {
    logger.debug(s"Saving $order")
    /*
     * Ensure the timestamps are updated
     */
    val now = new DateTime()
    val o: Order = order.copy(created_at = Some(now), updated_at = Some(now))
    val action = insertQuery += o
    db.run(action)
  }

  def get(id: Long) = {
    db.run(this.filter(_.id === id).result.headOption)
  }

  def update(order: Order): Future[Int] = {
    logger.debug(s"Updating $order")
    /*
     * Ensure the updated_at is set
     */
    val now = new DateTime()
    val o: Order = order.copy(updated_at = Some(now))
    db.run(this.filter(_.id === order.id).update(o))
  }
  
  def updatePreTradeStatus(order: Order): Future[Int] = {
    logger.debug(s"Updating $order")
    /*
     * Ensure the updated_at is set
     */
    val now = new DateTime()
    val o: Order = order.copy(updated_at = Some(now))
    db.run(this.filter(_.id === o.id.get).map{dbo=>(dbo.pre_trade_check_status, dbo.updated_at)}.update((o.pre_trade_check_status, o.updated_at.get)))
  }

  def updateMatchStatus(order: Order, matchedOrder: Order): Future[Int] = {
    logger.debug(s"Updating $order")
    /*
     * Ensure the updated_at is set
     */
    val now = new DateTime()
    val query = for {
      update1 <- this.filter { o1 => o1.id === order.id }.map { x => (x.unfilled_qty, x.trade_status, x.updated_at) }.update((order.unfilled_qty, order.trade_status, now))
      update2 <- this.filter { o1 => o1.id === matchedOrder.id }.map { x => (x.unfilled_qty, x.trade_status, x.updated_at) }.update((matchedOrder.unfilled_qty, matchedOrder.trade_status, now))
    } yield (update1 + update2)
    db.run(query.transactionally)
  }

  def delete(order: Order) = {
    logger.debug(s"Deleting $order")
    db.run(this.filter(_.id === order.id).delete)
  }

  def delete(delete_id: Long) = {
    logger.debug(s"Deleting $delete_id")
    db.run(this.filter(_.id === delete_id).delete)
  }
  
  def cancel(id: Long) = {
    logger.debug(s"Cancelling $id")
    db.run(this.filter(_.id === id).map(_.trade_status).update(OrderType.CANCELLED))
  }

  def createTables(): Future[Unit] = {
    db.run(DBIO.seq(this.schema.create))
  }

  def list: Future[Seq[(Order, Security)]] = {
    val allOrders = for {
      o <- this
      sec <- TableQuery[SecurityTable] if o.security_id === sec.id
    } yield (o, sec)

    val sorted = allOrders.sortBy {
      case (order, security) => order.id.desc
    }.result

    db.run(sorted)
  }

  /**
   * List of unfilled orders
   */
  def unfilled(security_id: Long, buy_sell: String) = {
    val allOrders = for {
      o <- this if (o.unfilled_qty > 0.0 && o.security_id === security_id && o.buy_sell === buy_sell && o.trade_status =!= OrderType.CANCELLED)
    } yield (o)
    db.run(allOrders.sortBy(_.price.desc).result)
  }
  
  /**
   * Returns the unfilled orders in the order they came in
   */
  def getUnprocessedOrders(security_id: Long) = {
    val allOrders = for {
      o <- this if (o.unfilled_qty > 0.0 && o.security_id === security_id && o.trade_status =!= OrderType.CANCELLED)
    } yield (o)
    db.run(allOrders.sortBy(_.id.asc).result)
  }

  def unfilled_buys(security_id: Long) = {
    val allOrders = for {
      o <- this if (o.unfilled_qty > 0.0 && o.security_id === security_id && o.buy_sell === OrderType.BUY && o.trade_status =!= OrderType.CANCELLED)
    } yield (o)
    /*
     * Market order on top of limit
     * Higher prices on top of lower
     * Time priority after that
     * TODO - cannot sort by created_at, fix that  
     */
    db.run(allOrders.sortBy(x => (x.order_type.desc, x.price.desc, x.id.asc)).result)
  }

  def unfilled_sells(security_id: Long) = {
    val allOrders = for {
      o <- this if (o.unfilled_qty > 0.0 && o.security_id === security_id && o.buy_sell === OrderType.SELL && o.trade_status =!= OrderType.CANCELLED)
    } yield (o)
    /*
     * Market order on top of limit
     * Lower prices on top of lower
     * Time priority after that
     * TODO - cannot sort by created_at, fix that         
     */
    db.run(allOrders.sortBy(x => (x.order_type.desc, x.price.asc, x.id.asc)).result)
  }

  /**
   * Used only for testing to clean the DB after each test
   */
  def truncate = {
    db.run(sqlu"TRUNCATE TABLE orders;")
  }

}
