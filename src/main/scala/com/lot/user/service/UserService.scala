package com.lot.user.service

import scala.collection.immutable.Vector
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.reflect.runtime.universe._
import com.lot.BaseService
import com.lot.user.dao.UserDao
import com.lot.user.model.User
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

object UserService extends BaseService {

  import com.lot.user.model.UserJsonProtocol._
  import com.lot.Json4sProtocol._
  
  val dao =  UserDao
  
  val list = getJson {
    path("users") {
      complete(dao.list)
    }
  }

  val details = getJson {
    path("users" / IntNumber) { id =>
      {
        complete(dao.get(id))
      }
    }
  }

  val create = postJson {
    path("users") {
      entity(as[User]) { user =>
        {
          complete(dao.save(user))
        }
      }
    }
  }
  val update = postJson {
    path("users") {
      entity(as[User]) { user =>
        {
          complete(dao.save(user))
        }
      }
    }
  }
  val destroy = postJson {
    path("users") {
      entity(as[User]) { user =>
        {
          complete(dao.save(user))
        }
      }
    }
  }

  val endpoints = list ~ details ~ create
}