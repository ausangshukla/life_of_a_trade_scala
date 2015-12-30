package com.lot.trade.dao

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import slick.driver.JdbcProfile
import com.lot.utils.{ DbModule }
import scala.concurrent.Future
import com.lot.trade.model.TradeTable
import slick.driver.MySQLDriver.api._
import com.lot.trade.model.Trade
import com.lot.utils.DB._
import scala.concurrent.ExecutionContext.Implicits.global
import org.joda.time.DateTime

object TradeDao extends TableQuery(new TradeTable(_)) {

  /**
   * Saves the Trade to the DB
   * @return The Id of the saved entity
   */
  def save(trade: Trade): Future[Int] = {
    // Update the timestamps
    val now = new DateTime();
    val new_trade: Trade = trade.copy(created_at = Some(now), updated_at = Some(now))
    
    db.run(this += new_trade).mapTo[Int] 
  }

  /**
   * Returns the Trade 
   * @id The id of the Trade in the DB
   */
  def get(id: Long) = {
    db.run(this.filter(_.id === id).result.headOption)
  }
  
  /**
   * Updates the Trade
   * @trade The new fields will be updated in the DB
   */
  def update(trade: Trade) = {
    // update the updated_at timestamp
    val now = new DateTime();
    val new_trade: Trade = trade.copy(updated_at = Some(now))
    
    db.run(this.filter(_.id === trade.id).update(new_trade))
  }
  
  /**
   * Deletes the trade from the DB. Warning this is permanent and irreversable
   * @trade This has the id which will be removed from the DB
   */
  def delete(trade: Trade) = {
    db.run(this.filter(_.id === trade.id).delete)
  }
  
  /**
   * Deletes the trade
   * @id The id of the trade to be deleted
   */
  def delete(delete_id: Long) = {
    db.run(this.filter(_.id === delete_id).delete)
  }

  /**
   * Creates the tables
   * Warning - do not call this in production
   */
  def createTables(): Future[Unit] = {
    db.run(DBIO.seq(this.schema.create))
  }

  /**
   * Returns all the trade in the DB
   */
  def list = {
    val allTrades = for (o <- this) yield o
    db.run(allTrades.result)
  }

}
