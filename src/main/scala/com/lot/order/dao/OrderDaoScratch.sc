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

object OrderDaoScratch {

  //Await.result(MarketEventDao.createTables(), 5 seconds)
  Await.result(TriggeredEventDao.createTables(), 5 seconds)
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
                                                  //| 02/07 11:06:57 DEBUG[main] s.b.D.action - #1: [fused] andThen
                                                  //|       1: schema.create [create table `market_events` (`id` BIGINT NOT NULL A
                                                  //| UTO_INCREMENT PRIMARY KEY,`name` TEXT NOT NULL,`event_type` VARCHAR(10) NOT 
                                                  //| NULL,`summary` VARCHAR(255) NOT NULL,`description` TEXT,`direction` VARCHAR(
                                                  //| 5) NOT NULL,`
                                                  //| Output exceeds cutoff limit.
  //Await.result(PositionDao.createTables(), 5 seconds)

  //Await.result(TradeDao.createTables(), 5 seconds)
  val o2 = Await.result(OrderDao.get(1), 5 seconds)
  //println(o2)
  //println(o2.get.security)
}