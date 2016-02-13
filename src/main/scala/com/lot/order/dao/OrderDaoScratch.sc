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
import com.lot.position.dao.PositionDao
import com.lot.marketEvent.dao.MarketEventDao
import com.lot.marketEvent.dao.TriggeredEventDao
import com.lot.blockAmount.dao.BlockAmountDao

object OrderDaoScratch {

  //Await.result(MarketEventDao.createTables(), 5 seconds)
  Await.result(BlockAmountDao.createTables(), 5 seconds)
                                                  //> SLF4J: Class path contains multiple SLF4J bindings.
                                                  //| SLF4J: Found binding in [jar:file:/home/thimmaiah/tools/activator-dist-1.3.5
                                                  //| /repository/ch.qos.logback/logback-classic/1.1.3/jars/logback-classic.jar!/o
                                                  //| rg/slf4j/impl/StaticLoggerBinder.class]
                                                  //| SLF4J: Found binding in [jar:file:/home/thimmaiah/tools/activator-dist-1.3.5
                                                  //| /repository/org.slf4j/slf4j-nop/1.6.4/jars/slf4j-nop.jar!/org/slf4j/impl/Sta
                                                  //| ticLoggerBinder.class]
                                                  //| SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanat
                                                  //| ion.
                                                  //| 09:05:57,721 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could
                                                  //|  NOT find resource [logback.groovy]
                                                  //| 09:05:57,722 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could
                                                  //|  NOT find resource [logback-test.xml]
                                                  //| 09:05:57,722 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Found
                                                  //|  resource [logback.xml] at [file:/home/thimmaiah/work/scala/life_of_a_trade_
                                                  //| scala/bin/logback.xml]
                                                  //| 09:05:57,802 |-INFO in
                                                  //| Output exceeds cutoff limit.
  //Await.result(PositionDao.createTables(), 5 seconds)

  //Await.result(TradeDao.createTables(), 5 seconds)
  val o2 = Await.result(OrderDao.get(1), 5 seconds)
                                                  //> 02/12 09:06:00 DEBUG[main] s.b.D.action - #1: StreamingInvokerAction$HeadOpt
                                                  //| ionAction [select x2.`id`, x2.`exchange`, x2.`buy_sell`, x2.`order_type`, x2
                                                  //| .`user_id`, x2.`security_id`, x2.`quantity`, x2.`unfilled_qty`, x2.`price`, 
                                                  //| x2.`pre_trade_check_status`, x2.`trade_status`, x2.`status`, x2.`created_at`
                                                  //| , x2.`updated_at` from `orders` x2 where x2.`id` = 1]
                                                  //| o2  : Option[com.lot.order.model.Order] = Some(Order(Some(1),NASDAQ,BUY,LIMI
                                                  //| T,55,100,100.0,100.0,49.0,BlockAmountSuccess,,,Some(2016-02-09T19:41:57.000+
                                                  //| 05:30),Some(2016-02-09T19:41:59.000+05:30)))
  //println(o2)
  //println(o2.get.security)
}