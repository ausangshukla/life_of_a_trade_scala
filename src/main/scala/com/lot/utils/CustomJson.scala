package com.lot.utils

import org.joda.time.format.ISODateTimeFormat
import spray.json.RootJsonFormat
import org.joda.time.DateTime
import spray.json._
import org.joda.time.format.DateTimeFormatter
import spray.httpx.SprayJsonSupport

trait CustomJson extends DefaultJsonProtocol {

  implicit object DateJsonFormat extends RootJsonFormat[DateTime] {

    private val parserISO: DateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis();

    override def write(obj: DateTime) = JsString(parserISO.print(obj))
    
    override def read(json: JsValue): DateTime = json match {
      case JsString(s) => parserISO.parseDateTime(s)
      case _           => throw new DeserializationException("Invalid DateTime")
    }
  }
 
}
