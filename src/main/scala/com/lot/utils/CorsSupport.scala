package com.lot.utils

import spray.routing.Directives
import spray.http._
import spray.http.parser._
import spray.routing.Route
import spray.http.HttpHeaders._
import spray.http.HttpMethods._
import spray.httpx.marshalling.ToResponseMarshallable.isMarshallable
import spray.routing.Directive.pimpApply

trait CORSSupport extends Directives {
   private val CORSHeaders = List(
     `Access-Control-Allow-Methods`(GET, POST, PUT, DELETE, OPTIONS),
     `Access-Control-Allow-Headers`("Origin, X-Requested-With, If-Modified-Since, Content-Type, Accept, Accept-Encoding, Accept-Language, Host, Referer, User-Agent, Authorization"),
     `Access-Control-Allow-Credentials`(true)
   )

   def respondWithCORS(origin: String)(routes: => Route) = {
     val originHeader = `Access-Control-Allow-Origin`(SomeOrigins(Seq(HttpOrigin(origin))))
     respondWithHeaders(originHeader :: CORSHeaders) {
       routes ~ options { complete(StatusCodes.OK) }
     }
   }
 }