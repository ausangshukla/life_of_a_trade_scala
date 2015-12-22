package com.lot.user.model

import org.joda.time.DateTime
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.util.Timeout
import scala.collection.immutable.Vector
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization.read
import org.json4s.native.Serialization.write
import com.lot.user.dao.UserDao

object UserScratch {

  implicit val timeout = Timeout(5 seconds)       //> timeout  : akka.util.Timeout = Timeout(5 seconds)
  val dao = UserDao                               //> SLF4J: Class path contains multiple SLF4J bindings.
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
                                                  //| dao  : com.lot.user.dao.UserDao.type = Rep(TableExpansion)

  val u1 = Await.result(dao.findById(25), 10 seconds)
                                                  //> 12/20 10:54:08 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`fi
                                                  //| rst_name`, x2.`last_name`, x2.`email`, x2.`created_at`, x2.`updated_at`, x2.
                                                  //| `encrypted_password`, x2.`role`, x2.`team_id`, x2.`contest_id`, x2.`tokens` 
                                                  //| from `users` x2 where x2.`id` = 25]
                                                  //| u1  : Option[com.lot.user.model.UserTable#TableElementType] = Some(User(25,D
                                                  //| emarcus,Lowe,ona.schultz@wuckert.biz,2015-12-20 05:58:22.0,2015-12-20 07:39:
                                                  //| 14.0,$2a$10$L2dd1OdUWh2FgQA.df5cYuOHFFR3OAVGGbGBjShKD92JLf8PHnc/C,Reader,Non
                                                  //| e,None,Some({"j5NiYSdKvzQQ56NBmeGATA":{"token":"$2a$10$5ztIKZdURBR/5eR8oZENl
                                                  //| .gNj7TyCYwAIFjyfCoe0JkbVMLCZGmE6","expiry":1451806753,"last_token":"$2a$10$F
                                                  //| 6GknNSU/aWRA6tQa32xi.iT4XYNZWk9dQg2U/KLrbUqz2tl36Cmi","updated_at":"2015-12-
                                                  //| 20T02:39:14.749-05:00"}})))

  //val u = Await.result(u1, timeout.duration).asInstanceOf[Vector[User]]
  println(u1)                                     //> Some(User(25,Demarcus,Lowe,ona.schultz@wuckert.biz,2015-12-20 05:58:22.0,201
                                                  //| 5-12-20 07:39:14.0,$2a$10$L2dd1OdUWh2FgQA.df5cYuOHFFR3OAVGGbGBjShKD92JLf8PHn
                                                  //| c/C,Reader,None,None,Some({"j5NiYSdKvzQQ56NBmeGATA":{"token":"$2a$10$5ztIKZd
                                                  //| URBR/5eR8oZENl.gNj7TyCYwAIFjyfCoe0JkbVMLCZGmE6","expiry":1451806753,"last_to
                                                  //| ken":"$2a$10$F6GknNSU/aWRA6tQa32xi.iT4XYNZWk9dQg2U/KLrbUqz2tl36Cmi","updated
                                                  //| _at":"2015-12-20T02:39:14.749-05:00"}})))

  val f = dao.list                                //> 12/20 10:54:08 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`fi
                                                  //| rst_name`, x2.`last_name`, x2.`email`, x2.`created_at`, x2.`updated_at`, x2.
                                                  //| `encrypted_password`, x2.`role`, x2.`team_id`, x2.`contest_id`, x2.`tokens` 
                                                  //| from `users` x2]
                                                  //| f  : scala.concurrent.Future[Seq[com.lot.user.model.User]] = scala.concurren
                                                  //| t.impl.Promise$DefaultPromise@1fb51af1

  val result = Await.result(f, timeout.duration).asInstanceOf[Vector[User]]
                                                  //> result  : scala.collection.immutable.Vector[com.lot.user.model.User] = Vecto
                                                  //| r(User(25,Demarcus,Lowe,ona.schultz@wuckert.biz,2015-12-20 05:58:22.0,2015-1
                                                  //| 2-20 07:39:14.0,$2a$10$L2dd1OdUWh2FgQA.df5cYuOHFFR3OAVGGbGBjShKD92JLf8PHnc/C
                                                  //| ,Reader,None,None,Some({"j5NiYSdKvzQQ56NBmeGATA":{"token":"$2a$10$5ztIKZdURB
                                                  //| R/5eR8oZENl.gNj7TyCYwAIFjyfCoe0JkbVMLCZGmE6","expiry":1451806753,"last_token
                                                  //| ":"$2a$10$F6GknNSU/aWRA6tQa32xi.iT4XYNZWk9dQg2U/KLrbUqz2tl36Cmi","updated_at
                                                  //| ":"2015-12-20T02:39:14.749-05:00"}})), User(26,Ally,Steuber,haylee@dicki.biz
                                                  //| ,2015-12-20 05:58:22.0,2015-12-20 05:58:22.0,$2a$10$FAlX7Cbv0hf17Sgf9taFOe/D
                                                  //| YlABFR8N8SC77PIVzbQ038PyW2Ja6,Reader,None,None,Some({})), User(27,Loren,Saue
                                                  //| r,arne_emmerich@stark.biz,2015-12-20 05:58:22.0,2015-12-20 05:58:22.0,$2a$10
                                                  //| $FhO9s5Av8o2qURF5zzju2.xb31qfhUuQJdy4F2t1/.WFkgYCxn8sa,Reader,None,None,Some
                                                  //| ({})), User(28,Queenie,Grady,rebeka.emard@cummings.net,2015-12-20 05:58:22.0
                                                  //| ,2015-12-20 05:58:22.0,$
                                                  //| Output exceeds cutoff limit.
}