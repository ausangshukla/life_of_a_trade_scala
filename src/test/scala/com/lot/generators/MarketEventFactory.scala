package com.lot.generators

import org.joda.time.DateTime
import org.scalacheck.Gen.oneOf
import com.lot.security.model.SecurityType
import com.lot.marketEvent.model.MarketEventType
import com.lot.marketEvent.model.MarketEvent

object MarketEventFactory {

  def generate(id: Option[Long] = None,
               name: String,
               event_type: String = oneOf(MarketEventType.TYPE_MARKET, MarketEventType.TYPE_NON_MARKET).sample.get,
               summary: String,
               description: Option[String] = None,
               direction: String = oneOf(MarketEventType.DIRECTION_DOWN, MarketEventType.DIRECTION_UP).sample.get,
               intensity: String = oneOf(MarketEventType.INTENSITY_HIGH, MarketEventType.INTENSITY_LOW, MarketEventType.INTENSITY_MED).sample.get,
               asset_class: String = oneOf(SecurityType.ASSET_CLASSES).sample.get,
               region: Option[String] = Some(oneOf(SecurityType.REGIONS).sample.get),
               sector: Option[String] = Some(oneOf(SecurityType.SECTORS).sample.get),
               ticker: Option[String] = None,
               external_url: Option[String] = None,
               created_at: Option[DateTime] = None,
               updated_at: Option[DateTime] = None) = {

    MarketEvent(id, name, event_type, summary, description, direction, intensity, asset_class, region, sector, ticker, external_url, created_at, updated_at)
  }

}