package com.lot.position.service

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import com.lot.position.model.Position
import akka.actor.ActorLogging
import com.lot.position.dao.PositionDao
import com.lot.trade.model.TradeMessage
import com.lot.trade.model.Trade
import com.lot.trade.model.TradeState
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import java.sql.SQLException

class PositionManager extends Actor with ActorLogging {

  override def preStart = {

  }

  def receive = {
    case msg @ TradeMessage.New(trade)    => { handleNewTrade(msg) }
    case msg @ TradeMessage.Cancel(trade) => { handleCancelTrade(msg) }
    case msg                              => { log.error(s"Received unknown msg $msg") }
  }

  def handleNewTrade(tradeMsg: TradeMessage.New) = {
    log.info(s"handleNewTrade: $tradeMsg")
    updatePosition(tradeMsg)
  }

  def handleCancelTrade(tradeMsg: TradeMessage.Cancel) = {
    log.info(s"handleCancelTrade: $tradeMsg")
    updatePosition(tradeMsg)
  }

  /**
   * Updates the position in the DB
   *
   * TODO: How to handle without Await ? Can this use case be solved without a blocking call?
   * Note that  the positions for the same security have to be updated sequentially,
   * so future updates via slick will cause problems if there are multiple trades for the same security
   *
   * @trade: The trade which is incoming and we need to create a position or update
   * an existing position for the security of the trade
   * @typeOfTrade: New or Cancel. Note Modify is a Cancel followed by a New
   */
  private def updatePosition(tradeMsg: TradeMessage.TradeMessage) = {

    val trade = tradeMsg match {
      case TradeMessage.New(trade)    => trade
      case TradeMessage.Cancel(trade) => trade
    }

    val posF = PositionDao.get(trade.security_id, trade.user_id)
    posF.map { pos =>
      pos match {
        case Some(position) => {
          /*
         * Position already exists - compute the new quantity, average_price and pnl
         */
          val (totalQty, average_price, pnl) = tradeMsg match {
            case TradeMessage.New(t) => {
              val totalQty = position.quantity + trade.quantity
              val purchaseValue = position.value + trade.value
              val average_price = purchaseValue / totalQty
              /*
	  	       * Use the trade price as the current price of the security to get the current market value
  	  	     */
              val pnl = totalQty * trade.price - purchaseValue
              /*
             * Return the tuple
             */
              (totalQty, average_price, pnl)

            }
            case TradeMessage.Cancel(t) => {
              val totalQty = position.quantity - trade.quantity
              val purchaseValue = totalQty * position.average_price
              val average_price = purchaseValue / totalQty
              /*
	  	       * Use the trade price as the current price of the security to get the current market value
  	  	     */
              val pnl = totalQty * trade.price - purchaseValue
              /*
             * Return the tuple
             */
              (totalQty, average_price, pnl)

            }
          }
          /*
         * Update the DB. Note that multipe trades may be updating the same position
         * Hence we use optimistic locking using the updated_at field.
         * If the update does not go thru, we retry updating the position by resending the 
         * msg to ourself
         */
          
          val newPos = position.copy(quantity = totalQty, average_price = average_price, pnl = pnl)
          
          log.debug(s"Updating position $position with $newPos")
          
          PositionDao.updateWithOptimisticLocking(newPos).map { rowCount =>
            rowCount match {
              case 1 => {
                /*
                 * Update succeeded
                 */
                log.debug("Position updated successfully")
              }
              case 0 => {
                /*
                 * No updates happened - try again
                 */
                log.debug("Position not updated successfully. Retrying")
                self ! tradeMsg
              }
            }
          }

        }
        case None => {
          /*
         * No position exists for this security, create one
         * NOTE that multiple trades may try and create the same position, but the DB will throw an Exception
         * as there is a unique key constraint on user_id & security_id
         * When this happens we retry the msg
         */
          val position = Position(None, "", trade.security_id, trade.user_id, trade.quantity, trade.price, 0, None, None)
          val saved = PositionDao.save(position)
          saved map {
            p => log.debug(s"Saved position $p")
          } recover {
            case s: SQLException =>
              log.debug(s"Error saving position $position: ${s.getMessage}. Retrying ...")
              self ! tradeMsg
          }
          Await.result(saved, 5 seconds)
        }
      }
    }
  }
}