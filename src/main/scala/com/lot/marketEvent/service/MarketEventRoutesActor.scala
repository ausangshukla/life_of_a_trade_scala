package com.lot.marketEvent.service

import scala.concurrent.duration.DurationInt
import com.lot.marketEvent.model.MarketEventJsonProtocol
import akka.actor.Actor
import akka.util.Timeout
import spray.routing.HttpService
import com.lot.utils.Configuration
import com.lot.StaticService
import com.lot.utils.CORSSupport
import com.typesafe.config.ConfigFactory
import akka.actor.ActorLogging

/**
 * The Actor which is used to run the routes associated with this service
 */
class MarketEventRoutesActor(modules: Configuration) extends Actor with 
  HttpService with StaticService with CORSSupport with ActorLogging {

  import com.lot.marketEvent.model.MarketEventJsonProtocol._

  def actorRefFactory = context

  implicit val timeout = Timeout(5.seconds)

  /**
   * Runs the routes in the service, defined by the MarketEventService.endpoints
   */
  def receive = runRoute(
        respondWithCORS(conf.getString("origin.domain")) { MarketEventService.endpoints })
}



 