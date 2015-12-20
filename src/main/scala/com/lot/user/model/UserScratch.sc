package com.lot.user.model

import com.lot.usermanager.dao.UserDao
import org.joda.time.DateTime
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.util.Timeout
import scala.collection.immutable.Vector
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization.read
import org.json4s.native.Serialization.write

object UserScratch {

  implicit val timeout = Timeout(5 seconds)       //> timeout  : akka.util.Timeout = Timeout(5 seconds)
  val dao = new UserDao()                         //> dao  : com.lot.usermanager.dao.UserDao = com.lot.usermanager.dao.UserDao@26e
                                                  //| 7ee9f

  val u1 = dao.get(1)                             //> SLF4J: Class path contains multiple SLF4J bindings.
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
                                                  //| 12/20 00:58:36 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`fi
                                                  //| rst_name`, x2.`last_name`, x2.`email`, x2.`created_at`, x2.`updated_at`, x2.
                                                  //| `encrypted_password`, x2.`reset_password_token`, x2.`reset_password_sent_at`
                                                  //| , x2.`remember_created_at`, x2.`sign_in_count`, x2.`current_sign_in_at`, x2.
                                                  //| `last_sign_in_at`, x2.`current_
                                                  //| Output exceeds cutoff limit.
  val f = dao.list                                //> 12/20 00:58:36 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`fi
                                                  //| rst_name`, x2.`last_name`, x2.`email`, x2.`created_at`, x2.`updated_at`, x2.
                                                  //| `encrypted_password`, x2.`reset_password_token`, x2.`reset_password_sent_at`
                                                  //| , x2.`remember_created_at`, x2.`sign_in_count`, x2.`current_sign_in_at`, x2.
                                                  //| `last_sign_in_at`, x2.`current_sign_in_ip`, x2.`last_sign_in_ip`, x2.`confir
                                                  //| mation_token`, x2.`confirmed_at`, x2.`confirmation_sent_at`, x2.`unconfirmed
                                                  //| _email`, x2.`role`, x2.`team_id`, x2.`contest_id` from `users` x2]
                                                  //| f  : scala.concurrent.Future[Seq[com.lot.user.model.User]] = scala.concurren
                                                  //| t.impl.Promise$DefaultPromise@a1172c5

  val result = Await.result(f, timeout.duration).asInstanceOf[Vector[User]]
                                                  //> result  : scala.collection.immutable.Vector[com.lot.user.model.User] = Vecto
                                                  //| r(User(162,Araceli,Becker,ca10@loat.com,2015-09-17 06:50:03.0,2015-09-17 06:
                                                  //| 53:30.0,$2a$10$tXm6CdHHI5TXDExMngltiO6.LxSfZw1mydU4/EIg9HvOgz83ubdsy,None,No
                                                  //| ne,None,6,Some(2015-09-17 06:53:30.0),Some(2015-09-17 06:53:30.0),Some(127.0
                                                  //| .0.1),Some(127.0.0.1),None,Some(2015-09-17 06:50:03.0),Some(2015-09-17 06:50
                                                  //| :03.0),None,Some(Contest Admin),None,Some(10)), User(163,Myra,Tillman,ca11@l
                                                  //| oat.com,2015-09-17 06:50:03.0,2015-09-17 06:50:03.0,$2a$10$OGDJyRQ7a0Cib.g8C
                                                  //| hI.ZuARPpjUCC8AX92e68LcEJpVYzV2DQJ8u,None,None,None,5,None,None,None,None,No
                                                  //| ne,Some(2015-09-17 06:50:03.0),Some(2015-09-17 06:50:03.0),None,Some(Contest
                                                  //|  Admin),None,Some(11)), User(164,Ivah,Schmeler,ca12@loat.com,2015-09-17 06:5
                                                  //| 0:03.0,2015-09-17 06:50:03.0,$2a$10$PneE0SsPNnwu5HXS/3mY3eifBvIxFvN9C56yD1GP
                                                  //| MJ/NeYtZl5Fc2,None,None,None,5,None,None,None,None,None,Some(2015-09-17 06:5
                                                  //| 0:03.0),Some(2015-09-17 
                                                  //| Output exceeds cutoff limit.
}