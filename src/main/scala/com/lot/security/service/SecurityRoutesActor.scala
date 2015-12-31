package com.lot.security.service

import scala.concurrent.duration.DurationInt
import com.lot.security.model.SecurityJsonProtocol
import com.typesafe.scalalogging.LazyLogging
import akka.actor.Actor
import akka.util.Timeout
import spray.routing.HttpService
import com.lot.utils.Configuration
import com.lot.utils.PersistenceModule
import com.lot.StaticService
import com.lot.utils.CORSSupport
import com.typesafe.config.ConfigFactory
import akka.actor.ActorLogging

class SecurityRoutesActor(modules: Configuration with PersistenceModule) extends Actor with 
  HttpService with StaticService with CORSSupport with ActorLogging {

  import com.lot.security.model.SecurityJsonProtocol._

  def actorRefFactory = context

  implicit val timeout = Timeout(5.seconds)

  def receive = runRoute(
        respondWithCORS(conf.getString("origin.domain")) { SecurityService.endpoints })
}



 