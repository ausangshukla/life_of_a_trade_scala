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


case class Order(id: Option[Long],
                 buy_sell: String,
                 order_type: String,
                 user_id: Int,
                 security_id: Int,
                 quantity: Double,
                 price: Double,
                 created_at: DateTime)

object OrderType {
  val BUY = "BUY"
  val SELL = "SELL"
  val LIMIT = "LIMIT"
  val MARKET = "MARKET"
}

class OrderTable(tag: Tag) extends Table[Order](tag, "ORDERS") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def buy_sell = column[String]("buy_sell")
  def order_type = column[String]("order_type")
  def user_id = column[Int]("user_id")
  def security_id = column[Int]("security_id")
  def quantity = column[Double]("quantity")
  def price = column[Double]("price")
  def created_at = column[DateTime]("created_at")
  def * = (id.?, buy_sell, order_type, user_id, security_id, quantity, price, created_at) <> (Order.tupled, Order.unapply)
}

object OrderJsonProtocol extends DefaultJsonProtocol {

  implicit object DateTimeFormat extends RootJsonFormat[DateTime] {

    val formatter = ISODateTimeFormat.basicDateTimeNoMillis

    def write(obj: DateTime): JsValue = {
      JsString(formatter.print(obj))
    }

    def read(json: JsValue): DateTime = json match {
      case JsString(s) => try {
        formatter.parseDateTime(s)
      } catch {
        case t: Throwable => error(s)
      }
      case _ =>
        error(json.toString())
    }

    def error(v: Any): DateTime = {
      val example = formatter.print(0)
      throw new Exception(f"'$v' is not a valid date value. Dates must be in compact ISO-8601 format, e.g. '$example'")
    }
  }


  implicit val orderFormat = jsonFormat8(Order)
}

