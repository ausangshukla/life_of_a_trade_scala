package com.lot.user.service

import scala.concurrent.duration._
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.io.IO
import akka.util.Timeout
import spray.can.Http
import com.lot.utils.ActorModuleImpl
import com.lot.utils.ConfigurationModuleImpl
import com.lot.utils.InitData
import com.lot.order.service.OrderRoutesActor
import com.lot.RoutesActor
import com.lot.user.service.UserManager._

object Boot extends App {

  // configuring modules for application, cake pattern for DI
  val modules = new ConfigurationModuleImpl with ActorModuleImpl

  // create and start our service actor
  val um = modules.system.actorOf(Props(classOf[UserManager]), "UserManager")

  um ! BlockAmount(26, 100)
  um ! UnBlockAmount(26, 100)
  //um ! AddAccountBalance(29, 1000)
  //um ! DeductBlockedAmount(26, 100)
}
