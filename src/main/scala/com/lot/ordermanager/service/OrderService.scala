package com.lot.ordermanager.service

import scala.collection.immutable.Vector
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.reflect.runtime.universe._
import com.lot.BaseService
import com.lot.ordermanager.dao.OrderDao
import com.lot.ordermanager.model.Order
import com.typesafe.scalalogging.LazyLogging
import akka.pattern.ask
import spray.http.MediaTypes._
import spray.http.StatusCodes._
import spray.httpx.Json4sSupport
import spray.routing._
import utils.Configuration
import utils.PersistenceModule
import scala.util.Success
import scala.util.Failure

object OrderService extends BaseService {

  import com.lot.ordermanager.model.OrderJsonProtocol._
  import com.lot.Json4sProtocol._
  
  val dao = new OrderDao()
  
  val list = getJson {
    path("orders") {
      complete(dao.list)
    }
  }

  val details = getJson {
    path("orders" / IntNumber) { id =>
      {
        complete(dao.get(id))
      }
    }
  }

  val create = postJson {
    path("orders") {
      entity(as[Order]) { order =>
        {
          complete(dao.save(order))
        }
      }
    }
  }
  val update = postJson {
    path("orders") {
      entity(as[Order]) { order =>
        {
          complete(dao.save(order))
        }
      }
    }
  }
  val destroy = postJson {
    path("orders") {
      entity(as[Order]) { order =>
        {
          complete(dao.save(order))
        }
      }
    }
  }

  val endpoints = list ~ details ~ create
}