package com.lot.market_event.test
import spray.json._
import com.lot.marketEvent.model.MarketEventJsonProtocol._
import com.lot.marketEvent.model.MarketEvent
import com.lot.generators.MarketEventFactory
import com.lot.marketEvent.dao.MarketEventDao
import scala.concurrent.Await
import scala.concurrent.duration._
import org.joda.time.DateTime

object MarketEventScratch {
  val now = new DateTime()                        //> now  : org.joda.time.DateTime = 2016-02-13T17:47:22.776+05:30
  val me = MarketEventFactory.generate(name = "Test", summary = "This is a test", created_at = Some(now), updated_at = Some(new DateTime()))
                                                  //> me  : com.lot.marketEvent.model.MarketEvent = MarketEvent(None,Test,Market,T
                                                  //| his is a test,None,Up,Med,Some(Stock),Some(EMEA),Some(Pharma),None,None,Some
                                                  //| (2016-02-13T17:47:22.776+05:30),Some(2016-02-13T17:47:23.060+05:30))
  me.toJson                                       //> ############DateJsonFormat = 2016-02-13 17:47:22.776
                                                  //| ############DateJsonFormat = 2016-02-13 17:47:23.060
                                                  //| res0: spray.json.JsValue = {"name":"Test","asset_class":"Stock","direction":
                                                  //| "Up","sector":"Pharma","intensity":"Med","event_type":"Market","region":"EME
                                                  //| A","created_at":"2016-02-13 17:47:22.776","updated_at":"2016-02-13 17:47:23.
                                                  //| 060","summary":"This is a test"}
}