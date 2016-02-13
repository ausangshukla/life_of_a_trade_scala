package com.lot.user.service

import akka.actor.Actor
import akka.actor.ActorLogging
import com.lot.exchange.Message.NewOrder
import com.lot.exchange.Message.ModifyOrder
import com.lot.exchange.Message.CancelOrder
import com.lot.order.model.Order
import scala.concurrent.duration._
import akka.util.Timeout
import scala.concurrent.Await
import akka.actor.ActorRef
import com.lot.security.model.PriceMessage
import com.lot.security.model.Price
import akka.pattern.ask
import scala.concurrent.ExecutionContext.Implicits.global
import com.lot.user.dao.UserDao
import scala.collection.immutable.HashMap
import akka.actor.Props
import com.lot.user.service.UserManagerMessages._
import akka.actor.ReceiveTimeout
import com.lot.utils.GenericMessages._

/**
 * Used to manage the updates to the user account_balance and blocked_amount
 */
class UserActor(val user_id: Long) extends Actor with ActorLogging {

  override def preStart = {
    /*
     * Ensure this actor times out and does not hog resources
     */
    context.setReceiveTimeout(120 seconds)
  }

  def receive = {
    case msg @ BlockAmount(user_id, order_id, amount) => {
      log.debug(s"$msg")
      val update = Await.result(UserDao.addBlockedAmount(user_id, order_id, amount), 5 seconds)
      update match {
        case 1 => sender ! Success
        case 0 => sender ! Failure
      }
    }
    case msg @ UnBlockAmount(user_id, order_id, amount) => {
      log.debug(s"$msg")
      val update = Await.result(UserDao.unBlockedAmount(user_id, order_id, amount * -1), 5 seconds)
      update match {
        case 1 => sender ! Success
        case 0 => sender ! Failure
      }
    }
    case msg @ AddAccountBalance(user_id, amount) => {
      log.debug(s"$msg")
      val update = Await.result(UserDao.addAccountBalance(user_id, amount), 5 seconds)
      update match {
        case 1 => sender ! Success
        case 0 => sender ! Failure
      }
    }
    case msg @ DeductBlockedAmount(user_id, order_id, amount) => {
      log.debug(s"$msg")
      val update = Await.result(UserDao.deductBlockedAmount(user_id, order_id, amount), 5 seconds)
      update match {
        case 1 => sender ! Success
        case 0 => sender ! Failure
      }
    }
    case ReceiveTimeout => {
      // To turn it off
      log.warning(s"Stopping $self due to timeout")
      context.setReceiveTimeout(Duration.Undefined)
      context.stop(self)
    }

  }

}