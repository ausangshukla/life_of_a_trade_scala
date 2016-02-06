package com.lot.marketEvent.service

import com.lot.BaseService
import com.lot.marketEvent.dao.TriggeredEventDao
import com.lot.marketEvent.model.TriggeredEvent
import com.lot.marketEvent.model.TriggeredEventJsonProtocol
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * The service that provides the REST interface for TriggeredEvent 
 */
object TriggeredEventService extends BaseService {
  
  /**
   * For JSON serialization/deserialization
   */
  import com.lot.Json4sProtocol._

  /**
   * The DAO for DB access to TriggeredEvent
   */
  val dao = TriggeredEventDao

  /**
   * Returns the list of triggeredEvents
   */
  val list = getJson {
    path("market_events") {
      complete(dao.list)
    }
  }

  /**
   * Returns a specific triggeredEvent identified by the id
   */
  val details = getJson {
    path("market_events" / IntNumber) { id =>
      {
        complete(dao.get(id))
      }
    }
  }
  
  
  val trigger = getJson {
    path("market_events/trigger" / IntNumber) { id =>
      {
        val event = dao.get(id)        
        complete(event)
      }
    }
  }

  /**
   * Creates a new triggeredEvent
   */
  val create = postJson {
    path("market_events") {
      entity(as[TriggeredEvent]) { triggeredEvent =>
        {
          complete(dao.save(triggeredEvent))
        }
      }
    }
  }
  
  /**
   * Updates an existing triggeredEvent identified by the id
   */
  val update = putJson {
    path("market_events" / IntNumber) { id =>
      entity(as[TriggeredEvent]) { triggeredEvent =>
        {
          complete(dao.update(triggeredEvent))
        }
      }
    }
  }
  
  /**
   * Deletes the triggeredEvent identified by the id
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