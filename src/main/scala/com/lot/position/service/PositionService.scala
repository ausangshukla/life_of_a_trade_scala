package com.lot.position.service

import com.lot.BaseService
import com.lot.position.dao.PositionDao
import com.lot.position.model.Position
import com.lot.position.model.PositionJsonProtocol
import scala.concurrent.ExecutionContext.Implicits.global


object PositionService extends BaseService {

  import com.lot.Json4sProtocol._

  val dao = PositionDao

  val list = getJson {
    path("positions") {
      complete(dao.list)
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
    list ~ details ~ create ~ update ~ destroy

}