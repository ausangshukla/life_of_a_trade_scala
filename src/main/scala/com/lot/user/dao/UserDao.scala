package com.lot.usermanager.dao

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import persistence.entities.{ Suppliers, Supplier }
import slick.driver.JdbcProfile
import utils.{ DbModule }
import scala.concurrent.Future
import com.lot.user.model.UserTable
import slick.driver.MySQLDriver.api._
import com.lot.user.model.User
import utils.DB._

class UserDao() extends LazyLogging {

  val users = TableQuery[UserTable]

  def save(user: User): Future[Int] = { db.run(users += user).mapTo[Int] }

  def get(id: Int) = { db.run(users.filter(_.id === id).take(1).result) }

  def createTables(): Future[Unit] = {
    db.run(DBIO.seq(users.schema.create))
  }
  
  def list = {
      val allUsers = for(o <- users) yield o 
      db.run(allUsers.result) 
  }

}
