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

case class Trade(id: Option[Long],
    trade_date: DateTime,  security_id: Int, 
    created_at: Option[DateTime], 
    updated_at: Option[DateTime])

class TradeTable(tag: Tag) extends Table[Trade](tag, "ORDERS") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def created_at = column[DateTime]("created_at")
  def updated_at = column[DateTime]("updated_at")
  def trade_date = column[DateTime]("trade_date") 
  def security_id = column[Int]("security_id") 
 
  def * = (id.?,  trade_date,  security_id,  created_at.?, updated_at.?) <> (Trade.tupled, Trade.unapply)
}

object TradeJsonProtocol extends DefaultJsonProtocol {
  import com.lot.utils.CustomJson._
  implicit val tradeFormat = jsonFormat5(Trade)
}

