package com.lot.position.dao

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import slick.driver.JdbcProfile
import com.lot.utils.{ DbModule }
import scala.concurrent.Future
import com.lot.position.model.PositionTable
import slick.driver.MySQLDriver.api._
import com.lot.position.model.Position
import com.lot.utils.DB._
import scala.concurrent.ExecutionContext.Implicits.global
import org.joda.time.DateTime

object PositionDao extends TableQuery(new PositionTable(_)) {

  /**
   * Saves the Position to the DB
   * @return The Id of the saved entity
   */
  
  val insertQuery = this returning this.map(_.id) into ((position, id) => position.copy(id = Some(id)))
  
  def save(position: Position) : Future[Position] = {
    val action = insertQuery += position
    db.run(action)
  }
  
  /**
   * Returns the Position 
   * @id The id of the Position in the DB
   */
  def get(id: Long) = {
    db.run(this.filter(_.id === id).result.headOption)
  }
  
  /**
   * Updates the Position
   * @position The new fields will be updated in the DB
   */
  def update(position: Position) = {
    // update the updated_at timestamp
    val now = new DateTime();
    val new_position: Position = position.copy(updated_at = Some(now))
    
    db.run(this.filter(_.id === position.id).update(new_position))
  }
  
  /**
   * Deletes the position from the DB. Warning this is permanent and irreversable
   * @position This has the id which will be removed from the DB
   */
  def delete(position: Position) = {
    db.run(this.filter(_.id === position.id).delete)
  }
  
  /**
   * Deletes the position
   * @id The id of the position to be deleted
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
   * Returns all the position in the DB
   */
  def list = {
    val allPositions = for (o <- this) yield o
    db.run(allPositions.result)
  }
  
    /**
   * Used only for testing to clean the DB after each test
   */
  def truncate = {
    db.run(sqlu"TRUNCATE TABLE positions;")
  }


}
