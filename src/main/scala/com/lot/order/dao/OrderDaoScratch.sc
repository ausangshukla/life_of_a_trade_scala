package com.lot.order.dao

import scala.concurrent.Await
import scala.concurrent.duration._
import com.lot.security.dao.SecurityDao
import com.lot.order.dao.OrderDao
import org.joda.time.DateTime
import com.lot.order.model._
import com.lot.generators.OrderFactory
import scala.collection.mutable.ListBuffer

object OrderDaoScratch {
  val orders = OrderDao.list                      //> SLF4J: Class path contains multiple SLF4J bindings.
                                                  //| SLF4J: Found binding in [jar:file:/home/thimmaiah/tools/activator-dist-1.3.5
                                                  //| /repository/ch.qos.logback/logback-classic/1.1.3/jars/logback-classic.jar!/o
                                                  //| rg/slf4j/impl/StaticLoggerBinder.class]
                                                  //| SLF4J: Found binding in [jar:file:/home/thimmaiah/tools/activator-dist-1.3.5
                                                  //| /repository/org.slf4j/slf4j-nop/1.6.4/jars/slf4j-nop.jar!/org/slf4j/impl/Sta
                                                  //| ticLoggerBinder.class]
                                                  //| SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanat
                                                  //| ion.
                                                  //| SLF4J: Actual binding is of type [ch.qos.logback.classic.util.ContextSelecto
                                                  //| rStaticBinder]
                                                  //| loading application.dev.conf
                                                  //| 01/15 09:04:08 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`ex
                                                  //| change`, x2.`buy_sell`, x2.`order_type`, x2.`user_id`, x2.`security_id`, x2.
                                                  //| `quantity`, x2.`unfilled_qty`, x2.`price`, x2.`created_at`, x2.`updated_at` 
                                                  //| from `orders` x2 order by x2.`id` desc]
                                                  //| orders  : scala.concurrent.Future[S
                                                  //| Output exceeds cutoff limit.

  val s = Await.result(orders, Duration.Inf)      //> s  : Seq[com.lot.order.model.Order] = Vector(Order(Some(36),NYSE,BUY,LIMIT,9
                                                  //| ,11,6000.0,0.0,800.0,Some(2016-01-15T08:52:57.926+05:30),Some(2016-01-15T08:
                                                  //| 52:57.926+05:30)))
  val l = new ListBuffer[Order]                   //> l  : scala.collection.mutable.ListBuffer[com.lot.order.model.Order] = ListBu
                                                  //| ffer()
  l ++= s                                         //> res0: com.lot.order.dao.OrderDaoScratch.l.type = ListBuffer(Order(Some(36),N
                                                  //| YSE,BUY,LIMIT,9,11,6000.0,0.0,800.0,Some(2016-01-15T08:52:57.926+05:30),Some
                                                  //| (2016-01-15T08:52:57.926+05:30)))
  println(l)                                      //> ListBuffer(Order(Some(36),NYSE,BUY,LIMIT,9,11,6000.0,0.0,800.0,Some(2016-01-
                                                  //| 15T08:52:57.926+05:30),Some(2016-01-15T08:52:57.926+05:30)))

  val o = l.headOption                            //> o  : Option[com.lot.order.model.Order] = Some(Order(Some(36),NYSE,BUY,LIMIT,
                                                  //| 9,11,6000.0,0.0,800.0,Some(2016-01-15T08:52:57.926+05:30),Some(2016-01-15T08
                                                  //| :52:57.926+05:30)))
	o match {
		case Some(order) => order.unfilled_qty = 777
	}
  

  println(l)                                      //> ListBuffer(Order(Some(36),NYSE,BUY,LIMIT,9,11,6000.0,777.0,800.0,Some(2016-0
                                                  //| 1-15T08:52:57.926+05:30),Some(2016-01-15T08:52:57.926+05:30)))
  println(l(0).unfilled_qty)                      //> 777.0

  // val o1 = OrderFactory.generate(security_id = 11)

  // val saved = Await.result(OrderDao.save(o1), 5 seconds)
  // val o2 = Await.result(OrderDao.get(saved.id.get), 5 seconds)
  // println(saved == o2.get)

}