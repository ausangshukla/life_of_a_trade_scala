package com.lot.user.service

import scala.concurrent.duration.DurationInt

import com.lot.user.model.UserJsonProtocol
import com.typesafe.scalalogging.LazyLogging

import akka.actor.Actor
import akka.util.Timeout
import spray.routing.HttpService
import com.lot.utils.Configuration
import com.lot.utils.PersistenceModule
import akka.actor.ActorLogging

class UserRoutesActor(modules: Configuration with PersistenceModule) extends Actor 
with HttpService with ActorLogging {

  import com.lot.user.model.UserJsonProtocol._

  def actorRefFactory = context

  implicit val timeout = Timeout(5.seconds)

  def receive = runRoute(UserService.endpoints)
}



 