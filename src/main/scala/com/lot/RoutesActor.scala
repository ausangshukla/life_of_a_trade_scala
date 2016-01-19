package com.lot

import scala.concurrent.duration.DurationInt
import com.lot.order.model.OrderJsonProtocol
import com.lot.user.service.UserService
import com.typesafe.scalalogging.LazyLogging
import akka.actor.Actor
import akka.util.Timeout
import spray.routing.HttpService
import utils.Configuration
import com.typesafe.config.ConfigFactory
import com.lot.order.service.OrderService
import com.lot.utils.CORSSupport
import com.lot.security.service.SecurityService
import akka.actor.ActorLogging
import com.lot.trade.service.TradeService
import com.lot.position.service.PositionService

class RoutesActor(modules: Configuration) extends Actor with 
  HttpService with StaticService with CORSSupport with ActorLogging {

  def actorRefFactory = context

  implicit val timeout = Timeout(5.seconds)

  def receive = runRoute(respondWithCORS(conf.getString("origin.domain")) {
      OrderService.endpoints ~ 
      UserService.endpoints ~ 
      SecurityService.endpoints ~
      TradeService.endpoints ~ 
      PositionService.endpoints ~
      staticRoute })
 
}



 