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
case class User(id: Long, first_name: String, last_name: String, email: String,
                created_at: java.sql.Timestamp, updated_at: java.sql.Timestamp,
                encrypted_password: String = "",
                role: String, team_id: Option[Int], contest_id: Option[Int], tokens: Option[String])

/** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
class UserTable(_tableTag: Tag) extends Table[User](_tableTag, "users") {
  def * = (id, first_name, last_name, email, created_at, updated_at, encrypted_password, role, team_id, contest_id, tokens) <> (User.tupled, User.unapply)
  /** Maps whole row to an option. Useful for outer joins. */
  def ? = (Rep.Some(id), first_name, last_name, email, created_at, updated_at, Rep.Some(encrypted_password), role, Rep.Some(team_id), Rep.Some(contest_id), Rep.Some(tokens)).shaped.<>({ r => import r._; _1.map(_ => User.tupled((_1.get, _2, _3, _4, _5, _6, _7.get, _8, _9.get, _10.get, _11.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  /** Database column id SqlType(INT), AutoInc, PrimaryKey */
  val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
  /** Database column first_name SqlType(VARCHAR), Length(255,true), Default(None) */
  val first_name: Rep[String] = column[String]("first_name", O.Length(255, varying = true))
  /** Database column last_name SqlType(VARCHAR), Length(255,true), Default(None) */
  val last_name: Rep[String] = column[String]("last_name", O.Length(255, varying = true))
  /** Database column email SqlType(VARCHAR), Length(255,true), Default(None) */
  val email: Rep[String] = column[String]("email", O.Length(255, varying = true))
  /** Database column created_at SqlType(DATETIME), Default(None) */
  val created_at: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
  /** Database column updated_at SqlType(DATETIME), Default(None) */
  val updated_at: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
  /** Database column encrypted_password SqlType(VARCHAR), Length(255,true), Default() */
  val encrypted_password: Rep[String] = column[String]("encrypted_password", O.Length(255, varying = true), O.Default(""))
  /** Database column role SqlType(VARCHAR), Length(255,true), Default(None) */
  val role: Rep[String] = column[String]("role", O.Length(255, varying = true))
  /** Database column team_id SqlType(INT), Default(None) */
  val team_id: Rep[Option[Int]] = column[Option[Int]]("team_id")
  /** Database column contest_id SqlType(INT), Default(None) */
  val contest_id: Rep[Option[Int]] = column[Option[Int]]("contest_id")

  val tokens: Rep[Option[String]] = column[Option[String]]("tokens", O.SqlType("TEXT"))
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