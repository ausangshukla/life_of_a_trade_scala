package com.lot.security.model

case class Price(security_id: Long, price: Double)
/*
 * Message
 */
object PriceMessage {
  case class Set(price: Price)
  case class Get(price: Price)
  case class Value(price: Price)
}
