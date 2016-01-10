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


