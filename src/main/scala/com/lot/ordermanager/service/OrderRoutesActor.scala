package com.lot.ordermanager.service

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.reflect.runtime.universe._
import scala.reflect.runtime.universe

import com.lot.ordermanager.dao.OrderDao
import com.lot.ordermanager.service.OrderService
import com.typesafe.scalalogging.LazyLogging

import akka.actor.Actor
import akka.util.Timeout
import spray.http.MediaTypes._
import spray.http.StatusCodes._
import spray.httpx.marshalling.ToResponseMarshallable.isMarshallable
import spray.routing._
import spray.routing.Directive.pimpApply
import spray.routing.directives.OnCompleteFutureMagnet.apply
import utils.Configuration
import utils.PersistenceModule

class OrderRoutesActor(modules: Configuration with PersistenceModule) extends Actor with HttpService with LazyLogging {

  import com.lot.ordermanager.model.OrderJsonProtocol._

  def actorRefFactory = context

  implicit val timeout = Timeout(5.seconds)

  def receive = runRoute(OrderService.endpoints)
}



 