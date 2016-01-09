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
                                                  //| 01/07 10:19:24 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`ex
                                                  //| change`, x2.`buy_sell`, x2.`order_type`, x2.`user_id`, x2.`security_id`, x2.
                                                  //| `quantity`, x2.`unfilled_qty`, x2.`price`, x2.`created_at`, x2.`updated_at` 
                                                  //| from `orders` x2 order by x2.`id` desc]
                                                  //| orders  : scala.concurrent.Future[Seq[com.lot.order.model.Order]] = 
                                                  //| Output exceeds cutoff limit.
  val s = Await.result(orders, 5 seconds)         //> com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column 'x
                                                  //| 2.unfilled_qty' in 'field list'
                                                  //| 	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
                                                  //| 
                                                  //| 	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstruct
                                                  //| orAccessorImpl.java:57)
                                                  //| 	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingC
                                                  //| onstructorAccessorImpl.java:45)
                                                  //| 	at java.lang.reflect.Constructor.newInstance(Constructor.java:526)
                                                  //| 	at com.mysql.jdbc.Util.handleNewInstance(Util.java:404)
                                                  //| 	at com.mysql.jdbc.Util.getInstance(Util.java:387)
                                                  //| 	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:939)
                                                  //| 	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3878)
                                                  //| 	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3814)
                                                  //| 	at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:2478)
                                                  //| 	at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2625)
                                                  //| 	at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.
                                                  //| Output exceeds cutoff limit.
}