package com.lot.user.dao

import scala.concurrent.Await
import scala.concurrent.duration._
import com.lot.order.dao.OrderDao
import com.lot.security.dao.SecurityDao
import org.joda.time.DateTime
import com.lot.order.model._

object UserDaoScratch {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  //  val users = UserDao.list
  //  val u = Await.result(users, 5 seconds)

  OrderDao.createTables()                         //> SLF4J: Class path contains multiple SLF4J bindings.
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
                                                  //| 12/30 16:28:59 DEBUG[main] s.b.D.action - #1: [fused] andThen
                                                  //|       1: schema.create [create table `orders` (`id` BIGINT NOT NULL AUTO_INC
                                                  //| REMENT PRIMARY KEY,`exchange` TEXT NOT NULL,`buy_sell` TEXT NOT NULL,`order_
                                                  //| type` TEXT NOT NULL,`user_id` BIGINT NOT NULL,`security_id` BIGINT NOT NULL,
                                                  //| `quantity` DOUBLE NOT NULL,`price` DOUBLE NOT 
                                                  //| Output exceeds cutoff limit.

}