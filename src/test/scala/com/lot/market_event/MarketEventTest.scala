package com.lot.market_event

import com.lot.test.BaseTest
import com.lot.generators.MarketEventFactory
import com.lot.marketEvent.model.MarketEventTable
import com.lot.marketEvent.model.MarketEventType
import com.lot.marketEvent.dao.MarketEventDao
import com.lot.marketEvent.dao.TriggeredEventDao
import com.lot.marketEvent.model.TriggeredEvent
import scala.concurrent.duration.Duration
import scala.concurrent.Await
import com.lot.marketEvent.service.Simulator
import com.lot.security.dao.SecurityDao
import com.lot.test.NewTest
import com.lot.exchange.Message.StopMatchers
import com.lot.exchange.Exchange
import com.lot.user.service.UserManager
import com.lot.user.service.UserManagerMessages.StopUserActors

class MarketEventTest extends BaseTest {

  "An TriggeredMarketEvent with direction UP " should " when triggered move the market up" in {

    val sec = Await.result(SecurityDao.findByTicker("EBB"), Duration.Inf)

    /*
     * Push some MarketEvent into the DB
     */
    val m1 = MarketEventFactory.generate(name = "Event1",
      event_type = MarketEventType.TYPE_MARKET,
      direction = MarketEventType.DIRECTION_UP,
      summary = "Trigger Test",
      ticker = Some("EBB"))

    val saved = Await.result(MarketEventDao.save(m1), Duration.Inf)

    val t1 = TriggeredEventDao.save(TriggeredEvent(None, saved.id.get, false, None, None))

    Simulator() ! saved

    Thread.sleep(40000)

    val secNew = Await.result(SecurityDao.findByTicker("EBB"), Duration.Inf)

    /*
     * Ensure price has actually gone up
     */
    assert(sec.get.price < secNew.get.price)

    /*
     * Cleanup - stop all matchers so the next test case passes
     */
    for {
      (name, exchange) <- Exchange.exchanges
    } yield (exchange ! StopMatchers)
    
    UserManager.userManager ! StopUserActors
  }

  "An TriggeredMarketEvent with direction DOWN " should " when triggered move the market down" in {

    val sec = Await.result(SecurityDao.findByTicker("EBB"), Duration.Inf)

    /*
     * Push some MarketEvent into the DB
     */
    val m1 = MarketEventFactory.generate(name = "Event1",
      event_type = MarketEventType.TYPE_MARKET,
      direction = MarketEventType.DIRECTION_DOWN,
      summary = "Trigger Test",
      ticker = Some("EBB"))

    val saved = Await.result(MarketEventDao.save(m1), Duration.Inf)

    val t1 = TriggeredEventDao.save(TriggeredEvent(None, saved.id.get, false, None, None))

    Simulator() ! saved

    Thread.sleep(40000)

    val secNew = Await.result(SecurityDao.findByTicker("EBB"), Duration.Inf)

    /*
     * Ensure price has actually gone up
     */
    assert(sec.get.price > secNew.get.price)

    /*
     * Cleanup - stop all matchers so the next test case passes
     */
    for {
      (name, exchange) <- Exchange.exchanges
    } yield (exchange ! StopMatchers)

    UserManager.userManager ! StopUserActors
  }

}