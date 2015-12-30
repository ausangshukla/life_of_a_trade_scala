package com.lot.security.service

import com.lot.BaseService
import com.lot.security.dao.SecurityDao
import com.lot.security.model.Security
import com.lot.security.model.SecurityJsonProtocol
import scala.concurrent.ExecutionContext.Implicits.global


object SecurityService extends BaseService {

  import com.lot.security.model.SecurityJsonProtocol._
  import com.lot.Json4sProtocol._

  val dao = SecurityDao

  val list = getJson {
    path("securities") {
      complete(dao.list)
    }
  }

  val details = getJson {
    path("securities" / IntNumber) { id =>
      {
        complete(dao.get(id))
      }
    }
  }

  val create = postJson {
    path("securities") {
      entity(as[Security]) { security =>
        {
          complete(dao.save(security))
        }
      }
    }
  }
  val update = putJson {
    path("securities" / IntNumber) { id =>
      entity(as[Security]) { security =>
        {
          complete(dao.update(security))
        }
      }
    }
  }
  val destroy = deleteJson {
    path("securities" / IntNumber) { id =>

      complete(dao.delete(id))

    }
  }

  val endpoints =
    list ~ details ~ create ~ update ~ destroy

}