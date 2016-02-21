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

object Authorize {

  val READ = "Read"
  val LIST = "List"
  val CREATE = "Create"
  val UPDATE = "Update"
  val DELETE = "Delete"

  /**
   * The central authorize method
   */
  def authorize(access: String, current_user: User, entity: Option[Any]): Boolean = {
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
      case Some(Trade) | Some(Position) | Some(BlockAmount) => {
        access match {
          case READ => true
          case _    => false
        }
      }
      /*
       * Access to others
       */
      case Some(User) | Some(MarketEvent) | Some(TriggeredEvent) | Some(Security) => {
        access match {
          case _ => true
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
          case READ => e.user_id == current_user.id.get
          case _    => false
        }
      }

      /*
       * Access to others
       */
      case Some(User) | Some(Security) | Some(TriggeredEvent) => {
        access match {
          case READ => true
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
          case READ => true
          case _    => false
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