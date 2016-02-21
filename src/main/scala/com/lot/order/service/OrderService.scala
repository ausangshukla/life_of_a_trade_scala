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
import com.lot.user.model.User

trait OrderRestService extends BaseService {

  val REST_ENDPOINT = "orders"
  
  import com.lot.Json4sProtocol._

  val dao = OrderDao

  def list(current_user: User) = getJson {
    path(REST_ENDPOINT) {

      complete {
        dao.list(current_user)
      }
    }

  }

  val details = getJson {
    path(REST_ENDPOINT / IntNumber) { id =>
      complete(dao.get(id))
    }
  }

  def create(user:User) = postJson {
    path(REST_ENDPOINT) {
      entity(as[Order]) { o =>
        {
          logger.debug(s"Got order $o")
          complete({
            /*
             * Ensure the unfilled_qty is set to the quantity
             */
            val order = o.copy(unfilled_qty = o.quantity, user_id=user.id.get)
            /*
             * Save to the DB
             */
            logger.debug(s"Saving order $order")
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
    path(REST_ENDPOINT / IntNumber) { id =>
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
    path(REST_ENDPOINT / IntNumber) { id =>

      complete(dao.delete(id))

    }
  }

  def endpoints =
    auth {
      current_user =>
        {
          logger.info("current_user = " + current_user.email)
          list(current_user) ~ details ~ create(current_user) ~ update ~ destroy
        }
    }
}

object OrderService extends OrderRestService {
  
}