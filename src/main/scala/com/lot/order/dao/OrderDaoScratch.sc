package com.lot.order.dao

import scala.concurrent.Await
import scala.concurrent.duration._
import com.lot.security.dao.SecurityDao
import com.lot.order.dao.OrderDao
import org.joda.time.DateTime
import com.lot.order.model._

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
                                                  //| 12/31 12:41:27 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`ex
                                                  //| change`, x2.`buy_sell`, x2.`order_type`, x2.`user_id`, x2.`security_id`, x2.
                                                  //| `quantity`, x2.`price`, x2.`created_at`, x2.`updated_at` from `orders` x2 or
                                                  //| der by x2.`id` desc]
                                                  //| orders  : scala.concurrent.Future[Seq[com.lot.order.model.Order]] = scala.co
                                                  //| ncurre
                                                  //| Output exceeds cutoff limit.
  val s = Await.result(orders, 5 seconds)         //> s  : Seq[com.lot.order.model.Order] = Vector(Order(Some(96),NYSE,SELL,MARKET
                                                  //| ,26,10,100.0,101.11,Some(2015-12-31T12:24:33.000+05:30),Some(2015-12-31T12:2
                                                  //| 4:33.000+05:30)), Order(Some(95),NYSE,BUY,MARKET,26,10,100.0,100.0,Some(2015
                                                  //| -12-31T10:02:38.000+05:30),Some(2015-12-31T10:02:38.000+05:30)), Order(Some(
                                                  //| 94),NYSE,BUY,LIMIT,1,10,150.0,98.3,Some(2015-12-31T10:02:26.000+05:30),Some(
                                                  //| 2015-12-31T10:02:26.000+05:30)), Order(Some(93),NASDAQ,SELL,MARKET,1,10,120.
                                                  //| 0,103.3,Some(2015-12-31T10:02:26.000+05:30),Some(2015-12-31T10:02:26.000+05:
                                                  //| 30)), Order(Some(92),NASDAQ,BUY,MARKET,1,10,100.0,102.3,Some(2015-12-31T10:0
                                                  //| 2:26.000+05:30),Some(2015-12-31T10:02:26.000+05:30)), Order(Some(91),NYSE,BU
                                                  //| Y,LIMIT,1,10,150.0,98.3,Some(2015-12-31T09:59:37.000+05:30),Some(2015-12-31T
                                                  //| 09:59:37.000+05:30)), Order(Some(90),NASDAQ,SELL,MARKET,1,10,120.0,103.3,Som
                                                  //| e(2015-12-31T09:59:37.000+05:30),Some(2015-12-31T09:59:37.000+05:30)), Order
                                                  //| (Some(89),NASDAQ,BUY,MAR
                                                  //| Output exceeds cutoff limit.
}