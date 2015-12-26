package com.lot.user.dao

import scala.concurrent.Await
import scala.concurrent.duration._

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
                                                  //| 12/26 05:26:58 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`fi
                                                  //| rst_name`, x2.`last_name`, x2.`email`, x2.`created_at`, x2.`updated_at`, x2.
                                                  //| `encrypted_password`, x2.`role`, x2.`team_id`, x2.`contest_id`, x2.`tokens` 
                                                  //| from `users` x2]
                                                  //| users  : scala.concurrent.Future[Seq[com.lot.user.model.User]] = scala.concu
                                                  //| rrent.impl
                                                  //| Output exceeds cutoff limit.
  val u = Await.result(users, 5 seconds)          //> u  : Seq[com.lot.user.model.User] = Vector(User(25,Demarcus,Lowe,ona.schultz
                                                  //| @wuckert.biz,2015-12-20 05:58:22.0,2015-12-26 05:38:43.0,$2a$10$L2dd1OdUWh2F
                                                  //| gQA.df5cYuOHFFR3OAVGGbGBjShKD92JLf8PHnc/C,Reader,None,None,Some({"j5NiYSdKvz
                                                  //| QQ56NBmeGATA":{"token":"$2a$10$/00BWLITrWRaRAqph8I5V./SpSyGLMTONtDiiHDMb7kMY
                                                  //| Uqix.KpO","expiry":1451966236,"last_token":"$2a$10$cI.ks.bt2db7e9Zty9zQzu4Bk
                                                  //| jITnMKV4PXdWFCcik1ZKzRSkIBN.","updated_at":"2015-12-21T22:57:16.710-05:00"},
                                                  //| "7qxtYoDDQRD6xQOCaqV44w":{"token":"$2a$10$2TOvlQ7KGNKn1q7T//14ce8DYVtGTuy/vO
                                                  //| zrnyXJkYBgYBLeyTC86","expiry":1452161418,"last_token":"$2a$10$1ujhtnaVI0Rfl5
                                                  //| U44TzBZONvRaHH6OXVrGUB.htDIiDoSdbOKSFNq","updated_at":"2015-12-24T05:10:22.7
                                                  //| 94-05:00"},"d6xaW-kVVnuUB5vE0qAZlA":{"token":"$2a$10$j.fl500O9PyvD9MiYPXjluH
                                                  //| fD8o6E1ZvEgCf8huVb8qIJ0nAc3pSO","expiry":1452317923,"last_token":"$2a$10$30a
                                                  //| PpUwxXSCam0U0Q50oZ./RqWPOpKIA1MksMwAyRRkTUiSfaeH8y","updated_at":"2015-12-26
                                                  //| T00:38:43.049-05:00"}}))
                                                  //| Output exceeds cutoff limit.
  
}