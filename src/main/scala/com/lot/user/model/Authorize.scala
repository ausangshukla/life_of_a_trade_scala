package com.lot.user.model

import com.lot.order.model.Order
import com.lot.order.model.Order

object Authorize {

  case class RoleAccess(current_user: User, entity: Any,
                        admin: Boolean = true, ops: Boolean, sim: Boolean,
                        trader: Boolean, guest: Boolean = false)

  def checkAccess(access: RoleAccess) = {
    access.current_user.role match {
      case UserRoles.ADMIN  => access.admin
      case UserRoles.OPS    => access.ops
      case UserRoles.SIM    => access.sim
      case UserRoles.TRADER => access.trader
      case UserRoles.GUEST  => access.guest
    }
  }

  /*
   * For Order
   */
  def update(current_user: User, order: Order) = {
    checkAccess(RoleAccess(current_user, order,
      admin = true, ops = true, sim = false,
      trader = (current_user.id.get == order.user_id), guest = false))
  }

  def cancel(current_user: User, order: Order) = {
    checkAccess(RoleAccess(current_user, order,
      admin = true, ops = true, sim = false,
      trader = (current_user.id.get == order.user_id), guest = false))
  }

  def create(current_user: User, order: Order) = {
    checkAccess(RoleAccess(current_user, order,
      admin = false, ops = false, sim = false, trader = true, guest = false))
  }

  def read(current_user: User, order: Order) = {
    checkAccess(RoleAccess(current_user, order,
      admin = true, ops = true, sim = false,
      trader = (current_user.id.get == order.user_id), guest = false))
  }

}