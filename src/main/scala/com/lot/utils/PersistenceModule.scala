package com.lot.utils

import slick.backend.DatabaseConfig
import slick.driver.{ JdbcProfile }
import com.lot.utils.Configuration

trait Profile {
  val profile: JdbcProfile
}

trait DbModule extends Profile {
  val db: JdbcProfile#Backend#Database
}

trait PersistenceModule {
}

trait PersistenceModuleImpl extends PersistenceModule with DbModule {
  this: Configuration =>

  // use an alternative database configuration ex:
  // private val dbConfig : DatabaseConfig[JdbcProfile]  = DatabaseConfig.forConfig("pgdb")
  // private val dbConfig : DatabaseConfig[JdbcProfile]  = DatabaseConfig.forConfig("h2db")
  private val dbConfig: DatabaseConfig[JdbcProfile] = DatabaseConfig.forConfig("loat_dev")

  override implicit val profile: JdbcProfile = dbConfig.driver
  override implicit val db: JdbcProfile#Backend#Database = dbConfig.db

  val self = this

}
