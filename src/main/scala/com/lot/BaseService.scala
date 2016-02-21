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
import scala.concurrent.{ ExecutionContext, Future }
import spray.routing._
import Directives._
import spray.routing.authentication.{ Authentication, ContextAuthenticator }
import scala.concurrent.Await
import java.sql.Timestamp
import utils.CORSSupport
import com.lot.TokenAuthenticator.TokenAuthenticator
import com.typesafe.scalalogging.LazyLogging

object Json4sProtocol extends Json4sSupport {
  implicit def json4sFormats: Formats = DefaultFormats.lossless ++ org.json4s.ext.JodaTimeSerializers.all
}

trait BaseService extends SimpleRoutingApp with CORSSupport with LazyLogging {

  /**
   * The autheticator
   */
  val authenticator = TokenAuthenticator[User]() { (token, uid) =>
    UserDao.findByEmail(uid)
  }

  /**
   * The directive used to authenticate
   */
  def auth: Directive1[User] = authenticate(authenticator)

  /**
   * Allows for future authorization
   */
  def checkAccess(check: => Future[Boolean]): Directive0 =
    onSuccess(check).flatMap(authorize(_))

  /*
   * Json wrapped http methods follow
   */

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

  def putJson(route: Route) = put {
    respondWithMediaType(MediaTypes.`application/json`) {
      route
    }
  }

  def deleteJson(route: Route) = delete {
    respondWithMediaType(MediaTypes.`application/json`) {
      route
    }
  }

}