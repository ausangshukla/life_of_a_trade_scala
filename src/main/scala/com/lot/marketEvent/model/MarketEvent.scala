package com.lot.marketEvent.model

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
case class MarketEvent(id: Option[Long],
                       name: String,
                       event_type: String,
                       summary: String,
                       description: Option[String],
                       direction: String,
                       intensity: String,
                       asset_class: Option[String],
                       region: Option[String],
                       sector: Option[String],
                       ticker: Option[String],
                       external_url: Option[String],
                       created_at: Option[DateTime],
                       updated_at: Option[DateTime]) {

  def priceMultiplier(price: Double, factor: Int = 1) = {

    val pm = intensity match {
      case MarketEventType.INTENSITY_HIGH => 5.0
      case MarketEventType.INTENSITY_MED  => 3.0
      case MarketEventType.INTENSITY_LOW  => 1.0
    }

    direction match {
      case MarketEventType.DIRECTION_UP   => Math.round(price * (1 + factor * pm / 100.0))
      case MarketEventType.DIRECTION_DOWN => Math.round(price * (1 - factor * pm / 100.0))
    }

  }

}

/**
 * The DB schema
 */
class MarketEventTable(tag: Tag) extends Table[MarketEvent](tag, "market_events") {
  /*
   * Auto inc primary key
   */
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  /*
   * Updated automatically by the DAO on save
   */
  def created_at = column[Option[DateTime]]("created_at", O.Nullable)
  /*
   * Updated automatically by the DAO on update
   */
  def updated_at = column[Option[DateTime]]("updated_at", O.Nullable)
  /*
   * The rest of the domain specific fields
   */
  def name = column[String]("name")
  def event_type = column[String]("event_type", O.Length(10, varying = true))
  def summary = column[String]("summary", O.Length(255, varying = true))
  def description = column[String]("description", O.Nullable)
  def direction = column[String]("direction", O.Length(5, varying = true))
  def intensity = column[String]("intensity", O.Length(10, varying = true))
  def asset_class = column[String]("asset_class", O.Length(10, varying = true), O.Nullable)
  def region = column[String]("region", O.Length(10, varying = true), O.Nullable)
  def sector = column[String]("sector", O.Length(20, varying = true), O.Nullable)
  def ticker = column[String]("ticker", O.Length(5, varying = true), O.Nullable)
  def external_url = column[String]("external_url", O.Nullable)

  /*
  * Projection betw the DB and the model
  */
  def * = (id.?, name, event_type, summary, description.?, direction, intensity, asset_class.?, region.?, sector.?, ticker.?, external_url.?, created_at, updated_at) <> (MarketEvent.tupled, MarketEvent.unapply)
}

/**
 * The JSON protocol
 */
object MarketEventJsonProtocol extends CustomJson {
  implicit val marketEventFormat = jsonFormat14(MarketEvent)
}

object MarketEventType {
  val ASSET_CLASSES = List("Bond", "Stock", "Derivative")
  val REGIONS = List("NA", "EMEA", "APAC")
  val SECTORS = List("Tech", "Pharma", "Auto", "Finance", "Consumer")

  val TYPE_MARKET = "Market"
  val TYPE_NON_MARKET = "Non Market"

  val DIRECTION_DOWN = "Down"
  val DIRECTION_UP = "Up"

  val INTENSITY_HIGH = "High"
  val INTENSITY_MED = "Medium"
  val INTENSITY_LOW = "Low"

}