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
                                                  //| 01/16 13:56:48 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`ex
                                                  //| change`, x2.`buy_sell`, x2.`order_type`, x2.`user_id`, x2.`security_id`, x2.
                                                  //| `quantity`, x2.`unfilled_qty`, x2.`price`, x2.`created_at`, x2.`updated_at` 
                                                  //| from `orders` x2 order by x2.`id` desc]
                                                  //| orders  : scala.concurrent.Future[S
                                                  //| Output exceeds cutoff limit.

  val s = Await.result(orders, Duration.Inf)      //> s  : Seq[com.lot.order.model.Order] = Vector(Order(Some(66),NYSE,BUY,MARKET,
                                                  //| 19,10,100.0,100.0,600.0,Some(2016-01-15T09:56:22.782+05:30),Some(2016-01-15T
                                                  //| 09:56:22.782+05:30)), Order(Some(65),NYSE,BUY,MARKET,21,10,100.0,100.0,600.0
                                                  //| ,Some(2016-01-15T09:56:22.738+05:30),Some(2016-01-15T09:56:22.738+05:30)), O
                                                  //| rder(Some(64),NASDAQ,SELL,MARKET,7,10,100.0,100.0,1000.0,Some(2016-01-15T09:
                                                  //| 56:22.692+05:30),Some(2016-01-15T09:56:22.692+05:30)), Order(Some(63),NASDAQ
                                                  //| ,BUY,LIMIT,39,10,100.0,100.0,300.0,Some(2016-01-15T09:56:22.592+05:30),Some(
                                                  //| 2016-01-15T09:56:22.592+05:30)), Order(Some(62),NYSE,BUY,MARKET,46,10,100.0,
                                                  //| 100.0,900.0,Some(2016-01-15T09:56:22.481+05:30),Some(2016-01-15T09:56:22.481
                                                  //| +05:30)), Order(Some(61),NASDAQ,SELL,LIMIT,34,10,100.0,100.0,800.0,Some(2016
                                                  //| -01-15T09:56:22.392+05:30),Some(2016-01-15T09:56:22.392+05:30)), Order(Some(
                                                  //| 60),NYSE,SELL,MARKET,7,10,100.0,100.0,700.0,Some(2016-01-15T09:56:22.348+05:
                                                  //| 30),Some(2016-01-15T09:5
                                                  //| Output exceeds cutoff limit.
  val l = new ListBuffer[Order]                   //> l  : scala.collection.mutable.ListBuffer[com.lot.order.model.Order] = ListBu
                                                  //| ffer()
  l ++= s                                         //> res0: com.lot.order.dao.OrderDaoScratch.l.type = ListBuffer(Order(Some(66),N
                                                  //| YSE,BUY,MARKET,19,10,100.0,100.0,600.0,Some(2016-01-15T09:56:22.782+05:30),S
                                                  //| ome(2016-01-15T09:56:22.782+05:30)), Order(Some(65),NYSE,BUY,MARKET,21,10,10
                                                  //| 0.0,100.0,600.0,Some(2016-01-15T09:56:22.738+05:30),Some(2016-01-15T09:56:22
                                                  //| .738+05:30)), Order(Some(64),NASDAQ,SELL,MARKET,7,10,100.0,100.0,1000.0,Some
                                                  //| (2016-01-15T09:56:22.692+05:30),Some(2016-01-15T09:56:22.692+05:30)), Order(
                                                  //| Some(63),NASDAQ,BUY,LIMIT,39,10,100.0,100.0,300.0,Some(2016-01-15T09:56:22.5
                                                  //| 92+05:30),Some(2016-01-15T09:56:22.592+05:30)), Order(Some(62),NYSE,BUY,MARK
                                                  //| ET,46,10,100.0,100.0,900.0,Some(2016-01-15T09:56:22.481+05:30),Some(2016-01-
                                                  //| 15T09:56:22.481+05:30)), Order(Some(61),NASDAQ,SELL,LIMIT,34,10,100.0,100.0,
                                                  //| 800.0,Some(2016-01-15T09:56:22.392+05:30),Some(2016-01-15T09:56:22.392+05:30
                                                  //| )), Order(Some(60),NYSE,SELL,MARKET,7,10,100.0,100.0,700.0,Some(2016-01-15T0
                                                  //| 9:56:22.348+05:30),Some(
                                                  //| Output exceeds cutoff limit.
  println(l)                                      //> ListBuffer(Order(Some(66),NYSE,BUY,MARKET,19,10,100.0,100.0,600.0,Some(2016-
                                                  //| 01-15T09:56:22.782+05:30),Some(2016-01-15T09:56:22.782+05:30)), Order(Some(6
                                                  //| 5),NYSE,BUY,MARKET,21,10,100.0,100.0,600.0,Some(2016-01-15T09:56:22.738+05:3
                                                  //| 0),Some(2016-01-15T09:56:22.738+05:30)), Order(Some(64),NASDAQ,SELL,MARKET,7
                                                  //| ,10,100.0,100.0,1000.0,Some(2016-01-15T09:56:22.692+05:30),Some(2016-01-15T0
                                                  //| 9:56:22.692+05:30)), Order(Some(63),NASDAQ,BUY,LIMIT,39,10,100.0,100.0,300.0
                                                  //| ,Some(2016-01-15T09:56:22.592+05:30),Some(2016-01-15T09:56:22.592+05:30)), O
                                                  //| rder(Some(62),NYSE,BUY,MARKET,46,10,100.0,100.0,900.0,Some(2016-01-15T09:56:
                                                  //| 22.481+05:30),Some(2016-01-15T09:56:22.481+05:30)), Order(Some(61),NASDAQ,SE
                                                  //| LL,LIMIT,34,10,100.0,100.0,800.0,Some(2016-01-15T09:56:22.392+05:30),Some(20
                                                  //| 16-01-15T09:56:22.392+05:30)), Order(Some(60),NYSE,SELL,MARKET,7,10,100.0,10
                                                  //| 0.0,700.0,Some(2016-01-15T09:56:22.348+05:30),Some(2016-01-15T09:56:22.348+0
                                                  //| 5:30)), Order(Some(59),N
                                                  //| Output exceeds cutoff limit.

  val o = l.headOption                            //> o  : Option[com.lot.order.model.Order] = Some(Order(Some(66),NYSE,BUY,MARKET
                                                  //| ,19,10,100.0,100.0,600.0,Some(2016-01-15T09:56:22.782+05:30),Some(2016-01-15
                                                  //| T09:56:22.782+05:30)))
  o match {
    case Some(order) => order.unfilled_qty = 777
  }

  println(l)                                      //> ListBuffer(Order(Some(66),NYSE,BUY,MARKET,19,10,100.0,777.0,600.0,Some(2016-
                                                  //| 01-15T09:56:22.782+05:30),Some(2016-01-15T09:56:22.782+05:30)), Order(Some(6
                                                  //| 5),NYSE,BUY,MARKET,21,10,100.0,100.0,600.0,Some(2016-01-15T09:56:22.738+05:3
                                                  //| 0),Some(2016-01-15T09:56:22.738+05:30)), Order(Some(64),NASDAQ,SELL,MARKET,7
                                                  //| ,10,100.0,100.0,1000.0,Some(2016-01-15T09:56:22.692+05:30),Some(2016-01-15T0
                                                  //| 9:56:22.692+05:30)), Order(Some(63),NASDAQ,BUY,LIMIT,39,10,100.0,100.0,300.0
                                                  //| ,Some(2016-01-15T09:56:22.592+05:30),Some(2016-01-15T09:56:22.592+05:30)), O
                                                  //| rder(Some(62),NYSE,BUY,MARKET,46,10,100.0,100.0,900.0,Some(2016-01-15T09:56:
                                                  //| 22.481+05:30),Some(2016-01-15T09:56:22.481+05:30)), Order(Some(61),NASDAQ,SE
                                                  //| LL,LIMIT,34,10,100.0,100.0,800.0,Some(2016-01-15T09:56:22.392+05:30),Some(20
                                                  //| 16-01-15T09:56:22.392+05:30)), Order(Some(60),NYSE,SELL,MARKET,7,10,100.0,10
                                                  //| 0.0,700.0,Some(2016-01-15T09:56:22.348+05:30),Some(2016-01-15T09:56:22.348+0
                                                  //| 5:30)), Order(Some(59),N
                                                  //| Output exceeds cutoff limit.
  println(l(0).unfilled_qty)                      //> 777.0

  val o1 = OrderFactory.generate(security_id = 11)//> loading application.dev.conf
                                                  //| [DEBUG] [01/16/2016 13:56:49.395] [main] [EventStream(akka://lot-om)] logger
                                                  //|  log1-Logging$DefaultLogger started
                                                  //| [DEBUG] [01/16/2016 13:56:49.403] [main] [EventStream(akka://lot-om)] Defaul
                                                  //| t Loggers started
                                                  //| 01/16 13:56:49 INFO [main] c.l.e.Exchange$ - Started exchange NASDAQ on akka
                                                  //| ://lot-om/user/NASDAQ
                                                  //| 01/16 13:56:49 INFO [main] c.l.e.Exchange$ - Started exchange NYSE on akka:/
                                                  //| /lot-om/user/NYSE
                                                  //| o1  : com.lot.order.model.Order = Order(None,NYSE,SELL,LIMIT,23,11,1000.0,0.
                                                  //| 0,1000.0,None,None)

  val saved = Await.result(OrderDao.save(o1), 5 seconds)
                                                  //> 01/16 13:56:49 DEBUG[main] c.l.o.d.OrderDao$ - Saving Order(None,NYSE,SELL,L
                                                  //| IMIT,23,11,1000.0,0.0,1000.0,None,None)
                                                  //| 01/16 13:56:49 DEBUG[main] s.b.D.action - #1: += [insert into `orders` (`exc
                                                  //| hange`,`buy_sell`,`order_type`,`user_id`,`security_id`,`quantity`,`unfilled_
                                                  //| qty`,`price`,`created_at`,`updated_at`)  values (?,?,?,?,?,?,?,?,?,?)]
                                                  //| saved  : <error> = Order(Some(67),NYSE,SELL,LIMIT,23,11,1000.0,0.0,1000.0,So
                                                  //| me(2016-01-16T13:56:49.443+05:30),Some(2016-01-16T13:56:49.443+05:30))
  val o2 = Await.result(OrderDao.get(saved.id.get), 5 seconds)
                                                  //> 01/16 13:56:49 DEBUG[main] s.b.D.action - #1: StreamingInvokerAction$HeadOpt
                                                  //| ionAction [select x2.`id`, x2.`exchange`, x2.`buy_sell`, x2.`order_type`, x2
                                                  //| .`user_id`, x2.`security_id`, x2.`quantity`, x2.`unfilled_qty`, x2.`price`, 
                                                  //| x2.`created_at`, x2.`updated_at` from `orders` x2 where x2.`id` = 67]
                                                  //| o2  : <error> = Some(Order(Some(67),NYSE,SELL,LIMIT,23,11,1000.0,0.0,1000.0,
                                                  //| Some(2016-01-16T13:56:49.443+05:30),Some(2016-01-16T13:56:49.443+05:30)))
  println (saved == o2.get)                       //> true-

}