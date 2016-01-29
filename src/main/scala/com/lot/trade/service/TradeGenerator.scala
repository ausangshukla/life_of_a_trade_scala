package com.lot.trade.service

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import com.lot.trade.model.Trade
import akka.actor.ActorLogging
import com.lot.trade.dao.TradeDao
import com.lot.trade.model.TradeMessage
import com.lot.trade.model.TradeState
import akka.actor.ActorRef
import scala.concurrent.ExecutionContext.Implicits.global

class TradeGenerator(positionManager: ActorRef) extends Actor with ActorLogging {

  override def preStart = {
    
  }

  def receive = {
    case TradeMessage.New(trade)    => { handleNewTrade(trade) }
    case TradeMessage.Cancel(trade) => { handleCancelTrade(trade) }
    case msg                          => { log.error(s"Received unknown msg $msg")}
  }

  def handleNewTrade(trade: Trade) = {
    log.info(s"handleNewTrade: $trade")
    TradeDao.save(trade).map { t =>
      positionManager ! TradeMessage.New(t)
    }
  }
  
  
  def handleCancelTrade(trade: Trade) = {
    log.info(s"handleCancelTrade: $trade")
    TradeDao.update(trade.copy(state=TradeState.CANCELLED)).map { rowCount =>
      rowCount match {
        case 1 => positionManager ! TradeMessage.Cancel(trade)
        case 0 => log.error(s"Manual intervention required, Could not cancel trade $trade")
      }
    }
  }
}