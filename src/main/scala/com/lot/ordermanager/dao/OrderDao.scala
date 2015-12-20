package com.lot.ordermanager.dao

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import persistence.entities.{ Suppliers, Supplier }
import slick.driver.JdbcProfile
import utils.{ DbModule }
import scala.concurrent.Future
import com.lot.ordermanager.model.OrderTable
import slick.driver.MySQLDriver.api._
import com.lot.ordermanager.model.Order
import utils.DB._

class OrderDao() extends LazyLogging {

  val orders = TableQuery[OrderTable]

  def save(order: Order): Future[Int] = { db.run(orders += order).mapTo[Int] }

  def get(id: Int) = { db.run(orders.filter(_.id === id).take(1).result) }

  def createTables(): Future[Unit] = {
    db.run(DBIO.seq(orders.schema.create))
  }
  
  def list = {
      val allOrders = for(o <- orders) yield o 
      db.run(allOrders.result) 
  }

}
