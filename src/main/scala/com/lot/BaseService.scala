package com.lot

import spray.routing.SimpleRoutingApp
import spray.routing.Route
import spray.http.MediaTypes
import spray.httpx.Json4sSupport
import org.json4s.DefaultFormats
import org.json4s.Formats
import com.lot.user.model.User
import scala.concurrent.ExecutionContext.Implicits.global
import spray.routing.Directive
import com.lot.user.dao.UserDao
import scala.concurrent.{ExecutionContext, Future}
import spray.routing._
import Directives._
import spray.routing.authentication.{Authentication, ContextAuthenticator}
import scala.concurrent.Await


object Json4sProtocol extends Json4sSupport {
  implicit def json4sFormats: Formats = DefaultFormats
}

trait BaseService extends SimpleRoutingApp {

  import scala.concurrent.duration._
  
  val authenticator = TokenAuthenticator[User](
    headerName = "My-Api-Key",
    queryStringParameterName = "api_key") { key =>
      UserDao.get(key.toInt)
    }

  def auth: Directive1[User] = authenticate(authenticator)

  def getJson(route: Route) = get {
    respondWithMediaType(MediaTypes.`application/json`) {
      route
    }
  }

  def postJson(route: Route) = post {
    respondWithMediaType(MediaTypes.`application/json`) {
      route
    }
  }

}