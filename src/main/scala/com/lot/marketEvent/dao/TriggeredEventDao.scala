package com.lot.marketEvent.dao

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import slick.driver.JdbcProfile
import com.lot.utils.{ DbModule }
import scala.concurrent.Future
import com.lot.marketEvent.model.TriggeredEventTable
import slick.driver.MySQLDriver.api._
import com.lot.marketEvent.model.TriggeredEvent
import com.lot.utils.DB._
import scala.concurrent.ExecutionContext.Implicits.global
import org.joda.time.DateTime
import com.lot.marketEvent.model.MarketEventTable

object TriggeredEventDao extends TableQuery(new TriggeredEventTable(_)) {

  import com.lot.utils.CustomDBColMappers._

  /**
   * Saves the TriggeredEvent to the DB
   * @return The Id of the saved entity
   */

  val insertQuery = this returning this.map(_.id) into ((triggeredEvent, id) => triggeredEvent.copy(id = Some(id)))

  def save(triggeredEvent: TriggeredEvent): Future[TriggeredEvent] = {
    /*
     * Ensure the timestamps are updated
     */
    val now = new DateTime()
    val o: TriggeredEvent = triggeredEvent.copy(created_at = Some(now), updated_at = Some(now))

    val action = insertQuery += o
    db.run(action)
  }

  /**
   * Returns the TriggeredEvent and the MarketEvent
   * @id The id of the TriggeredEvent in the DB
   */
  def get(id: Long) = {
    val query =
      for {
        te <- this.filter(_.id === id)
        me <- TableQuery[MarketEventTable] if te.market_event_id === me.id
      } yield (te, me)

    db.run(query.result.headOption)

  }

  /**
   * Updates the TriggeredEvent
   * @triggeredEvent The new fields will be updated in the DB
   */
  def update(triggeredEvent: TriggeredEvent) = {
    // update the updated_at timestamp
    val now = new DateTime();
    val new_triggeredEvent: TriggeredEvent = triggeredEvent.copy(updated_at = Some(now))

    db.run(this.filter(_.id === triggeredEvent.id).update(new_triggeredEvent))
  }

  /**
   * Updates the triggeredEvent
   * @triggeredEvent The new fields will be updated in the DB but only if
   * the updated_at in the DB is the same as the one in the position param
   */
  def updateWithOptimisticLocking(triggeredEvent: TriggeredEvent) = {
    // update the updated_at timestamp
    val now = new DateTime();
    val new_triggeredEvent: TriggeredEvent = triggeredEvent.copy(updated_at = Some(now))

    db.run(this.filter(p => p.id === triggeredEvent.id && p.updated_at === triggeredEvent.updated_at).update(new_triggeredEvent))
  }

  /**
   * Deletes the triggeredEvent from the DB. Warning this is permanent and irreversable
   * @triggeredEvent This has the id which will be removed from the DB
   */
  def delete(triggeredEvent: TriggeredEvent) = {
    db.run(this.filter(_.id === triggeredEvent.id).delete)
  }

  /**
   * Deletes the triggeredEvent
   * @id The id of the triggeredEvent to be deleted
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
   * Returns all the triggeredEvent, marketEvent tuples in the DB
   */
  def list = {
    val allTriggeredEvents = for {
      te <- this
      me <- TableQuery[MarketEventTable] if te.market_event_id === me.id
    } yield (te, me)

    val sorted = allTriggeredEvents.sortBy {
      case (triggered, market) => triggered.id.desc
    }.result

    db.run(sorted)
  }

  /**
   * Used only for testing to clean the DB after each test
   */
  def truncate = {
    db.run(sqlu"TRUNCATE TABLE triggered_events;")
  }

}
