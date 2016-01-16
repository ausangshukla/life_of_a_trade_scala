package com.lot.trade.service

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import com.lot.trade.model.Trade
import akka.actor.ActorLogging
import com.lot.trade.dao.TradeDao
import com.lot.trade.model.TradeMessage

class TradeGenerator extends Actor with ActorLogging {

  override def preStart = {

  }

  def receive = {
    case TradeMessage.New(trade)    => { handleNewTrade(trade) }
    case TradeMessage.Modify(trade) => { handleModifyTrade(trade) }
    case TradeMessage.Cancel(trade) => { handleCancelTrade(trade) }
    case _                          => {}
  }

  def handleNewTrade(trade: Trade) = {

  }
  
  def handleModifyTrade(trade: Trade) = {

  }
  
  def handleCancelTrade(trade: Trade) = {

  }
}