package com.lot.user.dao

import scala.concurrent.Await
import scala.concurrent.duration._
import com.lot.order.dao.OrderDao
import com.lot.security.dao.SecurityDao
import org.joda.time.DateTime
import com.lot.order.model._

object UserDaoScratch {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  val users = UserDao.list                        //> SLF4J: Class path contains multiple SLF4J bindings.
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
                                                  //| 01/31 14:51:57 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`fi
                                                  //| rst_name`, x2.`last_name`, x2.`email`, x2.`created_at`, x2.`updated_at`, x2.
                                                  //| `encrypted_password`, x2.`role`, x2.`account_balance`, x2.`blocked_amount`, 
                                                  //| x2.`tokens` from `users` x2]
                                                  //| users  : scala.concurrent.Future[Seq[com.lot.u
                                                  //| Output exceeds cutoff limit.
  val u = Await.result(users, 5 seconds)          //> u  : Seq[com.lot.user.model.User] = Vector(User(Some(26),Ally1,Steuber,hayle
                                                  //| e@dicki.biz,Some(2015-12-20T05:58:22.000+05:30),Some(2016-01-29T13:57:55.000
                                                  //| +05:30),$2a$10$FAlX7Cbv0hf17Sgf9taFOe/DYlABFR8N8SC77PIVzbQ038PyW2Ja6,Reader,
                                                  //| 19000.0,1600.0,Some({"j8K2EUB8UqQRDT11QjwRCA":{"token":"$2a$10$fmpBkR1Sw.AO5
                                                  //| KJNET410OYVAwjF/A/w6wbA9R6QeVtR3LrCZB8bC","expiry":1455284767,"last_token":"
                                                  //| $2a$10$rEeQuywG/m.FQkOYcXMSC.C4sUfGpbGndUUh2YFUS.KedBOh0VEi2","updated_at":"
                                                  //| 2016-01-29T19:16:07.042+05:30"},"JgnxoiO0zovDk4mg8z34Zw":{"token":"$2a$10$cF
                                                  //| 7hAvUwDvuoRpRwfJTt3uY.DbDcpqzhGr0mezbM6D2y19.zLTBwa","expiry":1455284776,"la
                                                  //| st_token":"$2a$10$7ulTtufXYBU/GswqzxyZNut/BjLKufL9hJniDoIloiuUvWBYszQYa","up
                                                  //| dated_at":"2016-01-29T19:16:16.529+05:30"},"1OQzBe_TnA_Tj5_WBR2ojA":{"token"
                                                  //| :"$2a$10$yiAycCdy66SPlXB48puSr.eHHlYYX/WiPf9xKOeBfhmKhm0vWvlfq","expiry":145
                                                  //| 5285472,"last_token":"$2a$10$ok/UKKCVHL4YXXfV0ChoL.vGIHQklfGQYOHkUGgH1vS.TrA
                                                  //| r62CZm","updated_at":"20
                                                  //| Output exceeds cutoff limit.
  val res = Await.result(UserDao.addBlockedAmount(0, 1000), 5 seconds)
                                                  //> 01/31 14:51:58 DEBUG[main] s.b.D.action - #1: StartTransaction
                                                  //| 01/31 14:51:58 DEBUG[main] s.b.D.action - #2: StreamingInvokerAction$HeadOpt
                                                  //| ionAction [select x2.`id`, x2.`first_name`, x2.`last_name`, x2.`email`, x2.`
                                                  //| created_at`, x2.`updated_at`, x2.`encrypted_password`, x2.`role`, x2.`accoun
                                                  //| t_balance`, x2.`blocked_amount`, x2.`tokens` from `users` x2 where x2.`id` =
                                                  //|  0]
                                                  //| 01/31 14:51:58 DEBUG[Pool-1-worker-5] s.b.D.action - #3: success (None,None,
                                                  //| None)
                                                  //| 01/31 14:51:58 DEBUG[Pool-1-worker-5] s.b.D.action - #4: update [update `use
                                                  //| rs` set `account_balance` = ?, `blocked_amount` = ? where (`users`.`id` = 0)
                                                  //|  and (`users`.`account_balance` >= 1000.0)]
                                                  //| 01/31 14:51:58 DEBUG[Pool-1-worker-5] s.b.D.action - #5: success 0
                                                  //| 01/31 14:51:58 DEBUG[Pool-1-worker-5] s.b.D.action - #6: Commit
                                                  //| res  : Int = 0
  println(res)                                    //> 0
  Await.result(UserDao.get(26), 5 seconds)        //> 01/31 14:51:58 DEBUG[main] s.b.D.action - #1: StreamingInvokerAction$HeadOpt
                                                  //| ionAction [select x2.`id`, x2.`first_name`, x2.`last_name`, x2.`email`, x2.`
                                                  //| created_at`, x2.`updated_at`, x2.`encrypted_password`, x2.`role`, x2.`accoun
                                                  //| t_balance`, x2.`blocked_amount`, x2.`tokens` from `users` x2 where x2.`id` =
                                                  //|  26]
                                                  //| res0: Option[com.lot.user.model.User] = Some(User(Some(26),Ally1,Steuber,hay
                                                  //| lee@dicki.biz,Some(2015-12-20T05:58:22.000+05:30),Some(2016-01-29T13:57:55.0
                                                  //| 00+05:30),$2a$10$FAlX7Cbv0hf17Sgf9taFOe/DYlABFR8N8SC77PIVzbQ038PyW2Ja6,Reade
                                                  //| r,19000.0,1600.0,Some({"j8K2EUB8UqQRDT11QjwRCA":{"token":"$2a$10$fmpBkR1Sw.A
                                                  //| O5KJNET410OYVAwjF/A/w6wbA9R6QeVtR3LrCZB8bC","expiry":1455284767,"last_token"
                                                  //| :"$2a$10$rEeQuywG/m.FQkOYcXMSC.C4sUfGpbGndUUh2YFUS.KedBOh0VEi2","updated_at"
                                                  //| :"2016-01-29T19:16:07.042+05:30"},"JgnxoiO0zovDk4mg8z34Zw":{"token":"$2a$10$
                                                  //| cF7hAvUwDvuoRpRwfJTt3uY.DbDcpqzhGr0mezbM6D2y19.zLTBwa","expiry":1455284776,"
                                                  //| last_token":"$2
                                                  //| Output exceeds cutoff limit.

}