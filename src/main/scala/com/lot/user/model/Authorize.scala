package com.lot.user.model

import com.lot.order.model.Order
import com.lot.order.model.Order
import com.lot.order.model.Order
import com.lot.trade.model.Trade
import com.lot.position.model.Position
import com.lot.marketEvent.model.MarketEvent
import com.lot.marketEvent.model.TriggeredEvent
import com.lot.security.model.Security
import com.lot.blockAmount.model.BlockAmount
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import com.typesafe.scalalogging.LazyLogging
import com.lot.order.model.Order
import com.lot.position.model.Position
import com.lot.trade.model.Trade

object Authorize extends LazyLogging {

  val READ = "Read"
  val LIST = "List"
  val CREATE = "Create"
  val UPDATE = "Update"
  val DELETE = "Delete"

  val classOfUser = classOf[User]
  val classOfPosition = classOf[Position]
  val classOfTrade = classOf[Trade]
  val classOfBlockAmount = classOf[BlockAmount]
  val classOfOrder = classOf[Order]
  val classOfsecurity = classOf[Security]

  /**
   * The central authorize method
   */
  def authorize(access: String, current_user: User, entity: Option[Any]): Boolean = {
    logger.debug(s"$access requested by ${current_user.id} : ${current_user.role} for $entity")
    current_user.role match {
      case UserRoles.ADMIN  => adminAccess(access, current_user, entity)
      case UserRoles.OPS    => adminAccess(access, current_user, entity)
      case UserRoles.SIM    => traderAccess(access, current_user, entity)
      case UserRoles.TRADER => traderAccess(access, current_user, entity)
      case UserRoles.GUEST  => guestAccess(access, current_user, entity)
      case _                => false
    }
  }

  /**
   * The central authorize method
   */
  def authorize(access: String, current_user: User, entityF: Future[Option[Any]]): Future[Boolean] = {
    entityF.map { entity =>
      authorize(access, current_user, entity)
    }
  }

  /**
   * All access to admin role is defined here
   */
  private def adminAccess(access: String, current_user: User, entity: Option[Any]) = {
    entity match {
      /*
       * Access to Order
       */
      case Some(e: Order) => {
        access match {
          case CREATE => false
          case _      => true
        }
      }
      /*
       * Access to Trade, Position
       */
      case Some(e: Trade) => {
        access match {
          case READ => true
          case _    => false
        }
      }
      case Some(e: Position) => {
        access match {
          case READ => true
          case _    => false
        }
      }
      case Some(e: BlockAmount) => {
        access match {
          case READ => true
          case _    => false
        }
      }

      /*
       * Access everything we have forgotten to explicitly authorize
       */
      case _ => {
        access match {
          case _ => true
        }
      }

    }
  }

  /**
   * All access to the trader role is defined here
   */
  private def traderAccess(access: String, current_user: User, entity: Option[Any]) = {
    entity match {
      /*
       * Access to Order
       */
      case Some(e: Order) => {
        access match {
          case DELETE => false
          case CREATE => true
          case _      => e.user_id == current_user.id.get
        }
      }
      /*
       * Access to Trade, Position
       */
      case Some(e: Trade) => {
        access match {
          case READ => e.user_id == current_user.id.get
          case _    => false
        }
      }
      case Some(e: Position) => {
        access match {
          case READ => e.user_id == current_user.id.get
          case _    => false
        }
      }
      case Some(e: BlockAmount) => {
        access match {
          case LIST => true
          case READ => e.user_id == current_user.id.get
          case _    => false
        }
      }

      /*
       * Access to others
       */
      case Some(e: User) => {
        access match {
          case READ => true
          case _    => false

        }
      }
      case Some(e: Security) => {
        access match {
          case READ => true
          case _    => false

        }
      }
      case Some(e: TriggeredEvent) => {
        access match {
          case READ => true
          case _    => false

        }
      }

      case Some(Order) | Some(Position) | Some(Trade) | Some(BlockAmount) | Some(Security) | Some(TriggeredEvent) => {
        access match {
          case LIST => true
          case _    => false
        }
      }

      case _ => {
        access match {
          case _ => false
        }
      }

    }
  }

  /**
   * All access to Guest role
   */
  private def guestAccess(access: String, current_user: User, entity: Any) = {
    entity match {
      case Some(Trade) | Some(TriggeredEvent) | Some(Security) => {
        access match {
          case READ | LIST => true
          case _           => false
        }
      }
      /*
       * Access to something we have forgotten to explicitly authorize
       */
      case _ => {
        access match {
          case _ => false
        }
      }

    }
  }

}