package com.lot.user.model
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.H2JodaSupport._
import spray.json.DeserializationException
import java.sql.Timestamp
import spray.json._
import DefaultJsonProtocol._
import com.lot.utils.CustomJson
import org.joda.time.DateTime

/**
 * Entity class storing rows of table Users
 *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
 *  @param first_name Database column first_name SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param last_name Database column last_name SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param email Database column email SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param createdAt Database column created_at SqlType(DATETIME), Default(None)
 *  @param updatedAt Database column updated_at SqlType(DATETIME), Default(None)
 *  @param encryptedPassword Database column encrypted_password SqlType(VARCHAR), Length(255,true), Default()
 *  @param resetPasswordToken Database column reset_password_tokens SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param resetPasswordSentAt Database column reset_password_sent_at SqlType(DATETIME), Default(None)
 *  @param rememberCreatedAt Database column remember_created_at SqlType(DATETIME), Default(None)
 *  @param signInCount Database column sign_in_count SqlType(INT), Default(0)
 *  @param currentSignInAt Database column current_sign_in_at SqlType(DATETIME), Default(None)
 *  @param lastSignInAt Database column last_sign_in_at SqlType(DATETIME), Default(None)
 *  @param currentSignInIp Database column current_sign_in_ip SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param lastSignInIp Database column last_sign_in_ip SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param confirmationToken Database column confirmation_tokens SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param confirmedAt Database column confirmed_at SqlType(DATETIME), Default(None)
 *  @param confirmationSentAt Database column confirmation_sent_at SqlType(DATETIME), Default(None)
 *  @param unconfirmedEmail Database column unconfirmed_email SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param role Database column role SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param teamId Database column team_id SqlType(INT), Default(None)
 *  @param contestId Database column contest_id SqlType(INT), Default(None)
 */
case class User(id: Option[Long], first_name: String, last_name: String, email: String,
                created_at: Option[DateTime], updated_at: Option[DateTime],
                encrypted_password: String = "",
                role: String, account_balance: Double, blocked_amount: Double, tokens: Option[String])

/** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
class UserTable(_tableTag: Tag) extends Table[User](_tableTag, "users") {
  def * = (id.?, first_name, last_name, email, created_at.?, updated_at.?, encrypted_password, role, account_balance, blocked_amount, tokens) <> (User.tupled, User.unapply)
 
  /** Database column id SqlType(INT), AutoInc, PrimaryKey */
  val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
  /** Database column first_name SqlType(VARCHAR), Length(255,true), Default(None) */
  val first_name: Rep[String] = column[String]("first_name", O.Length(255, varying = true))
  /** Database column last_name SqlType(VARCHAR), Length(255,true), Default(None) */
  val last_name: Rep[String] = column[String]("last_name", O.Length(255, varying = true))
  /** Database column email SqlType(VARCHAR), Length(255,true), Default(None) */
  val email: Rep[String] = column[String]("email", O.Length(255, varying = true))
  /** Database column created_at SqlType(DATETIME), Default(None) */
  val created_at = column[DateTime]("created_at")
  /** Database column updated_at SqlType(DATETIME), Default(None) */
  val updated_at = column[DateTime]("updated_at")
  /** Database column encrypted_password SqlType(VARCHAR), Length(255,true), Default() */
  val encrypted_password: Rep[String] = column[String]("encrypted_password", O.Length(255, varying = true), O.Default(""))
  /** Database column role SqlType(VARCHAR), Length(255,true), Default(None) */
  val role: Rep[String] = column[String]("role", O.Length(255, varying = true))
  /** Database column team_id SqlType(INT), Default(None) */
  val account_balance = column[Double]("account_balance")
  /** Database column contest_id SqlType(INT), Default(None) */
  val blocked_amount = column[Double]("blocked_amount")

  val tokens: Rep[Option[String]] = column[Option[String]]("tokens", O.SqlType("TEXT"))
  val index1 = index("index_users_email", email, unique = true)
  
}

object UserJsonProtocol extends CustomJson {
  implicit val orderFormat = jsonFormat11(User)
}

object UserRoles {
  val ADMIN = "Admin"
  val TRADER = "Trader"
  val OPS = "Ops"
  val SIM = "Simulation"
}