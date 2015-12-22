package com.lot.user.service

import scala.concurrent.duration.DurationInt

import com.lot.user.model.UserJsonProtocol
import com.typesafe.scalalogging.LazyLogging

import akka.actor.Actor
import akka.util.Timeout
import spray.routing.HttpService
import utils.Configuration
import utils.PersistenceModule

class UserRoutesActor(modules: Configuration with PersistenceModule) extends Actor with HttpService with LazyLogging {

  import com.lot.user.model.UserJsonProtocol._

  def actorRefFactory = context

  implicit val timeout = Timeout(5.seconds)

  def receive = runRoute(UserService.endpoints)
}



 