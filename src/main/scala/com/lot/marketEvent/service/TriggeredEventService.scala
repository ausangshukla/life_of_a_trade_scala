package com.lot.marketEvent.service

import scala.concurrent.ExecutionContext.Implicits.global
import com.lot.BaseService
import com.lot.Json4sProtocol
import com.lot.marketEvent.dao.TriggeredEventDao
import com.lot.marketEvent.model.MarketEvent
import com.lot.marketEvent.model.TriggeredEvent
import com.lot.utils.ConfigurationModuleImpl
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala
import spray.httpx.marshalling.ToResponseMarshallable.isMarshallable
import spray.routing.Directive.pimpApply
import com.lot.utils.WebSocket

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
    path("trigger_events") {
      complete(dao.list)
    }
  }

  /**
   * Returns a specific triggeredEvent identified by the id
   */
  val details = getJson {
    path("trigger_events" / IntNumber) { id =>
      {
        complete(dao.get(id))
      }
    }
  }

  /**
   * Triggers the Simulation of the event
   * TODO - ensure only admins can do this action
   */
  val simulate = postJson {
    path("trigger_events/simulate") {
      entity(as[TriggeredEvent]) { triggeredEvent =>
        {
          dao.get(triggeredEvent.id.get).map { otm =>
            otm match {
              case Some((triggeredEvent: TriggeredEvent, marketEvent: MarketEvent)) =>
                Simulator() ! marketEvent
              case _ => logger.error(s"Could not trigger event $triggeredEvent")
            }
          }
          complete(dao.update(triggeredEvent.copy(sent_to_sim = true)))
        }
      }
    }
  }

  /**
   * Creates a new triggeredEvent
   */
  val create = postJson {
    path("trigger_events") {
      entity(as[TriggeredEvent]) { triggeredEvent =>
        {
          complete({
            /*
             * Save it
             */
            val teF = dao.save(triggeredEvent)
            teF.map { te =>
              /*
         		   * Push it to the web app
           	   */
              WebSocket.publishTriggerEvent(te)
              te
            }
          })

        }
      }
    }
  }

  /**
   * Updates an existing triggeredEvent identified by the id
   */
  val update = putJson {
    path("trigger_events" / IntNumber) { id =>
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
    path("trigger_events" / IntNumber) { id =>

      complete(dao.delete(id))

    }
  }

  /**
   * The list of methods which are exposed as the endpoint for this service
   */
  val endpoints =
    list ~ details ~ create ~ update ~ destroy

}