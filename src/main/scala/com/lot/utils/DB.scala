package com.lot.utils

import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

object DB {
  
	private val dbConfig : DatabaseConfig[JdbcProfile]  = DatabaseConfig.forConfig("lot_dev")
	val profile: JdbcProfile = dbConfig.driver
	
	val db: JdbcProfile#Backend#Database = dbConfig.db

}