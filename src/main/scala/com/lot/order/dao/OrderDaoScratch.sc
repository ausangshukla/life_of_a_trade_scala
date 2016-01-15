package com.lot.order.dao

import scala.concurrent.Await
import scala.concurrent.duration._
import com.lot.security.dao.SecurityDao
import com.lot.order.dao.OrderDao
import org.joda.time.DateTime
import com.lot.order.model._
import com.lot.generators.OrderFactory

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
                                                  //| 01/15 08:15:43 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`ex
                                                  //| change`, x2.`buy_sell`, x2.`order_type`, x2.`user_id`, x2.`security_id`, x2.
                                                  //| `quantity`, x2.`unfilled_qty`, x2.`price`, x2.`created_at`, x2.`updated_at` 
                                                  //| from `orders` x2 order by x2.`id` desc]
                                                  //| orders  : scala.concurrent.Future[S
                                                  //| Output exceeds cutoff limit.

  //val s = Await.result(orders, 5 seconds)

  val o1 = OrderFactory.generate(security_id = 11)//> loading application.dev.conf
                                                  //| [DEBUG] [01/15/2016 08:15:43.964] [main] [EventStream(akka://lot-om)] logger
                                                  //|  log1-Logging$DefaultLogger started
                                                  //| [DEBUG] [01/15/2016 08:15:43.965] [main] [EventStream(akka://lot-om)] Defaul
                                                  //| t Loggers started
                                                  //| 01/15 08:15:43 INFO [main] c.l.e.Exchange$ - Started exchange NASDAQ on akka
                                                  //| ://lot-om/user/NASDAQ
                                                  //| 01/15 08:15:43 INFO [main] c.l.e.Exchange$ - Started exchange NYSE on akka:/
                                                  //| /lot-om/user/NYSE
                                                  //| o1  : com.lot.order.model.Order = Order(None,NYSE,SELL,MARKET,33,11,8000.0,0
                                                  //| .0,500.0,None,None)

  val saved = Await.result(OrderDao.save(o1), 5 seconds)
                                                  //> 01/15 08:15:43 DEBUG[main] c.l.o.d.OrderDao$ - Saving Order(None,NYSE,SELL,M
                                                  //| ARKET,33,11,8000.0,0.0,500.0,None,None)
                                                  //| 01/15 08:15:43 DEBUG[main] s.b.D.action - #1: += [insert into `orders` (`exc
                                                  //| hange`,`buy_sell`,`order_type`,`user_id`,`security_id`,`quantity`,`unfilled_
                                                  //| qty`,`price`,`created_at`,`updated_at`)  values (?,?,?,?,?,?,?,?,?,?)]
                                                  //| saved  : <error> = Order(Some(38),NYSE,SELL,MARKET,33,11,8000.0,0.0,500.0,So
                                                  //| me(2016-01-15T08:15:43.985+05:30),Some(2016-01-15T08:15:43.985+05:30))
  val o2 = Await.result(OrderDao.get(saved.id.get), 5 seconds)
                                                  //> 01/15 08:15:44 DEBUG[main] s.b.D.action - #1: StreamingInvokerAction$HeadOpt
                                                  //| ionAction [select x2.`id`, x2.`exchange`, x2.`buy_sell`, x2.`order_type`, x2
                                                  //| .`user_id`, x2.`security_id`, x2.`quantity`, x2.`unfilled_qty`, x2.`price`, 
                                                  //| x2.`created_at`, x2.`updated_at` from `orders` x2 where x2.`id` = 38]
                                                  //| o2  : <error> = Some(Order(Some(38),NYSE,SELL,MARKET,33,11,8000.0,0.0,500.0,
                                                  //| Some(2016-01-15T08:15:43.985+05:30),Some(2016-01-15T08:15:43.985+05:30)))
 println(saved == o2.get)                         //> true/
 
}