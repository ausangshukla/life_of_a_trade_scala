package com.lot.trade.model

import java.sql.Date
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.H2JodaSupport._
import spray.json.DefaultJsonProtocol
import spray.json.RootJsonFormat
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter
import spray.json.JsString
import spray.json.JsValue
import spray.json.DeserializationException
import org.joda.time.format.ISODateTimeFormat
import com.lot.utils.CustomJson

/**
 * The model class
 */
case class Trade(id: Option[Long],
                 trade_date: DateTime, settlement_date: DateTime,
                 security_id: Long, quantity: Double, price: Double, buy_sell: String,
                 user_id: Long, order_id: Long, matched_order_id: Long,
                 state: String,
                 exchange:String,
                 commissions: Double=0,
                 taxes: Double=0,
                 total_amount:Double=0,
                 created_at: Option[DateTime]=None,
                 updated_at: Option[DateTime]=None) {
  
  def value = quantity * price
  
}

/**
 * The allowed states for the trade to be in
 */
object TradeState {
  val ACTIVE = "Active"
  val CANCELLED = "Cancelled"
  val SETTLED = "Settled"
}
/**
 * DB schema
 */
class TradeTable(tag: Tag) extends Table[Trade](tag, "trades") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def trade_date = column[DateTime]("trade_date")
  def settlement_date = column[DateTime]("settlement_date")
  def security_id = column[Long]("security_id")
  def quantity = column[Double]("quantity")
  def price = column[Double]("price")
  def buy_sell = column[String]("buy_sell")
  def user_id = column[Long]("user_id")
  def order_id = column[Long]("order_id")
  def matched_order_id = column[Long]("matched_order_id")
  def state = column[String]("state")
  def exchange = column[String]("exchange")  
  def commissions = column[Double]("commissions")
  def taxes = column[Double]("taxes")
  def total_amount = column[Double]("total_amount")
  def created_at = column[DateTime]("created_at")
  def updated_at = column[DateTime]("updated_at")

  def * = (id.?, trade_date, settlement_date, security_id, quantity, price, buy_sell, 
          user_id, order_id, matched_order_id, state, exchange, 
          commissions, taxes, total_amount, created_at.?, updated_at.?) <> (Trade.tupled, Trade.unapply)
}

/**
 * To convert to and from JSON
 */
object TradeJsonProtocol extends CustomJson {
  implicit val tradeFormat = jsonFormat17(Trade)
}

/**
 * Message singleton
 */
object TradeMessage {
  /*
   * These are the messages that the Exchange can receive
   */
  sealed trait TradeMessage
  case class New(trade: Trade) extends TradeMessage
  //case class Modify(trade: Trade) extends TradeMessage
  case class Cancel(trade: Trade) extends TradeMessage

}

