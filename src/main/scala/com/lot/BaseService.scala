package com.lot

import spray.routing.SimpleRoutingApp
import spray.routing.Route
import spray.http.MediaTypes
import spray.httpx.Json4sSupport
import org.json4s.DefaultFormats
import org.json4s.Formats

object Json4sProtocol extends Json4sSupport {
  implicit def json4sFormats: Formats = DefaultFormats
}

trait BaseService extends SimpleRoutingApp {

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