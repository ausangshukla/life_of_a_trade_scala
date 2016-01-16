package com.lot.boot

import com.lot.exchange.UnfilledOrderManager
import com.lot.order.dao.OrderDao
import com.lot.generators.OrderFactory
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object ExchangeScratch {

	for(i <- 1 to 10) {
		val o = OrderFactory.generate(security_id=10, quantity=100, unfilled_qty=100)
		//Await.result(OrderDao.save(o), Duration.Inf)
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
                                                  //| [DEBUG] [01/15/2016 09:56:20.580] [main] [EventStream(akka://lot-om)] logger
                                                  //|  log1-Logging$DefaultLogger started
                                                  //| [DEBUG] [01/15/2016 09:56:20.580] [main] [EventStream(akka://lot-om)] Defaul
                                                  //| t Loggers started
                                                  //| 01/15 09:56:20 INFO [main] c.l.e.Exchange$ - Started exchange NASDAQ on akka
                                                  //| ://lot-om/user/NA
                                                  //| Output exceeds cutoff limit.
	}
  val uom = UnfilledOrderManager(10)              //> 01/15 09:56:23 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`ex
                                                  //| change`, x2.`buy_sell`, x2.`order_type`, x2.`user_id`, x2.`security_id`, x2.
                                                  //| `quantity`, x2.`unfilled_qty`, x2.`price`, x2.`created_at`, x2.`updated_at` 
                                                  //| from `orders` x2 where ((x2.`unfilled_qty` > 0.0) and (x2.`security_id` = 10
                                                  //| )) and (x2.`buy_sell` = 'BUY') order by x2.`price` desc, x2.`id` desc]
                                                  //| 01/15 09:56:23 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`ex
                                                  //| change`, x2.`buy_sell`, x2.`order_type`, x2.`user_id`, x2.`security_id`, x2.
                                                  //| `quantity`, x2.`unfilled_qty`, x2.`price`, x2.`created_at`, x2.`updated_at` 
                                                  //| from `orders` x2 where ((x2.`unfilled_qty` > 0.0) and (x2.`security_id` = 10
                                                  //| )) and (x2.`buy_sell` = 'SELL') order by x2.`price`, x2.`id` desc]
                                                  //| uom  : com.lot.exchange.UnfilledOrderManager = com.lot.exchange.UnfilledOrde
                                                  //| rManager@645f9828
  uom.buys                                        //> res0: scala.collection.mutable.ListBuffer[com.lot.order.model.Order] = ListB
                                                  //| uffer(Order(Some(62),NYSE,BUY,MARKET,46,10,100.0,100.0,900.0,Some(2016-01-15
                                                  //| T09:56:22.481+05:30),Some(2016-01-15T09:56:22.481+05:30)), Order(Some(57),NA
                                                  //| SDAQ,BUY,MARKET,19,10,100.0,100.0,700.0,Some(2016-01-15T09:56:20.615+05:30),
                                                  //| Some(2016-01-15T09:56:20.615+05:30)), Order(Some(66),NYSE,BUY,MARKET,19,10,1
                                                  //| 00.0,100.0,600.0,Some(2016-01-15T09:56:22.782+05:30),Some(2016-01-15T09:56:2
                                                  //| 2.782+05:30)), Order(Some(65),NYSE,BUY,MARKET,21,10,100.0,100.0,600.0,Some(2
                                                  //| 016-01-15T09:56:22.738+05:30),Some(2016-01-15T09:56:22.738+05:30)), Order(So
                                                  //| me(63),NASDAQ,BUY,LIMIT,39,10,100.0,100.0,300.0,Some(2016-01-15T09:56:22.592
                                                  //| +05:30),Some(2016-01-15T09:56:22.592+05:30)))|
}