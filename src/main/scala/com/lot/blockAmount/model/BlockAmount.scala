package com.lot.blockAmount.model

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
case class BlockAmount(id: Option[Long],
    user_id: Long,  order_id: Long,  
    blocked_amount: Double,  
    actual_amount_charged: Double=0,  
    status: String="Blocked", 
    created_at: Option[DateTime]=None, 
    updated_at: Option[DateTime]=None)

/**
 * The DB schema
 */
class BlockAmountTable(tag: Tag) extends Table[BlockAmount](tag, "block_amounts") {
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
  def user_id = column[Long]("user_id", O.Nullable) 
  def order_id = column[Long]("order_id", O.Nullable) 
  def blocked_amount = column[Double]("blocked_amount", O.Nullable) 
  def actual_amount_charged = column[Double]("actual_amount_charged", O.Nullable) 
  def status = column[String]("status", O.Nullable) 
 
 /*
  * Projection betw the DB and the model
  */
  def * = (id.?,  user_id,  order_id,  blocked_amount,  actual_amount_charged,  status,  created_at, updated_at) <> (BlockAmount.tupled, BlockAmount.unapply)
}


object BlockAmountType {
  val BLOCKED = "Blocked"
  val DEDUCTED = "Deducted"
  
  val TYPES = List(BLOCKED, DEDUCTED)
}

/**
 * The JSON protocol
 */
object BlockAmountJsonProtocol extends CustomJson {
  implicit val blockAmountFormat = jsonFormat8(BlockAmount)
}

