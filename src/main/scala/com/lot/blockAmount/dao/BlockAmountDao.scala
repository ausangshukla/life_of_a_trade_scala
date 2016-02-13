package com.lot.blockAmount.dao

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import slick.driver.JdbcProfile
import com.lot.utils.{ DbModule }
import scala.concurrent.Future
import com.lot.blockAmount.model.BlockAmountTable
import slick.driver.MySQLDriver.api._
import com.lot.blockAmount.model.BlockAmount
import com.lot.utils.DB._
import scala.concurrent.ExecutionContext.Implicits.global
import org.joda.time.DateTime

object BlockAmountDao extends TableQuery(new BlockAmountTable(_)) with LazyLogging {

  import com.lot.utils.CustomDBColMappers._

  /**
   * Saves the BlockAmount to the DB
   * @return The Id of the saved entity
   */

  val insertQuery = this returning this.map(_.id) into ((blockAmount, id) => blockAmount.copy(id = Some(id)))

  def save(blockAmount: BlockAmount): Future[BlockAmount] = {
    /*
     * Ensure the timestamps are updated
     */
    val now = new DateTime()    
    val o: BlockAmount = blockAmount.copy(created_at = Some(now), updated_at = Some(now))
    logger.debug(s"Saving $blockAmount")
    logger.debug(s"Converted $o")
    val action = insertQuery += o
    db.run(action)
  }

  def saveQuery(blockAmount: BlockAmount) = {
    /*
     * Ensure the timestamps are updated
     */
    val now = new DateTime()
    val o: BlockAmount = blockAmount.copy(created_at = Some(now), updated_at = Some(now))

    val action = insertQuery += o
    action
  }

  /**
   * Returns the BlockAmount
   * @id The id of the BlockAmount in the DB
   */
  def get(id: Long) = {
    db.run(this.filter(_.id === id).result.headOption)
  }

  def get(user_id: Long, order_id: Long) = {
    db.run(this.filter(ba => ba.user_id === user_id && ba.order_id === order_id).result.headOption)
  }
  
  def getQuery(user_id: Long, order_id: Long) = {
    this.filter(ba => ba.user_id === user_id && ba.order_id === order_id).result.headOption
  }

  /**
   * Updates the BlockAmount
   * @blockAmount The new fields will be updated in the DB
   */
  def update(blockAmount: BlockAmount) = {
    // update the updated_at timestamp
    val now = new DateTime();
    val new_blockAmount: BlockAmount = blockAmount.copy(updated_at = Some(now))

    db.run(this.filter(_.id === blockAmount.id).update(new_blockAmount))
  }
  
  def updateQuery(blockAmount: BlockAmount) = {
    // update the updated_at timestamp
    val now = new DateTime();
    val new_blockAmount: BlockAmount = blockAmount.copy(updated_at = Some(now))

    this.filter(_.id === blockAmount.id).update(new_blockAmount)
  }

  /**
   * Updates the Position
   * @position The new fields will be updated in the DB but only if
   * the updated_at in the DB is the same as the one in the position param
   */
  def updateWithOptimisticLocking(blockAmount: BlockAmount) = {
    // update the updated_at timestamp
    val now = new DateTime();
    val new_blockAmount: BlockAmount = blockAmount.copy(updated_at = Some(now))

    db.run(this.filter(p => p.id === blockAmount.id && p.updated_at === blockAmount.updated_at).update(new_blockAmount))
  }

  /**
   * Deletes the blockAmount from the DB. Warning this is permanent and irreversable
   * @blockAmount This has the id which will be removed from the DB
   */
  def delete(blockAmount: BlockAmount) = {
    db.run(this.filter(_.id === blockAmount.id).delete)
  }

  /**
   * Deletes the blockAmount
   * @id The id of the blockAmount to be deleted
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
   * Returns all the blockAmount in the DB
   */
  def list = {
    val allBlockAmounts = for (o <- this) yield o
    db.run(allBlockAmounts.sortBy(_.id).result)
  }

  /**
   * Used only for testing to clean the DB after each test
   */
  def truncate = {
    db.run(sqlu"TRUNCATE TABLE block_amounts;")
  }

}
