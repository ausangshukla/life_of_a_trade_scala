package com.lot.utils

import java.io.File

import com.typesafe.config.{ Config, ConfigFactory }

trait Configuration {
  def config: Config
}

trait ConfigurationModuleImpl extends Configuration {

  /*
   * Find out which env we are in
   */
  val env = scala.sys.props.get("ENV") match {
    case Some(oenv) => oenv
    case None       => "dev"
  }

  /**
   * Load env specific config file
   */
  private val internalConfig: Config = {
    println(s"loading application.$env.conf")
    val configDefaults = ConfigFactory.load(this.getClass().getClassLoader(), s"application.$env.conf")

    scala.sys.props.get("application.config") match {
      case Some(filename) => ConfigFactory.parseFile(new File(filename)).withFallback(configDefaults)
      case None           => configDefaults
    }
  }
  
  /**
   * This is the main config object available when this trait is mixed in
   */
  def config = internalConfig
}