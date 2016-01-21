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

class PositionManager extends Actor with ActorLogging {

  override def preStart = {

  }

  def receive = {
    case TradeMessage.New(trade)    => { handleNewTrade(trade) }
    case TradeMessage.Modify(trade) => { handleModifyTrade(trade) }
    case TradeMessage.Cancel(trade) => { handleCancelTrade(trade) }
    case msg                        => { log.error(s"Received unknown msg $msg") }
  }

  def handleNewTrade(trade: Trade) = {
    log.info(s"handleNewTrade: $trade")
  }

  def handleModifyTrade(trade: Trade) = {
    log.info(s"handleModifyTrade: $trade")
  }

  def handleCancelTrade(trade: Trade) = {
    log.info(s"handleCancelTrade: $trade")
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
  private def updatePosition(trade: Trade, typeOfTrade: String) = {
    val pos = Await.result(PositionDao.get(trade.security_id, trade.user_id), 5 seconds)
    pos match {
      case Some(position) => {
        /*
         * Position already exists - compute the new quantity, average_price and pnl
         */
        val (totalQty, average_price, pnl) = typeOfTrade match {
          case "New" => {
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
          case "Cancel" => {
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
         * Update the DB
         */
        val newPos = position.copy(quantity = totalQty, average_price = average_price, pnl = pnl)
        PositionDao.update(newPos)

      }
      case None => {
        /*
         * No position exists for this security, create one
         */
        val position = Position(None, "", trade.security_id, trade.user_id, trade.quantity, trade.price, 0, None, None)
        val saved = PositionDao.save(position)
        Await.result(saved, 5 seconds)
      }
    }
  }
}