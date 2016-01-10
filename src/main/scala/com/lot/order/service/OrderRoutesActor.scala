package com.lot.order.service

import scala.concurrent.duration.DurationInt
import com.lot.order.model.OrderJsonProtocol
import akka.actor.Actor
import akka.util.Timeout
import spray.routing.HttpService
import com.lot.utils.Configuration
import com.lot.utils.CORSSupport
import com.typesafe.config.ConfigFactory
import com.lot.StaticService
import akka.actor.ActorLogging

class OrderRoutesActor(modules: Configuration) extends Actor with 
  HttpService with StaticService with CORSSupport with ActorLogging {

  import com.lot.order.model.OrderJsonProtocol._

  def actorRefFactory = context

  implicit val timeout = Timeout(5.seconds)

  def receive = runRoute(
        respondWithCORS(conf.getString("origin.domain")) { OrderService.endpoints })
}



 