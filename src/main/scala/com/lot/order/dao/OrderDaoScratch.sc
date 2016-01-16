package com.lot.order.dao

import scala.concurrent.Await
import scala.concurrent.duration._
import com.lot.security.dao.SecurityDao
import com.lot.order.dao.OrderDao
import org.joda.time.DateTime
import com.lot.order.model._
import com.lot.generators.OrderFactory
import scala.collection.mutable.ListBuffer
import com.lot.trade.dao.TradeDao

object OrderDaoScratch {
  val orders = OrderDao.list

  val s = Await.result(orders, Duration.Inf)
  val l = new ListBuffer[Order]
  l ++= s
  println(l)

  val o = l.headOption
  o match {
    case Some(order) => order.unfilled_qty = 777
  }

  println(l)
  println(l(0).unfilled_qty)

  val o1 = OrderFactory.generate(security_id = 11)

  val saved = Await.result(OrderDao.save(o1), 5 seconds)
  val o2 = Await.result(OrderDao.get(saved.id.get), 5 seconds)
  println(saved == o2.get)

}