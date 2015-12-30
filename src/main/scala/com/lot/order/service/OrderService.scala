package com.lot.order.service

import com.lot.BaseService
import com.lot.order.dao.OrderDao
import com.lot.order.model.Order
import com.lot.order.model.OrderJsonProtocol
import scala.concurrent.ExecutionContext.Implicits.global
import com.lot.utils.CORSSupport
import com.lot.order.model.Order

object OrderService extends BaseService with CORSSupport {

  import com.lot.order.model.OrderJsonProtocol._
  import com.lot.Json4sProtocol._

  val dao = OrderDao
  
  val list = getJson {
    path("orders") {
      complete(dao.list)
    }

  }

  val details = getJson {
    path("orders" / IntNumber) { id =>
          complete(dao.get(id))              
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
  val update = putJson {
    path("orders" / IntNumber) { id =>
      entity(as[Order]) { order =>
        {
          complete(dao.update(order))
        }
      }
    }
  }
  val destroy = deleteJson {
    path("orders" / IntNumber) { id =>

      complete(dao.delete(id))

    }
  }

  val endpoints = 
    auth { 
      current_user => {
          println("current_user = " + current_user)
          list ~ details ~ create ~ update ~ destroy
      }
    }
}