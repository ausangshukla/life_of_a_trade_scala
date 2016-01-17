package com.lot.trade.service

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import com.lot.trade.model.Trade
import akka.actor.ActorLogging
import com.lot.trade.dao.TradeDao
import com.lot.trade.model.TradeMessage
import com.lot.trade.model.TradeState

class TradeGenerator extends Actor with ActorLogging {

  override def preStart = {
    
  }

  def receive = {
    case TradeMessage.New(trade)    => { handleNewTrade(trade) }
    case TradeMessage.Modify(trade) => { handleModifyTrade(trade) }
    case TradeMessage.Cancel(trade) => { handleCancelTrade(trade) }
    case msg                          => { log.error(s"Received unknown msg $msg")}
  }

  def handleNewTrade(trade: Trade) = {
    log.info(s"handleNewTrade: $trade")
    TradeDao.save(trade)
  }
  
  def handleModifyTrade(trade: Trade) = {
    log.info(s"handleModifyTrade: $trade")
    TradeDao.update(trade)
  }
  
  def handleCancelTrade(trade: Trade) = {
    log.info(s"handleCancelTrade: $trade")
    TradeDao.update(trade.copy(state=TradeState.CANCELLED))
  }
}