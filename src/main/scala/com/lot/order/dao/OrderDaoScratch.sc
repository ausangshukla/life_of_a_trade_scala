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

object OrderDaoScratch {

  Await.result(PositionDao.createTables(), 5 seconds)
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
                                                  //| 01/21 09:15:21 DEBUG[main] s.b.D.action - #1: [fused] andThen
                                                  //|       1: schema.create [create table `positions` (`id` BIGINT NOT NULL AUTO_
                                                  //| INCREMENT PRIMARY KEY,`ticker` TEXT NOT NULL,`security_id` BIGINT NOT NULL,`
                                                  //| quantity` DOUBLE NOT NULL,`average_price` DOUBLE NOT NULL,`pnl` DOUBLE NOT N
                                                  //| ULL,`created_
                                                  //| Output exceeds cutoff limit.

  Await.result(TradeDao.createTables(), 5 seconds)//> 01/21 09:15:22 DEBUG[main] s.b.D.action - #1: [fused] andThen
                                                  //|       1: schema.create [create table `trades` (`id` BIGINT NOT NULL AUTO_INC
                                                  //| REMENT PRIMARY KEY,`trade_date` TIMESTAMP NOT NULL,`settlement_date` TIMESTA
                                                  //| MP NOT NULL,`security_id` BIGINT NOT NULL,`quantity` DOUBLE NOT NULL,`price`
                                                  //|  DOUBLE NOT NULL,`buy_sell` TEXT NOT NULL,`user_id` BIGINT NOT NULL,`order_i
                                                  //| d` BIGINT NOT NULL,`matched_order_id` BIGINT NOT NULL,`state` TEXT NOT NULL,
                                                  //| `created_at` TIMESTAMP NOT NULL,`updated_at` TIMESTAMP NOT NULL)]
                                                  //|       2: success ()
                                                  //| com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Table 'trades' al
                                                  //| ready exists
                                                  //| 	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
                                                  //| 
                                                  //| 	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstruct
                                                  //| orAccessorImpl.java:57)
                                                  //| 	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingC
                                                  //| onstructorAccessorImpl.java:45)
                                                  //| 	at java.lang.reflect.Construc
                                                  //| Output exceeds cutoff limit.
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