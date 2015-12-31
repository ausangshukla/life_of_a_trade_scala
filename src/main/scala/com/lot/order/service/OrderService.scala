package com.lot.order.service

import scala.concurrent.duration._
import com.lot.BaseService
import com.lot.order.dao.OrderDao
import com.lot.order.model.Order
import com.lot.order.model.OrderJsonProtocol
import scala.concurrent.ExecutionContext.Implicits.global
import com.lot.utils.CORSSupport
import com.lot.order.model.Order
import akka.actor.ActorContext
import com.lot.exchange.Message._
import org.joda.time.DateTime
import akka.util.Timeout
import com.lot.exchange.Exchange


class OrderService(context: ActorContext) extends BaseService with CORSSupport {

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
          complete({
            val saved = dao.save(order)
            Exchange.exchanges.get(order.exchange).map { exchange =>
              logger.info(s"Sending order to exchange $exchange")
              saved.map(exchange ! NewOrder(_, new DateTime()))
            }
            saved
           })
        }
      }
    }
  }
  val update = putJson {
    path("orders" / IntNumber) { id =>
      entity(as[Order]) { order =>
        {
          complete({
            dao.update(order)
            Exchange.exchanges.get(order.exchange).map { exchange => 
              logger.info(s"Sending order to exchange $exchange")
              exchange ! ModifyOrder(order, new DateTime())
            }
            order
          })
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
          logger.info("current_user = " + current_user.email)
          list ~ details ~ create ~ update ~ destroy
      }
    }
}