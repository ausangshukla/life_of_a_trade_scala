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
import java.sql.Timestamp

object UserScratch {

  implicit val timeout = Timeout(5 seconds)
  val dao = UserDao

  val u1 = Await.result(dao.findById(25), 10 seconds)

  //val u = Await.result(u1, timeout.duration).asInstanceOf[Vector[User]]
  println(u1)

  val f = dao.list

  val result = Await.result(f, timeout.duration).asInstanceOf[Vector[User]]

  // 1) create a java calendar instance
  val calendar = java.util.Calendar.getInstance();

  // 2) get a java.util.Date from the calendar instance.
  //    this date will represent the current instant, or "now".
  val now = calendar.getTime();

  val u = User(25, "Demarcus", "Lowe", "ona.schultz@wuckert.biz", new Timestamp(now.getTime()), new Timestamp(now.getTime()), "$2a$10$L2dd1OdUWh2FgQA.df5cYuOHFFR3OAVGGbGBjShKD92JLf8PHnc/C", "Reader", None, None, None)
}