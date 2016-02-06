package com.lot.marketEvent.service

import com.lot.BaseService
import com.lot.marketEvent.dao.TriggeredEventDao
import com.lot.marketEvent.model.TriggeredEvent
import com.lot.marketEvent.model.TriggeredEventJsonProtocol
import scala.concurrent.ExecutionContext.Implicits.global
import akka.routing.FromConfig
import akka.actor.Props
import akka.actor.ActorSystem
import com.lot.utils.ConfigurationModuleImpl
import com.lot.marketEvent.dao.TriggeredEventDao
import com.lot.marketEvent.model.MarketEvent

/**
 * The service that provides the REST interface for TriggeredEvent
 */
object TriggeredEventService extends BaseService with ConfigurationModuleImpl {

  val system = ActorSystem("lot-om", config)
  /*
   * The actor that manages the simulation of  trades based on the triggeredEvents
   */
  val simulator = system.actorOf(FromConfig.props(Props[Simulator]), "simulator")

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
   */
  val simulate = postJson {
    path("trigger_events/simulate") {
      entity(as[TriggeredEvent]) { triggeredEvent =>
        {
          dao.get(triggeredEvent.id.get).map { otm =>
            otm match {
              case Some((triggeredEvent: TriggeredEvent, marketEvent: MarketEvent)) =>
                simulator ! marketEvent
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
          complete(dao.save(triggeredEvent))
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