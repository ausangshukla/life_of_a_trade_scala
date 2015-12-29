package com.lot.order.dao

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import persistence.entities.{ Suppliers, Supplier }
import slick.driver.JdbcProfile
import utils.{ DbModule }
import scala.concurrent.Future
import com.lot.order.model.OrderTable
import slick.driver.MySQLDriver.api._
import com.lot.order.model.Order
import utils.DB._
import scala.concurrent.ExecutionContext.Implicits.global

object OrderDao extends TableQuery(new OrderTable(_)) {

  def save(order: Order): Future[Int] = { db.run(this += order).mapTo[Int] }

  def get(id: Long) = {
    db.run(this.filter(_.id === id).result.headOption)
  }
  

  def update(order: Order) = {
    db.run(this.filter(_.id === order.id).update(order))
  }
  def delete(order: Order) = {
    db.run(this.filter(_.id === order.id).delete)
  }
  
  def delete(delete_id: Long) = {
    db.run(this.filter(_.id === delete_id).delete)
  }

  def createTables(): Future[Unit] = {
    db.run(DBIO.seq(this.schema.create))
  }

  def list = {
    val allOrders = for (o <- this) yield o
    db.run(allOrders.result)
  }

}
