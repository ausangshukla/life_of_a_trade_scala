package com.lot.trade.dao

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import slick.driver.JdbcProfile
import com.lot.utils.DbModule
import scala.concurrent.Future
import com.lot.trade.model.TradeTable
import slick.driver.MySQLDriver.api._
import com.lot.trade.model.Trade
import com.lot.utils.DB._
import scala.concurrent.ExecutionContext.Implicits.global
import org.joda.time.DateTime
object TradeDao extends TableQuery(new TradeTable(_)) with LazyLogging {

  /**
   * This is used to return the id of the inserted object
   * http://stackoverflow.com/questions/31443505/slick-3-0-insert-and-then-get-auto-increment-value
   */
  val insertQuery = this returning this.map(o=>o.id) into ((trade, id) => trade.copy(id = Some(id)))

  def save(trade: Trade): Future[Trade] = {
    logger.debug(s"Saving $trade")
    /*
     * Ensure the timestamps are updated
     */
    val now = new DateTime()
    val o: Trade = trade.copy(created_at = Some(now), updated_at = Some(now))   
    val action = insertQuery += o
    db.run(action)
  }

  def get(id: Long) = {
    db.run(this.filter(_.id === id).result.headOption)
  }

  def update(trade: Trade): Future[Int] = {
    logger.debug(s"Updating $trade")
    /*
     * Ensure the updated_at is set
     */
    val now = new DateTime()
    val o: Trade = trade.copy(updated_at = Some(now))
    db.run(this.filter(_.id === trade.id).update(o))
  }

  def delete(trade: Trade) = {
    logger.debug(s"Deleting $trade")
    db.run(this.filter(_.id === trade.id).delete)
  }

  def delete(delete_id: Long) = {
    logger.debug(s"Deleting $delete_id")
    db.run(this.filter(_.id === delete_id).delete)
  }

  def createTables(): Future[Unit] = {
    db.run(DBIO.seq(this.schema.create))
  }

  def list = {
    val allTrades = for {
      o <- this
    } yield (o)
    db.run(allTrades.sortBy(_.id.desc).result)
  }


  /**
   * Used only for testing to clean the DB after each test
   */
  def truncate = {
    db.run(sqlu"TRUNCATE TABLE trades;")
  }

}
