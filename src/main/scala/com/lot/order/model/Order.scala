package com.lot.order.model

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
import java.sql.Timestamp
import com.lot.security.model.SecurityTable


case class Order(id: Option[Long],
                 exchange: String,
                 buy_sell: String,
                 order_type: String,
                 user_id: Long,
                 security_id: Long,
                 quantity: Double,
                 var unfilled_qty: Double,
                 price: Double,
                 created_at: Option[DateTime],
                 updated_at: Option[DateTime]) 


object OrderType {
  val BUY = "BUY"
  val SELL = "SELL"
  val LIMIT = "LIMIT"
  val MARKET = "MARKET"  
}



class OrderTable(tag: Tag) extends Table[Order](tag, "orders") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def exchange = column[String]("exchange", O.Length(10,varying=true))
  def buy_sell = column[String]("buy_sell", O.Length(5,varying=true))
  def order_type = column[String]("order_type", O.Length(10,varying=true))
  def user_id = column[Long]("user_id")
  def security_id = column[Long]("security_id")
  def quantity = column[Double]("quantity")
  def unfilled_qty = column[Double]("unfilled_qty")
  def price = column[Double]("price")
  def created_at = column[DateTime]("created_at")
  def updated_at = column[DateTime]("updated_at")
  def * = (id.?, exchange, buy_sell, order_type, user_id, security_id, quantity, unfilled_qty, price, created_at.?, updated_at.?) <> (Order.tupled, Order.unapply)
}



object OrderJsonProtocol extends DefaultJsonProtocol {
  import com.lot.utils.CustomJson._
  implicit val orderFormat = jsonFormat11(Order)
}

