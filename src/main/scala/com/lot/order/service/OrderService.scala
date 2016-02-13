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
import akka.actor.ActorSystem
import com.lot.utils.ConfigurationModuleImpl
import akka.actor.Props
import com.lot.trade.service.SecurityManager
import akka.routing.FromConfig
import com.lot.user.service.UserManager
import akka.actor.ActorRef
import com.lot.order.model.Order
import com.lot.security.model.Security
import com.lot.order.model.OrderSec

trait OrderRestService extends BaseService {

  import com.lot.Json4sProtocol._

  val dao = OrderDao

  val list = getJson {
    path("orders") {

      complete {
        dao.list.map { seq =>
          seq.map { os =>
            {
              OrderSec(os._1, os._2)
            }
          }
        }
      }
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
          logger.debug(s"1. Saving order $order")
          complete({
            /*
             * Ensure the unfilled_qty is set to the quantity
             */
            order.unfilled_qty = order.quantity
            /*
             * Save to the DB
             */
            logger.debug(s"2. Saving order $order")
            val savedOrder = dao.save(order)

            val preCheck: ActorRef = OrderPreCheck()
            savedOrder.map { order =>
              logger.debug(s"Precheck order $order")
              preCheck ! NewOrder(order, new DateTime())
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
      current_user =>
        {
          logger.info("current_user = " + current_user.email)
          list ~ details ~ create ~ update ~ destroy
        }
    }
}

object OrderService extends OrderRestService {
  
}