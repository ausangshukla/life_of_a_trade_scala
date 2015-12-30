package com.lot.security.model

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

case class Security(id: Option[Long],
    name: String,  ticker: String,  description: String,  price: Double,  asset_class: String,  sector: String,  region: String,  tick_size: Int,  liquidity: String, 
    created_at: Option[DateTime], 
    updated_at: Option[DateTime])

class SecurityTable(tag: Tag) extends Table[Security](tag, "securities") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def created_at = column[DateTime]("created_at")
  def updated_at = column[DateTime]("updated_at")
  def name = column[String]("name") 
  def ticker = column[String]("ticker") 
  def description = column[String]("description") 
  def price = column[Double]("price") 
  def asset_class = column[String]("asset_class") 
  def sector = column[String]("sector") 
  def region = column[String]("region") 
  def tick_size = column[Int]("tick_size") 
  def liquidity = column[String]("liquidity") 
 
  def * = (id.?,  name,  ticker,  description,  price,  asset_class,  sector,  region,  tick_size,  liquidity,  created_at.?, updated_at.?) <> (Security.tupled, Security.unapply)
}

object SecurityJsonProtocol extends DefaultJsonProtocol {
  import com.lot.utils.CustomJson._
  implicit val securityFormat = jsonFormat12(Security)
}

