package com.lot.user.service

import com.lot.BaseService
import com.lot.user.dao.UserDao
import com.lot.user.model.User
import com.lot.user.model.UserJsonProtocol
import scala.concurrent.ExecutionContext.Implicits.global
import utils.CORSSupport

object UserService extends BaseService with CORSSupport {

  import com.lot.user.model.UserJsonProtocol._
  import com.lot.Json4sProtocol._

  val dao = UserDao

  val list = getJson {
    path("users") {
      complete(dao.list)
    }

  }

  val details = getJson {
    path("users" / IntNumber) { id =>
      auth { current_user =>        
        {
          println("current_user = " + current_user)
          complete(dao.get(id))
        }
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
  val update = putJson {
    path("users" / IntNumber) { id =>
      entity(as[User]) { user =>
        {
          complete(dao.update(user))
        }
      }
    }
  }
  val destroy = deleteJson {
    path("users" / IntNumber) { id =>

      complete(dao.delete(id))

    }
  }

  val endpoints =
    list ~ details ~ create ~ update ~ destroy

}