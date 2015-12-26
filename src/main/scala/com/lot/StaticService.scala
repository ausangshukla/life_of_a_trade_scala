package com.lot

import spray.routing.HttpService
import com.typesafe.config.ConfigFactory
import spray.routing.Directive.pimpApply

trait StaticService extends HttpService {

  val conf = ConfigFactory.load()
  val webroot = conf.getString("webRoot")

  val staticRoute = {
    println("webRoot =" + webroot)
    path("/index.html") {
      getFromResource(webroot + "/src/client/index.html")
    } ~ path("/app") {
      getFromResource(webroot + "/src/client/app")
    } ~ {
      getFromDirectory(webroot)
    }
  }
}
