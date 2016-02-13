package com.lot.user.dao

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import slick.driver.JdbcProfile
import com.lot.utils.{ DbModule }
import scala.concurrent.Future
import com.lot.user.model.UserTable
import slick.driver.MySQLDriver.api._
import com.lot.user.model.User
import com.lot.utils.DB._
import scala.concurrent.ExecutionContext.Implicits.global
import org.joda.time.DateTime
import com.lot.blockAmount.dao.BlockAmountDao
import com.lot.blockAmount.model.BlockAmount

object UserDao extends TableQuery(new UserTable(_)) {

  import com.lot.utils.CustomDBColMappers._

  /**
   * Saves the User to the DB
   * @return The Id of the saved entity
   */

  val insertQuery = this returning this.map(_.id) into ((user, id) => user.copy(id = Some(id)))

  def save(user: User): Future[User] = {
    /*
     * Ensure the timestamps are updated
     */
    val now = new DateTime()
    val o: User = user.copy(created_at = Some(now), updated_at = Some(now))

    val action = insertQuery += o
    db.run(action)
  }

  /**
   * Returns the User
   * @id The id of the User in the DB
   */
  def get(id: Long) = {
    db.run(this.filter(_.id === id).result.headOption)
  }

  def getByRole(role: String) = {
    db.run(this.filter(_.role === role).result)
  }

  def findByEmail(email: String) = {
    db.run(this.filter(_.email === email).result.headOption)
  }

  /**
   * Updates the User
   * @user The new fields will be updated in the DB
   */
  def update(user: User) = {
    // update the updated_at timestamp
    val now = new DateTime();
    val new_user: User = user.copy(updated_at = Some(now))

    db.run(this.filter(_.id === user.id).update(new_user))
  }

  /**
   * Updates the Position
   * @position The new fields will be updated in the DB but only if
   * the updated_at in the DB is the same as the one in the position param
   */
  def updateWithOptimisticLocking(user: User) = {
    // update the updated_at timestamp
    val now = new DateTime();
    val new_user: User = user.copy(updated_at = Some(now))

    db.run(this.filter(p => p.id === user.id && p.updated_at === user.updated_at).update(new_user))
  }

  /**
   * Blocks the amount specified by moving it from the account_balance to the blocked_amount
   * @return: 0 if amount was not blocked and 1 if the amount blocking succeeded
   */
  def addBlockedAmount(user_id: Long, order_id: Long, amount: Double): Future[Int] = {
    val q = for {
      /*
       * Get the user
       */
      user <- this.filter(_.id === user_id).result.headOption
      /*
       * Adjust the amounts
       */
      blocked_amount: Option[Double] = user.map { _.blocked_amount + amount }
      account_balance: Option[Double] = user.map { _.account_balance - amount }
      /*
       * Update but only if the account_balance > amount
       */
      updateUser <- this.filter {
        u => u.id === user_id && u.account_balance >= amount
      }.map {
        x => (x.account_balance, x.blocked_amount)
      }.update((account_balance.getOrElse(0), blocked_amount.getOrElse(0)))
      /*
       * Insert into block_amount for audit/deduct/unBlock      
       */
      insertBlockAmount <- BlockAmountDao.saveQuery(BlockAmount(None, user_id, order_id, amount))

    } yield (updateUser)

    db.run(q.transactionally)
  }

  /**
   * Blocks the amount specified by moving it from the account_balance to the blocked_amount
   * @return: 0 if amount was not blocked and 1 if the amount blocking succeeded
   */
  def unBlockedAmount(user_id: Long, order_id: Long, amount: Double): Future[Int] = {
    val q = for {
      /*
       * Get the user
       */
      user <- this.filter(_.id === user_id).result.headOption
      /*
       * Adjust the amounts
       */
      blocked_amount: Option[Double] = user.map { _.blocked_amount + amount }
      account_balance: Option[Double] = user.map { _.account_balance - amount }
      /*
       * Update but only if the account_balance > amount
       */
      updateUser <- this.filter {
        u => u.id === user_id && u.account_balance >= amount
      }.map {
        x => (x.account_balance, x.blocked_amount)
      }.update((account_balance.getOrElse(0), blocked_amount.getOrElse(0)))
      /*
       * Update the block_amount entry
       */
      block_amount = BlockAmountDao.getQuery(user_id, order_id)
      updateBlockAmount <- block_amount.map { b =>
        b match {
          case Some(ba) => BlockAmountDao.updateQuery(ba.copy(blocked_amount = (ba.blocked_amount - amount), status = "Unblocked"))
        }
      }

    } yield (updateUser)

    db.run(q.transactionally)
  }
  /**
   * Deducts the amount specified from the blocked_amount
   * @return 1 if the amount was deducted, 0 if deduction failed
   */
  def deductBlockedAmount(user_id: Long, order_id: Long, actual_amount_charged: Double) : Future[Int] = {
    val q = for {
      user <- this.filter(_.id === user_id).result.headOption

      /*
       * Update the block_amount entry
       */
      block_amount <- BlockAmountDao.getQuery(user_id, order_id)
      ba = block_amount.get
      updateBlockAmount <- BlockAmountDao.updateQuery(ba.copy(actual_amount_charged = actual_amount_charged, status = "Deducted"))

      /*
       * Now ensure the delta betw blocked_amount and actual_amount_charged 
       * is adjusted in the user.account_balance
       */
      val newUser = user.get.deductBlockedAmount(ba.blocked_amount, actual_amount_charged)
      updateUser <- this.filter(_.id === user_id).map { x => (x.blocked_amount, x.account_balance) }.update((newUser.blocked_amount, newUser.account_balance))
          

    } yield (updateUser)

    db.run(q.transactionally)
  }

  /**
   * Add the amount specified to the account_balance
   * @return 1 if the amount was added, 0 if addition failed
   */
  def addAccountBalance(user_id: Long, amount: Double) = {
    val q = for {
      user <- this.filter(_.id === user_id).result.headOption
      account_balance: Option[Double] = user.map { _.account_balance + amount }
      update <- this.filter(_.id === user_id).map { x => (x.account_balance) }.update((account_balance.get))
    } yield (update)

    db.run(q.transactionally)
  }

  /**
   * Deletes the user from the DB. Warning this is permanent and irreversable
   * @user This has the id which will be removed from the DB
   */
  def delete(user: User) = {
    db.run(this.filter(_.id === user.id).delete)
  }

  /**
   * Deletes the user
   * @id The id of the user to be deleted
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
   * Returns all the user in the DB
   */
  def list = {
    val allUsers = for (o <- this) yield o
    db.run(allUsers.result)
  }

  /**
   * Used only for testing to clean the DB after each test
   */
  def truncate = {
    db.run(sqlu"TRUNCATE TABLE users;")
  }

}
