package com.lot.position.model

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

case class Position(id: Option[Long],
    ticker: String,  security_id: Long,  user_id: Long, quantity: Double,  average_price: Double,  pnl: Double, 
    created_at: Option[DateTime], 
    updated_at: Option[DateTime]) {
  
  def value = quantity * average_price

}

class PositionTable(tag: Tag) extends Table[Position](tag, "positions") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def created_at = column[DateTime]("created_at", O.Nullable)
  def updated_at = column[DateTime]("updated_at", O.Nullable)
  def ticker = column[String]("ticker") 
  def security_id = column[Long]("security_id")
  def user_id = column[Long]("user_id") 
  def quantity = column[Double]("quantity") 
  def average_price = column[Double]("average_price") 
  def pnl = column[Double]("pnl") 
 
  def * = (id.?,  ticker,  security_id,  user_id, quantity,  average_price,  pnl,  created_at.?, updated_at.?) <> (Position.tupled, Position.unapply)
}

object PositionJsonProtocol extends CustomJson {
  implicit val positionFormat = jsonFormat9(Position)
}

