package com.lot.utils
import spray.json._
import com.lot.marketEvent.model.MarketEventJsonProtocol._
import com.lot.marketEvent.model.MarketEvent
import scala.concurrent.duration._
import scala.concurrent.{ Await, Future }
import com.lot.marketEvent.dao.TriggeredEventDao

object UtilsScratch {

  val te = Await.result(TriggeredEventDao.first, Duration.Inf)
  //> SLF4J: Class path contains multiple SLF4J bindings.
  //| SLF4J: Found binding in [jar:file:/home/thimmaiah/tools/activator-dist-1.3.5
  //| /repository/ch.qos.logback/logback-classic/1.1.3/jars/logback-classic.jar!/o
  //| rg/slf4j/impl/StaticLoggerBinder.class]
  //| SLF4J: Found binding in [jar:file:/home/thimmaiah/tools/activator-dist-1.3.5
  //| /repository/org.slf4j/slf4j-nop/1.6.4/jars/slf4j-nop.jar!/org/slf4j/impl/Sta
  //| ticLoggerBinder.class]
  //| SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanat
  //| ion.
  //| 10:15:02,549 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could
  //|  NOT find resource [logback.groovy]
  //| 10:15:02,549 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could
  //|  NOT find resource [logback-test.xml]
  //| 10:15:02,549 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Found
  //|  resource [logback.xml] at [file:/home/thimmaiah/work/scala/life_of_a_trade_
  //| scala/bin/logback.xml]
  //| 10:15:02,664 |-INFO in
  //| Output exceeds cutoff limit.
  println(te) //> Some(TriggeredEvent(Some(1),1,false,Some(2016-02-09T19:41:57.000+05:30),Some
  //| (2016-02-09T19:41:57.000+05:30)))
  WebSocket.publishTriggerEvent(te.get) //> [DEBUG] [02/24/2016 10:15:04.670] [main] [EventStream(akka://WebSocket)] log
  //| ger log1-Logging$DefaultLogger started
  //| [DEBUG] [02/24/2016 10:15:05.668] [WebSocket-akka.actor.default-dispatcher-2
  //| ] [akka://WebSocket/system/IO-TCP/selectors/$a/0] Attempting connection to [
  //| localhost/127.0.
  //| Output exceeds cutoff limit./
}