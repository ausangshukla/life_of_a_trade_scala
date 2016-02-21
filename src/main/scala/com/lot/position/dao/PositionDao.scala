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
import com.lot.user.model.User
import com.lot.user.model.UserRoles

object PositionDao extends TableQuery(new PositionTable(_)) {

  import com.lot.utils.CustomDBColMappers._
  /**
   * Saves the Position to the DB
   * @return The Id of the saved entity
   */
  
  val insertQuery = this returning this.map(_.id) into ((position, id) => position.copy(id = Some(id)))
  
  def save(position: Position) : Future[Position] = {
    /*
     * Ensure the timestamps are updated
     */
    val now = new DateTime()
    val o: Position = position.copy(created_at = Some(now), updated_at = Some(now))   
    val action = insertQuery += o
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
   * Returns the Position 
   * @security_id The id of the security for the Position in the DB
   */
  def get(security_id: Long, user_id: Long) = {
    db.run(this.filter(p => p.security_id === security_id && p.user_id === user_id).result.headOption)
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
   * Updates the Position
   * @position The new fields will be updated in the DB but only if 
   * the updated_at in the DB is the same as the one in the position param 
   */
  def updateWithOptimisticLocking(position: Position) = {
    // update the updated_at timestamp
    val now = new DateTime();
    val new_position: Position = position.copy(updated_at = Some(now))
    
    db.run(this.filter(p=>p.id === position.id && p.updated_at === position.updated_at).update(new_position))
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
  def list(current_user:User) = {
    val allPositions = for {
      o <- if(current_user.role == UserRoles.TRADER) this.filter(_.user_id === current_user.id.get) else this
    } yield o
    
    db.run(allPositions.result)
  }
  
    /**
   * Used only for testing to clean the DB after each test
   */
  def truncate = {
    db.run(sqlu"TRUNCATE TABLE positions;")
  }


}
