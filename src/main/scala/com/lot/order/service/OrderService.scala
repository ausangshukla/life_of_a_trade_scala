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


object OrderService extends BaseService {

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
            /*
             * Ensure the unfilled_qty is set to the quantity
             */
            order.unfilled_qty = order.quantity
            /*
             * Save to the DB
             */
            val savedOrder = dao.save(order)
            /*
             * Send it to the exchange for execution
             */
            Exchange.exchanges.get(order.exchange).map { exchange =>
              savedOrder.map(exchange ! NewOrder(_, new DateTime()))
            }
            /*
             * Return the saved but yet unmatched order
             */
            savedOrder
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