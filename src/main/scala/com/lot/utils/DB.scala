package com.lot.utils

import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import com.lot.order.dao.OrderDao
import com.lot.trade.dao.TradeDao
import com.lot.security.dao.SecurityDao
import com.lot.user.dao.UserDao

object DB extends ConfigurationModuleImpl {

  private val dbConfig: DatabaseConfig[JdbcProfile] = DatabaseConfig.forConfig(s"lot_$env")
  val profile: JdbcProfile = dbConfig.driver

  /**
   * This is the variable used by all consumers of the DB
   */
  val db: JdbcProfile#Backend#Database = dbConfig.db

  def createTables = {
    OrderDao.createTables()
    TradeDao.createTables()
  }
}