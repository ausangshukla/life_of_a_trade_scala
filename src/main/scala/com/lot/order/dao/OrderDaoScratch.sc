package com.lot.order.dao

import scala.concurrent.Await
import scala.concurrent.duration._
import com.lot.security.dao.SecurityDao
import org.joda.time.DateTime
import com.lot.order.model._

object OrderDaoScratch {
  val secs = SecurityDao.list                     //> SLF4J: Class path contains multiple SLF4J bindings.
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
                                                  //| 12/30 10:52:22 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`na
                                                  //| me`, x2.`ticker`, x2.`description`, x2.`price`, x2.`asset_class`, x2.`sector
                                                  //| `, x2.`region`, x2.`tick_size`, x2.`liquidity`, x2.`created_at`, x2.`updated
                                                  //| _at` from `securities` x2]
                                                  //| secs  : scala.concurrent.Future[Seq[com.lot.security.model.Security]] = scal
                                                  //| 
                                                  //| Output exceeds cutoff limit.
  val s = Await.result(secs, 5 seconds)           //> s  : Seq[com.lot.security.model.Security] = Vector(Security(Some(1),Homenick
                                                  //| , Herman and Schaefer,FAX,Programmable zero defect contingency,16.0,Stock,Ph
                                                  //| arma,EU,5,Low,Some(2015-12-20T05:58:26.000+05:30),Some(2015-12-20T05:58:26.0
                                                  //| 00+05:30)), Security(Some(2),Wisoky LLC,BJE,Open-source responsive knowledge
                                                  //|  user,49.0,Bond,Technology,EU,1,Low,Some(2015-12-20T05:58:26.000+05:30),Some
                                                  //| (2015-12-20T05:58:26.000+05:30)), Security(Some(3),Hickle, Schmeler and Emme
                                                  //| rich,AXO,Upgradable human-resource capacity,64.0,Stock,Technology,EU,1,Very 
                                                  //| High,Some(2015-12-20T05:58:26.000+05:30),Some(2015-12-20T05:58:26.000+05:30)
                                                  //| ), Security(Some(4),Senger, Zemlak and Boyle,WGE,Quality-focused system-wort
                                                  //| hy ability,4.0,Bond,Pharma,US,9,None,Some(2015-12-20T05:58:26.000+05:30),Som
                                                  //| e(2015-12-20T05:58:26.000+05:30)), Security(Some(5),Kovacek-Dickinson,VFH,Er
                                                  //| gonomic exuding definition,36.0,Stock,Manufacturing,EMEA,3,High,Some(2015-12
                                                  //| -20T05:58:26.000+05:30),
                                                  //| Output exceeds cutoff limit.

  val orders = OrderDao.list                      //> 12/30 10:52:22 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`bu
                                                  //| y_sell`, x2.`order_type`, x2.`user_id`, x2.`security_id`, x2.`quantity`, x2.
                                                  //| `price`, x2.`created_at`, x2.`updated_at` from `orders` x2 order by x2.`id` 
                                                  //| desc]
                                                  //| orders  : scala.concurrent.Future[Seq[com.lot.order.model.Order]] = scala.co
                                                  //| ncurrent.impl.Promise$DefaultPromise@1ce9293d

  val o = Await.result(orders, 5 seconds)         //> o  : Seq[com.lot.order.model.Order] = Vector(Order(Some(27),BUY,MARKET,1,10,
                                                  //| 10000.0,100.0,Some(2015-12-30T10:52:02.000+05:30),Some(2015-12-30T10:52:02.0
                                                  //| 00+05:30)), Order(Some(26),BUY,MARKET,1,10,10000.0,100.0,Some(2015-12-30T10:
                                                  //| 51:53.000+05:30),Some(2015-12-30T10:51:53.000+05:30)), Order(Some(25),BUY,MA
                                                  //| RKET,1,10,10000.0,100.0,Some(2015-12-30T10:50:01.000+05:30),Some(2015-12-30T
                                                  //| 10:50:01.000+05:30)), Order(Some(22),BUY,MARKET,26,10,100.0,100.0,Some(2015-
                                                  //| 12-29T23:45:03.000+05:30),Some(2015-12-29T23:45:03.000+05:30)), Order(Some(2
                                                  //| 1),BUY,MARKET,26,10,101.0,101.11,Some(2015-12-29T23:37:13.000+05:30),Some(20
                                                  //| 15-12-29T23:37:13.000+05:30)), Order(Some(20),BUY,MARKET,1,10,100.0,102.3,So
                                                  //| me(2015-12-29T23:37:04.000+05:30),Some(2015-12-29T23:37:04.000+05:30)), Orde
                                                  //| r(Some(19),SELL,MARKET,1,10,120.0,103.3,Some(2015-12-29T23:37:04.000+05:30),
                                                  //| Some(2015-12-29T23:37:04.000+05:30)), Order(Some(18),BUY,LIMIT,1,10,150.0,98
                                                  //| .3,Some(2015-12-29T23:37
                                                  //| Output exceeds cutoff limit.

  val o1 = Order(None, OrderType.BUY, OrderType.MARKET, 1, 10, 10000.0, 100.0, Some(new DateTime()), Some(new DateTime()))
                                                  //> o1  : com.lot.order.model.Order = Order(None,BUY,MARKET,1,10,10000.0,100.0,S
                                                  //| ome(2015-12-30T10:52:23.013+05:30),Some(2015-12-30T10:52:23.013+05:30))
  val respF1 = OrderDao.save(o1)                  //> 12/30 10:52:23 DEBUG[main] s.b.D.action - #1: += [insert into `orders` (`buy
                                                  //| _sell`,`order_type`,`user_id`,`security_id`,`quantity`,`price`,`created_at`,
                                                  //| `updated_at`)  values (?,?,?,?,?,?,?,?)]
                                                  //| respF1  : scala.concurrent.Future[com.lot.order.model.Order] = scala.concurr
                                                  //| ent.impl.Promise$DefaultPromise@74faf68b
  val resp1 = Await.result(respF1, 10 seconds)    //> resp1  : com.lot.order.model.Order = Order(Some(28),BUY,MARKET,1,10,10000.0,
                                                  //| 100.0,Some(2015-12-30T10:52:23.013+05:30),Some(2015-12-30T10:52:23.013+05:30
                                                  //| ))

	
}