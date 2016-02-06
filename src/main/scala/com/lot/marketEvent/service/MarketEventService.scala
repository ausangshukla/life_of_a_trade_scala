package com.lot.marketEvent.service

import com.lot.BaseService
import com.lot.marketEvent.dao.MarketEventDao
import com.lot.marketEvent.model.MarketEvent
import com.lot.marketEvent.model.MarketEventJsonProtocol
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * The service that provides the REST interface for MarketEvent 
 */
object MarketEventService extends BaseService {
  
  /**
   * For JSON serialization/deserialization
   */
  import com.lot.Json4sProtocol._

  /**
   * The DAO for DB access to MarketEvent
   */
  val dao = MarketEventDao

  /**
   * Returns the list of marketEvents
   */
  val list = getJson {
    path("market_events") {
      complete(dao.list)
    }
  }

  /**
   * Returns a specific marketEvent identified by the id
   */
  val details = getJson {
    path("market_events" / IntNumber) { id =>
      {
        complete(dao.get(id))
      }
    }
  }
  

  /**
   * Creates a new marketEvent
   */
  val create = postJson {
    path("market_events") {
      entity(as[MarketEvent]) { marketEvent =>
        {
          complete(dao.save(marketEvent))
        }
      }
    }
  }
  
  /**
   * Updates an existing marketEvent identified by the id
   */
  val update = putJson {
    path("market_events" / IntNumber) { id =>
      entity(as[MarketEvent]) { marketEvent =>
        {
          complete(dao.update(marketEvent))
        }
      }
    }
  }
  
  /**
   * Deletes the marketEvent identified by the id
   */
  val destroy = deleteJson {
    path("market_events" / IntNumber) { id =>

      complete(dao.delete(id))

    }
  }

  /**
   * The list of methods which are exposed as the endpoint for this service
   */
  val endpoints =
    list ~ details ~ create ~ update ~ destroy

}