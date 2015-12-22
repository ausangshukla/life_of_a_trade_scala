package com.lot.user.model
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.H2JodaSupport._
import spray.json.DeserializationException
import java.sql.Timestamp
import spray.json._
import DefaultJsonProtocol._

/**
 * Entity class storing rows of table Users
 *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
 *  @param firstName Database column first_name SqlType(VARCHAR), Length(255,true), Default(None)
 *  @param lastName Database column last_name SqlType(VARCHAR), Length(255,true), Default(None)
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
case class User(id: Long, firstName: String, lastName: String, email: String,
                createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp,
                encryptedPassword: String = "",
                role: String, teamId: Option[Int], contestId: Option[Int], tokens: Option[String])

/** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
class UserTable(_tableTag: Tag) extends Table[User](_tableTag, "users") {
  def * = (id, firstName, lastName, email, createdAt, updatedAt, encryptedPassword, role, teamId, contestId, tokens) <> (User.tupled, User.unapply)
  /** Maps whole row to an option. Useful for outer joins. */
  def ? = (Rep.Some(id), firstName, lastName, email, createdAt, updatedAt, Rep.Some(encryptedPassword), role, Rep.Some(teamId), Rep.Some(contestId), Rep.Some(tokens)).shaped.<>({ r => import r._; _1.map(_ => User.tupled((_1.get, _2, _3, _4, _5, _6, _7.get, _8, _9.get, _10.get, _11.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  /** Database column id SqlType(INT), AutoInc, PrimaryKey */
  val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
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
  /** Database column role SqlType(VARCHAR), Length(255,true), Default(None) */
  val role: Rep[String] = column[String]("role", O.Length(255, varying = true))
  /** Database column team_id SqlType(INT), Default(None) */
  val teamId: Rep[Option[Int]] = column[Option[Int]]("team_id")
  /** Database column contest_id SqlType(INT), Default(None) */
  val contestId: Rep[Option[Int]] = column[Option[Int]]("contest_id")

  val tokens: Rep[Option[String]] = column[Option[String]]("tokens", O.DBType("TEXT"))
  val index1 = index("index_users_email", email, unique = true)
  
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
      User.tupled((<<[Long], <<[String], <<[String], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<[String], <<[String], <<?[Int], <<?[Int], <<?[String]))
  }

}

object UserJsonProtocol extends DefaultJsonProtocol {

  implicit object TimestampJsonFormat extends JsonFormat[Timestamp] {
    def write(x: Timestamp) = JsNumber(x.getTime)
    def read(value: JsValue) = value match {
      case JsNumber(x) => new Timestamp(x.longValue)
      case x           => deserializationError("Expected Timestamp as JsNumber, but got " + x)
    }
  }
  implicit val orderFormat = jsonFormat11(User)
}