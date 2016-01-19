package com.lot.position.service

import scala.concurrent.duration.DurationInt
import com.lot.position.model.PositionJsonProtocol
import akka.actor.Actor
import akka.util.Timeout
import spray.routing.HttpService
import com.lot.utils.Configuration
import com.lot.StaticService
import com.lot.utils.CORSSupport
import com.typesafe.config.ConfigFactory
import akka.actor.ActorLogging

class PositionRoutesActor(modules: Configuration) extends Actor with 
  HttpService with StaticService with CORSSupport with ActorLogging {

  import com.lot.position.model.PositionJsonProtocol._

  def actorRefFactory = context

  implicit val timeout = Timeout(5.seconds)

  def receive = runRoute(
        respondWithCORS(conf.getString("origin.domain")) { PositionService.endpoints })
}



 