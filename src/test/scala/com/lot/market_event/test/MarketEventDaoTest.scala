package com.lot.marketEvent.tests

import com.lot.test.BaseTest
import com.lot.generators.OrderFactory
import com.lot.marketEvent.model.MarketEvent
import com.lot.marketEvent.dao.MarketEventDao
import scala.collection.mutable.ListBuffer
import com.lot.generators.MarketEventFactory

class MarketEventDaoTest extends BaseTest {

  "MarketEventDao" should "save MarketEvent correctly" in {

    /*
     * Create an entity
     */
    val marketEvent = MarketEventFactory.generate(name="Name1", summary="This is a test event")
    /*
     * Save it
     */
    val fSaved = MarketEventDao.save(marketEvent)
    val saved = wait(fSaved)
    /*
     * Get it back from the DB
     */
    val dbMarketEvent = wait(MarketEventDao.get(saved.id.get)).get
    /*
     * They should be the same
     */
    assert(saved == dbMarketEvent)

  }

  "MarketEventDao" should "list MarketEvents correctly" in {

    /*
     * Create some entities and save
     */
    val marketEventList = new ListBuffer[MarketEvent]
    for (i <- 1 to 10) {
      val b = MarketEventFactory.generate(name="Name1", summary="This is a test event")
      marketEventList += wait(MarketEventDao.save(b))
    }

    //println(marketEventList)
    
    /*
     * Get it back from the DB
     */
    val dbList = wait(MarketEventDao.list)
    
    // println(dbList)
    
    /*
     * They should be the same
     */
    assert(dbList.length == marketEventList.length)
    val mixed = marketEventList zip dbList
    for {
      (marketEvent, dbMarketEvent) <- mixed
      x = println(s"comparing marketEvent = $marketEvent with dbMarketEvent = $dbMarketEvent")
    } yield (assert(marketEvent == dbMarketEvent))

  }

  "MarketEventDao" should "update MarketEvent correctly" in {

    /*
     * Create an entity
     */
    val marketEvent = MarketEventFactory.generate(name="Name1", summary="This is a test event")
    /*
     * Save it
     */
    val fSaved = MarketEventDao.save(marketEvent)
    val saved = wait(fSaved)

    val modified = MarketEventFactory.generate(name="Name1", summary="This is a test event").copy(id=saved.id, created_at=saved.created_at, updated_at=saved.updated_at)
    wait(MarketEventDao.update(modified))
    /*
     * Get it back from the DB
     */
    val dbMarketEvent = wait(MarketEventDao.get(saved.id.get)).get
    /*
     * They should be the same. We need to copy the updated_at
     */
    assert(modified.copy(updated_at = dbMarketEvent.updated_at) == dbMarketEvent)

  }

  "MarketEventDao" should "updateWithOptimisticLocking MarketEvent correctly" in {

    /*
     * Create an entity
     */
    val marketEvent = MarketEventFactory.generate(name="Name1", summary="This is a test event")
    /*
     * Save it
     */
    val fSaved = MarketEventDao.save(marketEvent)
    val saved = wait(fSaved)

    val modified1 = MarketEventFactory.generate(name="Name1", summary="This is a test event").copy(id=saved.id, created_at=saved.created_at, updated_at=saved.updated_at)    
    val modified2 = MarketEventFactory.generate(name="Name1", summary="This is a test event").copy(id=saved.id, created_at=saved.created_at, updated_at=saved.updated_at)
    val rowCount1 = wait(MarketEventDao.updateWithOptimisticLocking(modified1))
    val rowCount2 = wait(MarketEventDao.updateWithOptimisticLocking(modified1))
    
    assert(rowCount1 == 1)
    assert(rowCount2 == 0)

  }

  "MarketEventDao" should "delete MarketEvent correctly" in {

    /*
     * Create an entity
     */
    val marketEvent = MarketEventFactory.generate(name="Name1", summary="This is a test event")
    /*
     * Save it
     */
    val fSaved = MarketEventDao.save(marketEvent)
    val saved = wait(fSaved)
    /*
     * Delete it
     */
    wait(MarketEventDao.delete(saved.id.get))
    /*
     * Get it back from the DB
     */
    val dbMarketEvent = wait(MarketEventDao.get(saved.id.get))
    /*
     * They should be None
     */
    assert(dbMarketEvent == None)

  }
}