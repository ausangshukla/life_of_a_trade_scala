package com.lot.ordermanager.service

import scala.concurrent.ExecutionContext.Implicits.global
import com.lot.BaseService
import com.lot.ordermanager.dao.OrderDao
import com.lot.ordermanager.model.Order

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