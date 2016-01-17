package com.lot.trade.service

import com.lot.BaseService
import com.lot.trade.dao.TradeDao
import com.lot.trade.model.Trade
import com.lot.trade.model.TradeJsonProtocol
import scala.concurrent.ExecutionContext.Implicits.global


object TradeService extends BaseService {

  import com.lot.Json4sProtocol._

  val dao = TradeDao

  val list = getJson {
    path("trades") {
      complete(dao.list)
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

  val endpoints =
    list ~ details ~ create ~ update ~ destroy

}