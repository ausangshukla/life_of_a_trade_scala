package com.lot.utils

import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

object DB extends ConfigurationModuleImpl {
  /**
   * the env dev,test,prod we are operating in
   */
  val env = config.getString("env")
  
  /**
   * load the db config for this env
   */
	private val dbConfig : DatabaseConfig[JdbcProfile]  = DatabaseConfig.forConfig(s"lot_$env")
	val profile: JdbcProfile = dbConfig.driver
	
	/**
	 * This is the variable used by all consumers of the DB
	 */
	val db: JdbcProfile#Backend#Database = dbConfig.db

}