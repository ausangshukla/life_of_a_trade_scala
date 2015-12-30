package com.lot

import spray.routing.HttpService
import com.typesafe.config.ConfigFactory
import spray.routing.Directive.pimpApply
import scala.concurrent.ExecutionContext.Implicits.global

trait StaticService extends HttpService {

  val conf = ConfigFactory.load()
  val webroot = conf.getString("webRoot")

  val staticRoute = {
    println("webRoot =" + webroot)
    path("") {
      getFromFile(webroot + "/src/client/index.html")
    } ~ pathPrefix("app") {
      getFromDirectory(webroot + "/src/client/app")
    } ~ pathPrefix("images") {
      getFromDirectory(webroot + "/src/client/images")
    }  ~ pathPrefix("styles") {
      getFromDirectory(webroot + "/src/client/styles")
    }  ~ pathPrefix("fonts") {
      getFromDirectory(webroot + "/src/client/styles/fonts")
    } ~ {
      getFromDirectory(webroot)
    }
  }
}
