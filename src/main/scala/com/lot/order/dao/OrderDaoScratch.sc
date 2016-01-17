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
	
	Await.result(TradeDao.createTables(), 5 seconds)
                                                  //> SLF4J: Class path contains multiple SLF4J bindings.
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
                                                  //| 01/16 20:44:05 DEBUG[main] s.b.D.action - #1: [fused] andThen
                                                  //|       1: schema.create [create table `trades` (`id` BIGINT NOT NULL AUTO_INC
                                                  //| REMENT PRIMARY KEY,`trade_date` TIMESTAMP NOT NULL,`settlement_date` TIMESTA
                                                  //| MP NOT NULL,`security_id` BIGINT NOT NULL,`quantity` DOUBLE NOT NULL,`price`
                                                  //|  DOUBLE NOT N
                                                  //| Output exceeds cutoff limit.
  val orders = OrderDao.list                      //> 01/16 20:44:06 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`ex
                                                  //| change`, x2.`buy_sell`, x2.`order_type`, x2.`user_id`, x2.`security_id`, x2.
                                                  //| `quantity`, x2.`unfilled_qty`, x2.`price`, x2.`created_at`, x2.`updated_at` 
                                                  //| from `orders` x2 order by x2.`id` desc]
                                                  //| orders  : scala#21.concurrent#2756.Future#17948[Seq#3027[com#9.lot#17371.ord
                                                  //| er#17480.model#20431.Order#20434]] = scala.concurrent.impl.Promise$DefaultPr
                                                  //| omise@7a96cf9

  val s = Await.result(orders, Duration.Inf)      //> s  : Seq#3027[com#9.lot#17371.order#17480.model#20431.Order#20434] = Vector(
                                                  //| Order(Some(10),NASDAQ,SELL,MARKET,1,10,120.0,120.0,103.3,Some(2016-01-16T20:
                                                  //| 42:48.537+05:30),Some(2016-01-16T20:42:48.537+05:30)), Order(Some(9),NYSE,BU
                                                  //| Y,LIMIT,1,10,150.0,150.0,98.3,Some(2016-01-16T20:42:48.545+05:30),Some(2016-
                                                  //| 01-16T20:42:48.545+05:30)), Order(Some(8),NASDAQ,BUY,MARKET,1,10,100.0,100.0
                                                  //| ,102.3,Some(2016-01-16T20:42:47.476+05:30),Some(2016-01-16T20:42:47.476+05:3
                                                  //| 0)), Order(Some(7),NYSE,BUY,MARKET,29,10,100.0,0.0,100.0,Some(2016-01-16T20:
                                                  //| 41:07.050+05:30),Some(2016-01-16T20:41:07.468+05:30)), Order(Some(6),NASDAQ,
                                                  //| BUY,MARKET,1,10,100.0,100.0,102.3,Some(2016-01-16T20:38:56.938+05:30),Some(2
                                                  //| 016-01-16T20:38:56.938+05:30)), Order(Some(5),NYSE,BUY,LIMIT,1,10,150.0,150.
                                                  //| 0,98.3,Some(2016-01-16T20:38:57.992+05:30),Some(2016-01-16T20:38:57.992+05:3
                                                  //| 0)), Order(Some(4),NASDAQ,SELL,MARKET,1,10,120.0,20.0,103.3,Some(2016-01-16T
                                                  //| 20:38:57.991+05:30),Some
                                                  //| Output exceeds cutoff limit.
  val l = new ListBuffer[Order]                   //> l  : scala#21.collection#2758.mutable#5845.ListBuffer#21851[com#9.lot#17371.
                                                  //| order#17480.model#20431.Order#20434] = ListBuffer()
  l ++= s                                         //> res0: com#9.lot#17371.order#17480.dao#20429.OrderDaoScratch#3063013.l#136526
                                                  //| 67.type = ListBuffer(Order(Some(10),NASDAQ,SELL,MARKET,1,10,120.0,120.0,103.
                                                  //| 3,Some(2016-01-16T20:42:48.537+05:30),Some(2016-01-16T20:42:48.537+05:30)), 
                                                  //| Order(Some(9),NYSE,BUY,LIMIT,1,10,150.0,150.0,98.3,Some(2016-01-16T20:42:48.
                                                  //| 545+05:30),Some(2016-01-16T20:42:48.545+05:30)), Order(Some(8),NASDAQ,BUY,MA
                                                  //| RKET,1,10,100.0,100.0,102.3,Some(2016-01-16T20:42:47.476+05:30),Some(2016-01
                                                  //| -16T20:42:47.476+05:30)), Order(Some(7),NYSE,BUY,MARKET,29,10,100.0,0.0,100.
                                                  //| 0,Some(2016-01-16T20:41:07.050+05:30),Some(2016-01-16T20:41:07.468+05:30)), 
                                                  //| Order(Some(6),NASDAQ,BUY,MARKET,1,10,100.0,100.0,102.3,Some(2016-01-16T20:38
                                                  //| :56.938+05:30),Some(2016-01-16T20:38:56.938+05:30)), Order(Some(5),NYSE,BUY,
                                                  //| LIMIT,1,10,150.0,150.0,98.3,Some(2016-01-16T20:38:57.992+05:30),Some(2016-01
                                                  //| -16T20:38:57.992+05:30)), Order(Some(4),NASDAQ,SELL,MARKET,1,10,120.0,20.0,1
                                                  //| 03.3,Some(2016-01-16T20:
                                                  //| Output exceeds cutoff limit.
  println(l)                                      //> ListBuffer(Order(Some(10),NASDAQ,SELL,MARKET,1,10,120.0,120.0,103.3,Some(201
                                                  //| 6-01-16T20:42:48.537+05:30),Some(2016-01-16T20:42:48.537+05:30)), Order(Some
                                                  //| (9),NYSE,BUY,LIMIT,1,10,150.0,150.0,98.3,Some(2016-01-16T20:42:48.545+05:30)
                                                  //| ,Some(2016-01-16T20:42:48.545+05:30)), Order(Some(8),NASDAQ,BUY,MARKET,1,10,
                                                  //| 100.0,100.0,102.3,Some(2016-01-16T20:42:47.476+05:30),Some(2016-01-16T20:42:
                                                  //| 47.476+05:30)), Order(Some(7),NYSE,BUY,MARKET,29,10,100.0,0.0,100.0,Some(201
                                                  //| 6-01-16T20:41:07.050+05:30),Some(2016-01-16T20:41:07.468+05:30)), Order(Some
                                                  //| (6),NASDAQ,BUY,MARKET,1,10,100.0,100.0,102.3,Some(2016-01-16T20:38:56.938+05
                                                  //| :30),Some(2016-01-16T20:38:56.938+05:30)), Order(Some(5),NYSE,BUY,LIMIT,1,10
                                                  //| ,150.0,150.0,98.3,Some(2016-01-16T20:38:57.992+05:30),Some(2016-01-16T20:38:
                                                  //| 57.992+05:30)), Order(Some(4),NASDAQ,SELL,MARKET,1,10,120.0,20.0,103.3,Some(
                                                  //| 2016-01-16T20:38:57.991+05:30),Some(2016-01-16T20:41:07.504+05:30)))

  val o = l.headOption                            //> o  : Option#1941[com#9.lot#17371.order#17480.model#20431.Order#20434] = Some
                                                  //| (Order(Some(10),NASDAQ,SELL,MARKET,1,10,120.0,120.0,103.3,Some(2016-01-16T20
                                                  //| :42:48.537+05:30),Some(2016-01-16T20:42:48.537+05:30)))
  o match {
    case Some(order) => order.unfilled_qty = 777
  }

  println(l)                                      //> ListBuffer(Order(Some(10),NASDAQ,SELL,MARKET,1,10,120.0,777.0,103.3,Some(201
                                                  //| 6-01-16T20:42:48.537+05:30),Some(2016-01-16T20:42:48.537+05:30)), Order(Some
                                                  //| (9),NYSE,BUY,LIMIT,1,10,150.0,150.0,98.3,Some(2016-01-16T20:42:48.545+05:30)
                                                  //| ,Some(2016-01-16T20:42:48.545+05:30)), Order(Some(8),NASDAQ,BUY,MARKET,1,10,
                                                  //| 100.0,100.0,102.3,Some(2016-01-16T20:42:47.476+05:30),Some(2016-01-16T20:42:
                                                  //| 47.476+05:30)), Order(Some(7),NYSE,BUY,MARKET,29,10,100.0,0.0,100.0,Some(201
                                                  //| 6-01-16T20:41:07.050+05:30),Some(2016-01-16T20:41:07.468+05:30)), Order(Some
                                                  //| (6),NASDAQ,BUY,MARKET,1,10,100.0,100.0,102.3,Some(2016-01-16T20:38:56.938+05
                                                  //| :30),Some(2016-01-16T20:38:56.938+05:30)), Order(Some(5),NYSE,BUY,LIMIT,1,10
                                                  //| ,150.0,150.0,98.3,Some(2016-01-16T20:38:57.992+05:30),Some(2016-01-16T20:38:
                                                  //| 57.992+05:30)), Order(Some(4),NASDAQ,SELL,MARKET,1,10,120.0,20.0,103.3,Some(
                                                  //| 2016-01-16T20:38:57.991+05:30),Some(2016-01-16T20:41:07.504+05:30)))
  println(l(0).unfilled_qty)                      //> 777.0

  val o1 = OrderFactory.generate(security_id = 11)//> loading application.dev.conf
                                                  //| [DEBUG] [01/16/2016 20:44:06.878] [main] [EventStream(akka://lot-om)] logger
                                                  //|  log1-Logging$DefaultLogger started
                                                  //| [DEBUG] [01/16/2016 20:44:06.879] [main] [EventStream(akka://lot-om)] Defaul
                                                  //| t Loggers started
                                                  //| 01/16 20:44:06 INFO [main] c.l.e.Exchange$ - Started exchange NASDAQ on akka
                                                  //| ://lot-om/user/NASDAQ
                                                  //| 01/16 20:44:06 INFO [main] c.l.e.Exchange$ - Started exchange NYSE on akka:/
                                                  //| /lot-om/user/NYSE
                                                  //| o1  : com#9.lot#17371.order#17480.model#20431.Order#20434 = Order(None,NASDA
                                                  //| Q,BUY,MARKET,26,11,8000.0,0.0,0.0,None,None)

  val saved = Await.result(OrderDao.save(o1), 5 seconds)
                                                  //> 01/16 20:44:06 DEBUG[main] c.l.o.d.OrderDao$ - Saving Order(None,NASDAQ,BUY,
                                                  //| MARKET,26,11,8000.0,0.0,0.0,None,None)
                                                  //| 01/16 20:44:06 DEBUG[main] s.b.D.action - #1: += [insert into `orders` (`exc
                                                  //| hange`,`buy_sell`,`order_type`,`user_id`,`security_id`,`quantity`,`unfilled_
                                                  //| qty`,`price`,`created_at`,`updated_at`)  values (?,?,?,?,?,?,?,?,?,?)]
                                                  //| saved  : com#9.lot#17371.order#17480.model#20431.Order#20434 = Order(Some(11
                                                  //| ),NASDAQ,BUY,MARKET,26,11,8000.0,0.0,0.0,Some(2016-01-16T20:44:06.940+05:30)
                                                  //| ,Some(2016-01-16T20:44:06.940+05:30))
  val o2 = Await.result(OrderDao.get(saved.id.get), 5 seconds)
                                                  //> 01/16 20:44:07 DEBUG[main] s.b.D.action - #1: StreamingInvokerAction$HeadOpt
                                                  //| ionAction [select x2.`id`, x2.`exchange`, x2.`buy_sell`, x2.`order_type`, x2
                                                  //| .`user_id`, x2.`security_id`, x2.`quantity`, x2.`unfilled_qty`, x2.`price`, 
                                                  //| x2.`created_at`, x2.`updated_at` from `orders` x2 where x2.`id` = 11]
                                                  //| o2  : Option#1941[com#9.lot#17371.order#17480.model#20431.Order#20434] = Som
                                                  //| e(Order(Some(11),NASDAQ,BUY,MARKET,26,11,8000.0,0.0,0.0,Some(2016-01-16T20:4
                                                  //| 4:06.940+05:30),Some(2016-01-16T20:44:06.940+05:30)))
  println(saved == o2.get)                        //> true-

}