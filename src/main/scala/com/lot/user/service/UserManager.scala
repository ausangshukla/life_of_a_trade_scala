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
import com.lot.user.service.UserManager._
import akka.actor.ReceiveTimeout
import akka.actor.Terminated

class UserManager() extends Actor with ActorLogging {

  var users = new HashMap[String, ActorRef]

  def receive = {

    case msg @ BlockAmount(user_id, amount) => {
      getUserActor(user_id) ! msg
    }
    case msg @ UnBlockAmount(user_id, amount) => {
      getUserActor(user_id) ! msg
    }
    case msg @ AddAccountBalance(user_id, amount) => {
      getUserActor(user_id) ! msg
    }
    case msg @ DeductBlockedAmount(user_id, amount) => {
      getUserActor(user_id) ! msg
    }
    case Terminated(deadUserActor) => {
      log.info(s"UserActor terminated: $deadUserActor")
      /*
       * Remove the UserActor from the list of cached users
       */
      users -= deadUserActor.path.name
    }
  }

  /**
   * Find the matcher for the given security_id in the order
   * @user_id: The user_id of the user for which the userActor is required
   * @return: The ActorRef of the userActor which will handle this user request
   */
  private def getUserActor(user_id: Long) = {

    val userActor = users.get(actorName(user_id))
    userActor match {
      // We have a userActor for the give user_id - lets use that
      case Some(m) => m
      // No userActor found - lets create, cache and use
      case None => {
        log.info(s"Creating userActor-$user_id")
        /*
         * build it
         */
        val ua = buildUserActor(user_id)
        /*
         * cache it using its name
         */
        users += (ua.path.name -> ua)
        /*
         * We also watch the new userActor and remove it from the users hash when it dies
         */
        context.watch(ua)

        ua
      }
    }
  }

  private def actorName(user_id: Long) = {
    s"UserActor-$user_id"
  }
  /**
   * Builds an OrderMatcher actor by passing it all the unfilled orders in the DB
   */
  private def buildUserActor(user_id: Long) = {
    /*
     * Create the OrderMatcher actor
     */
    context.actorOf(Props(classOf[UserActor], user_id), actorName(user_id))
  }

}

object UserManager {

  sealed trait Message
  /**
   * Block the amount requested for the user
   */
  case class BlockAmount(user_id: Long, amount: Double) extends Message
  /**
   * Unblock the amount
   */
  case class UnBlockAmount(user_id: Long, amount: Double) extends Message
  /**
   * Add to the amount of a user
   */
  case class AddAccountBalance(user_id: Long, amount: Double) extends Message
  /**
   * Deduct the amount specified from the blocked amount
   */
  case class DeductBlockedAmount(user_id: Long, amount: Double) extends Message
}