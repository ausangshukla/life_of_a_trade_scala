package com.lot.order.dao

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import slick.driver.JdbcProfile
import com.lot.utils.DbModule 
import scala.concurrent.Future
import com.lot.order.model.OrderTable
import slick.driver.MySQLDriver.api._
import com.lot.order.model.Order
import com.lot.utils.DB._
import scala.concurrent.ExecutionContext.Implicits.global
import org.joda.time.DateTime

object OrderDao extends TableQuery(new OrderTable(_)) {

  def save(order: Order): Future[Int] = {
    val now = new DateTime()
    val o:Order = order.copy(created_at=Some(now), updated_at=Some(now))
    db.run(this += o).mapTo[Int] 
  }

  def get(id: Long) = {
    db.run(this.filter(_.id === id).result.headOption)
  }
  

  def update(order: Order) = {
    val now = new DateTime()
    val o:Order = order.copy(updated_at=Some(now))    
    db.run(this.filter(_.id === order.id).update(o))
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
