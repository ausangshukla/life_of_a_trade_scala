package com.lot.ordermanager.service

import scala.concurrent.duration.DurationInt
import com.lot.ordermanager.model.OrderJsonProtocol
import com.lot.user.service.UserService
import com.typesafe.scalalogging.LazyLogging
import akka.actor.Actor
import akka.util.Timeout
import spray.routing.HttpService
import utils.Configuration
import utils.PersistenceModule
import com.lot.boot.StaticService

class OrderRoutesActor(modules: Configuration with PersistenceModule) extends Actor with 
  HttpService with StaticService with LazyLogging {

  import com.lot.ordermanager.model.OrderJsonProtocol._

  def actorRefFactory = context

  implicit val timeout = Timeout(5.seconds)

  def receive = runRoute(OrderService.endpoints ~ UserService.endpoints)
}



 