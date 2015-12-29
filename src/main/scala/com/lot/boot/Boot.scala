package com.lot.boot

import scala.concurrent.duration._
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.io.IO
import akka.util.Timeout
import spray.can.Http
import utils.ActorModuleImpl
import utils.ConfigurationModuleImpl
import utils.PersistenceModuleImpl
import utils.InitData
import com.lot.order.service.OrderRoutesActor
import com.lot.RoutesActor


object Boot extends App   {

  // configuring modules for application, cake pattern for DI
  val modules = new ConfigurationModuleImpl  with ActorModuleImpl with PersistenceModuleImpl

  // create and start our service actor
  val service = modules.system.actorOf(Props(classOf[RoutesActor], modules), "routesActor")

  implicit val system = modules.system
  implicit val timeout = Timeout(5.seconds)
  
  InitData.init()

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8000)

}
