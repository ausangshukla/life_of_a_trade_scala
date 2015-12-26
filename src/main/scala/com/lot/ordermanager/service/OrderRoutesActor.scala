package com.lot.ordermanager.service

import scala.concurrent.duration.DurationInt
import com.lot.ordermanager.model.OrderJsonProtocol
import com.typesafe.scalalogging.LazyLogging
import akka.actor.Actor
import akka.util.Timeout
import spray.routing.HttpService
import utils.Configuration
import utils.PersistenceModule
import utils.CORSSupport
import com.typesafe.config.ConfigFactory
import com.lot.ordermanager.service.OrderService
import com.lot.StaticService

class OrderRoutesActor(modules: Configuration with PersistenceModule) extends Actor with 
  HttpService with StaticService with CORSSupport with LazyLogging {

  import com.lot.ordermanager.model.OrderJsonProtocol._

  def actorRefFactory = context

  implicit val timeout = Timeout(5.seconds)

  def receive = runRoute(
        respondWithCORS(conf.getString("origin.domain")) { OrderService.endpoints })
}



 