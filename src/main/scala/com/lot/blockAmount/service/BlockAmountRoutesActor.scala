package com.lot.blockAmount.service

import scala.concurrent.duration.DurationInt
import com.lot.blockAmount.model.BlockAmountJsonProtocol
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
class BlockAmountRoutesActor(modules: Configuration) extends Actor with 
  HttpService with StaticService with CORSSupport with ActorLogging {

  import com.lot.blockAmount.model.BlockAmountJsonProtocol._

  def actorRefFactory = context

  implicit val timeout = Timeout(5.seconds)

  /**
   * Runs the routes in the service, defined by the BlockAmountService.endpoints
   */
  def receive = runRoute(
        respondWithCORS(conf.getString("origin.domain")) { BlockAmountService.endpoints })
}



 