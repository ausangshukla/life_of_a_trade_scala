package com.lot.user.model
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


/**
 * Entity class storing rows of table Users
 *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
 *  @param firstName Database column first_name SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param lastName Database column last_name SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param email Database column email SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param createdAt Database column created_at SqlType(DATETIME), Default(None)
 *  @param updatedAt Database column updated_at SqlType(DATETIME), Default(None)
 *  @param encryptedPassword Database column encrypted_password SqlType(VARCHAR), Length(255,true), Default()
 *  @param resetPasswordToken Database column reset_password_token SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param resetPasswordSentAt Database column reset_password_sent_at SqlType(DATETIME), Default(None)
 *  @param rememberCreatedAt Database column remember_created_at SqlType(DATETIME), Default(None)
 *  @param signInCount Database column sign_in_count SqlType(INT), Default(0)
 *  @param currentSignInAt Database column current_sign_in_at SqlType(DATETIME), Default(None)
 *  @param lastSignInAt Database column last_sign_in_at SqlType(DATETIME), Default(None)
 *  @param currentSignInIp Database column current_sign_in_ip SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param lastSignInIp Database column last_sign_in_ip SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param confirmationToken Database column confirmation_token SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param confirmedAt Database column confirmed_at SqlType(DATETIME), Default(None)
 *  @param confirmationSentAt Database column confirmation_sent_at SqlType(DATETIME), Default(None)
 *  @param unconfirmedEmail Database column unconfirmed_email SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param role Database column role SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param teamId Database column team_id SqlType(INT), Default(None)
 *  @param contestId Database column contest_id SqlType(INT), Default(None)
 */
case class User(id: Int, firstName: String, lastName: String, email: String,
                    createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, encryptedPassword: String = "",
                    resetPasswordToken: Option[String] = None, resetPasswordSentAt: Option[java.sql.Timestamp] = None,
                    rememberCreatedAt: Option[java.sql.Timestamp] = None, signInCount: Int = 0, currentSignInAt: Option[java.sql.Timestamp] = None,
                    lastSignInAt: Option[java.sql.Timestamp] = None, currentSignInIp: Option[String] = None, lastSignInIp: Option[String] = None,
                    confirmationToken: Option[String] = None, confirmedAt: Option[java.sql.Timestamp] = None,
                    confirmationSentAt: Option[java.sql.Timestamp] = None, unconfirmedEmail: Option[String] = None, role: Option[String] = None, 
                    teamId: Option[Int] = None, contestId: Option[Int] = None)

/** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
class UserTable(_tableTag: Tag) extends Table[User](_tableTag, "users") {
  def * = (id, firstName, lastName, email, createdAt, updatedAt, encryptedPassword, resetPasswordToken, resetPasswordSentAt, rememberCreatedAt, signInCount, currentSignInAt, lastSignInAt, currentSignInIp, lastSignInIp, confirmationToken, confirmedAt, confirmationSentAt, unconfirmedEmail, role, teamId, contestId) <> (User.tupled, User.unapply)
  /** Maps whole row to an option. Useful for outer joins. */
  def ? = (Rep.Some(id), firstName, lastName, email, createdAt, updatedAt, Rep.Some(encryptedPassword), resetPasswordToken, resetPasswordSentAt, rememberCreatedAt, Rep.Some(signInCount), currentSignInAt, lastSignInAt, currentSignInIp, lastSignInIp, confirmationToken, confirmedAt, confirmationSentAt, unconfirmedEmail, role, teamId, contestId).shaped.<>({ r => import r._; _1.map(_ => User.tupled((_1.get, _2, _3, _4, _5, _6, _7.get, _8, _9, _10, _11.get, _12, _13, _14, _15, _16, _17, _18, _19, _20, _21, _22))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  /** Database column id SqlType(INT), AutoInc, PrimaryKey */
  val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
  /** Database column first_name SqlType(VARCHAR), Length(255,true), Default(None) */
  val firstName: Rep[String] = column[String]("first_name", O.Length(255, varying = true))
  /** Database column last_name SqlType(VARCHAR), Length(255,true), Default(None) */
  val lastName: Rep[String] = column[String]("last_name", O.Length(255, varying = true))
  /** Database column email SqlType(VARCHAR), Length(255,true), Default(None) */
  val email: Rep[String] = column[String]("email", O.Length(255, varying = true))
  /** Database column created_at SqlType(DATETIME), Default(None) */
  val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
  /** Database column updated_at SqlType(DATETIME), Default(None) */
  val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
  /** Database column encrypted_password SqlType(VARCHAR), Length(255,true), Default() */
  val encryptedPassword: Rep[String] = column[String]("encrypted_password", O.Length(255, varying = true), O.Default(""))
  /** Database column reset_password_token SqlType(VARCHAR), Length(255,true), Default(None) */
  val resetPasswordToken: Rep[Option[String]] = column[Option[String]]("reset_password_token", O.Length(255, varying = true), O.Default(None))
  /** Database column reset_password_sent_at SqlType(DATETIME), Default(None) */
  val resetPasswordSentAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("reset_password_sent_at", O.Default(None))
  /** Database column remember_created_at SqlType(DATETIME), Default(None) */
  val rememberCreatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("remember_created_at", O.Default(None))
  /** Database column sign_in_count SqlType(INT), Default(0) */
  val signInCount: Rep[Int] = column[Int]("sign_in_count", O.Default(0))
  /** Database column current_sign_in_at SqlType(DATETIME), Default(None) */
  val currentSignInAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("current_sign_in_at", O.Default(None))
  /** Database column last_sign_in_at SqlType(DATETIME), Default(None) */
  val lastSignInAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("last_sign_in_at", O.Default(None))
  /** Database column current_sign_in_ip SqlType(VARCHAR), Length(255,true), Default(None) */
  val currentSignInIp: Rep[Option[String]] = column[Option[String]]("current_sign_in_ip", O.Length(255, varying = true), O.Default(None))
  /** Database column last_sign_in_ip SqlType(VARCHAR), Length(255,true), Default(None) */
  val lastSignInIp: Rep[Option[String]] = column[Option[String]]("last_sign_in_ip", O.Length(255, varying = true), O.Default(None))
  /** Database column confirmation_token SqlType(VARCHAR), Length(255,true), Default(None) */
  val confirmationToken: Rep[Option[String]] = column[Option[String]]("confirmation_token", O.Length(255, varying = true), O.Default(None))
  /** Database column confirmed_at SqlType(DATETIME), Default(None) */
  val confirmedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("confirmed_at", O.Default(None))
  /** Database column confirmation_sent_at SqlType(DATETIME), Default(None) */
  val confirmationSentAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("confirmation_sent_at", O.Default(None))
  /** Database column unconfirmed_email SqlType(VARCHAR), Length(255,true), Default(None) */
  val unconfirmedEmail: Rep[Option[String]] = column[Option[String]]("unconfirmed_email", O.Length(255, varying = true), O.Default(None))
  /** Database column role SqlType(VARCHAR), Length(255,true), Default(None) */
  val role: Rep[Option[String]] = column[Option[String]]("role", O.Length(255, varying = true), O.Default(None))
  /** Database column team_id SqlType(INT), Default(None) */
  val teamId: Rep[Option[Int]] = column[Option[Int]]("team_id", O.Default(None))
  /** Database column contest_id SqlType(INT), Default(None) */
  val contestId: Rep[Option[Int]] = column[Option[Int]]("contest_id", O.Default(None))

  /** Uniqueness Index over (resetPasswordToken) (database name index_users_on_reset_password_token) */
  val index1 = index("index_users_on_reset_password_token", resetPasswordToken, unique = true)
}

object UserImplicits {
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{ GetResult => GR }

  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new UserTable(tag))
  /** GetResult implicit for fetching User objects using plain SQL queries */
  implicit def GetResultUser(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[java.sql.Timestamp]], e3: GR[String], e4: GR[Option[Int]]): GR[User] = GR {
    prs =>
      import prs._
      User.tupled((<<[Int], <<[String], <<[String], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<[String], <<?[String], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp], <<[Int], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[String], <<?[String], <<?[String], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[String], <<?[String], <<?[Int], <<?[Int]))
  }

}