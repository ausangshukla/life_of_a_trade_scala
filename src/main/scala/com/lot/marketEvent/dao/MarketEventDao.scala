package com.lot.marketEvent.dao

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import slick.driver.JdbcProfile
import com.lot.utils.{ DbModule }
import scala.concurrent.Future
import com.lot.marketEvent.model.MarketEventTable
import slick.driver.MySQLDriver.api._
import com.lot.marketEvent.model.MarketEvent
import com.lot.utils.DB._
import scala.concurrent.ExecutionContext.Implicits.global
import org.joda.time.DateTime

object MarketEventDao extends TableQuery(new MarketEventTable(_)) {

  import com.lot.utils.CustomDBColMappers._
  
  /**
   * Saves the MarketEvent to the DB
   * @return The Id of the saved entity
   */
  
  val insertQuery = this returning this.map(_.id) into ((marketEvent, id) => marketEvent.copy(id = Some(id)))
  
  def save(marketEvent: MarketEvent) : Future[MarketEvent] = {
    /*
     * Ensure the timestamps are updated
     */
    val now = new DateTime()
    val o: MarketEvent = marketEvent.copy(created_at = Some(now), updated_at = Some(now))   
 
    val action = insertQuery += o
    db.run(action)
  }
  
  /**
   * Returns the MarketEvent 
   * @id The id of the MarketEvent in the DB
   */
  def get(id: Long) = {
    db.run(this.filter(_.id === id).result.headOption)
  }
  
  /**
   * Updates the MarketEvent
   * @marketEvent The new fields will be updated in the DB
   */
  def update(marketEvent: MarketEvent) = {
    // update the updated_at timestamp
    val now = new DateTime();
    val new_marketEvent: MarketEvent = marketEvent.copy(updated_at = Some(now))
    
    db.run(this.filter(_.id === marketEvent.id).update(new_marketEvent))
  }
  
   /**
   * Updates the marketEvent
   * marketEvent The new fields will be updated in the DB but only if 
   * the updated_at in the DB is the same as the one in the position param 
   */
  def updateWithOptimisticLocking( marketEvent:  MarketEvent) = {
    // update the updated_at timestamp
    val now = new DateTime();
    val new_marketEvent:MarketEvent =  marketEvent.copy(updated_at = Some(now))
    
    db.run(this.filter(p=>p.id === marketEvent.id && p.updated_at === marketEvent.updated_at).update(new_marketEvent))
  }
  
  /**
   * Deletes the marketEvent from the DB. Warning this is permanent and irreversable
   * @marketEvent This has the id which will be removed from the DB
   */
  def delete(marketEvent: MarketEvent) = {
    db.run(this.filter(_.id === marketEvent.id).delete)
  }
  
  /**
   * Deletes the marketEvent
   * @id The id of the marketEvent to be deleted
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
   * Returns all the marketEvent in the DB
   */
  def list = {
    val allMarketEvents = for (o <- this) yield o
    db.run(allMarketEvents.result)
  }

  /**
   * Used only for testing to clean the DB after each test
   */
  def truncate = {
    db.run(sqlu"TRUNCATE TABLE market_events;")
  }

}
