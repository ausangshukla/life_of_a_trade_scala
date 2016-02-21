package com.lot.position.service

import com.lot.BaseService
import com.lot.position.dao.PositionDao
import com.lot.position.model.Position
import com.lot.position.model.PositionJsonProtocol
import scala.concurrent.ExecutionContext.Implicits.global
import com.lot.user.model.User

trait PositionRestService extends BaseService {

  import com.lot.Json4sProtocol._

  val dao = PositionDao

  def list(current_user: User) = getJson {
    path("positions") {
      complete(dao.list(current_user))
    }
  }

  val details = getJson {
    path("positions" / IntNumber) { id =>
      {
        complete(dao.get(id))
      }
    }
  }

  val create = postJson {
    path("positions") {
      entity(as[Position]) { position =>
        {
          complete(dao.save(position))
        }
      }
    }
  }
  val update = putJson {
    path("positions" / IntNumber) { id =>
      entity(as[Position]) { position =>
        {
          complete(dao.update(position))
        }
      }
    }
  }
  val destroy = deleteJson {
    path("positions" / IntNumber) { id =>

      complete(dao.delete(id))

    }
  }

  val endpoints =
    auth {
      current_user =>
        {
          logger.info("current_user = " + current_user.email)
          list(current_user) ~ details ~ update
        }
    }

}

object PositionService extends PositionRestService {

}