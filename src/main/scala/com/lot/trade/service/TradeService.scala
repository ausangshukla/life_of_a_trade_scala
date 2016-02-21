package com.lot.trade.service

import com.lot.BaseService
import com.lot.trade.dao.TradeDao
import com.lot.trade.model.Trade
import com.lot.trade.model.TradeJsonProtocol
import scala.concurrent.ExecutionContext.Implicits.global
import com.lot.user.model.User

trait TradeRestService extends BaseService {

  import com.lot.Json4sProtocol._

  val dao = TradeDao

  def list(current_user: User) = getJson {
    path("trades") {
      complete(dao.list(current_user))
    }
  }

  val details = getJson {
    path("trades" / IntNumber) { id =>
      {
        complete(dao.get(id))
      }
    }
  }

  val create = postJson {
    path("trades") {
      entity(as[Trade]) { trade =>
        {
          complete(dao.save(trade))
        }
      }
    }
  }
  val update = putJson {
    path("trades" / IntNumber) { id =>
      entity(as[Trade]) { trade =>
        {
          complete(dao.update(trade))
        }
      }
    }
  }
  val destroy = deleteJson {
    path("trades" / IntNumber) { id =>

      complete(dao.delete(id))

    }
  }

  def endpoints = auth {
    current_user =>
      {
        logger.info("current_user = " + current_user.email)
        list(current_user) ~ details ~ create ~ update ~ destroy
      }
  }

}

object TradeService extends TradeRestService {

}