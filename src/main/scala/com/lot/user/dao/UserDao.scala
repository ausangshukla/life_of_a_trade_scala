package com.lot.user.dao

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import slick.driver.JdbcProfile
import com.lot.utils.DbModule 
import scala.concurrent.Future
import com.lot.user.model.UserTable
import slick.driver.MySQLDriver.api._
import com.lot.user.model.User
import com.lot.utils.DB._
import scala.concurrent.ExecutionContext.Implicits.global

object UserDao extends TableQuery(new UserTable(_)) {

  def save(user: User): Future[Int] = { 
    db.run(this += user).mapTo[Int] 
  }

  def get(id: Long) = {
    db.run(this.filter(_.id === id).result.headOption)
  }
  
  def findByEmail(email: String) = {
    db.run(this.filter(_.email === email).result.headOption)
  }
  
  def update(user: User) = {
    db.run(this.filter(_.id === user.id).update(user))
  }
  def delete(user: User) = {
    db.run(this.filter(_.id === user.id).delete)
  }
  
  def delete(delete_id: Long) = {
    db.run(this.filter(_.id === delete_id).delete)
  }

  def createTables(): Future[Unit] = {
    db.run(DBIO.seq(this.schema.create))
  }

  def list = {
    val allUsers = for (o <- this) yield o
    db.run(allUsers.result)
  }

}
