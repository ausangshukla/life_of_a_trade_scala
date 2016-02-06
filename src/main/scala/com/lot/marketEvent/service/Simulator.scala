package com.lot.marketEvent.service

import akka.actor.ActorLogging
import akka.actor.Actor
import com.lot.marketEvent.model.MarketEvent
import com.lot.marketEvent.model.MarketEventType
import com.lot.security.dao.SecurityDao
import com.lot.security.model.Security
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import com.lot.user.dao.UserDao
import com.lot.user.model.UserRoles
import com.lot.user.model.User
import com.lot.order.model.Order
import com.lot.exchange.Exchange
import com.lot.order.model.OrderType

class Simulator extends Actor with ActorLogging {

  val TRADES_PER_SIM_USER = 10

  def receive = {
    case msg @ MarketEvent => {
      log.debug(s"Simulator received $msg")
    }
  }

  def simulate(marketEvent: MarketEvent) = {
    marketEvent.event_type match {
      case MarketEventType.TYPE_MARKET => {
        /*
         * Ok we have a Market Event which we need to simulate
         * 1. Get the list of securities whose price we need to perturb
         * 2. For each security generate limit and market trades such that the price moves in 
         * the direction specified by the marketEvent.direction
         */

        val securities: Future[Seq[Security]] = SecurityDao.list(marketEvent)

        securities.map { list =>
          list.map {
            sec => generateOrders(marketEvent, sec)
          }
        }

      }
      case _ => log.warning(s"Simulator ignoring $marketEvent")
    }
  }

  private def generateOrders(marketEvent: MarketEvent, security: Security) = {
    /*
     * Only users with SIM role should be used to generate orders 
     */
    UserDao.getByRole(UserRoles.SIM).map { users =>
      val (limitUsers, marketUsers) = users.splitAt(users.length / 2)
      /*
       * Generate some limit orders
       */
      for {
        user <- limitUsers
      } yield (genrateLimitOrders(marketEvent, security, user))
      /*
       * Generate some market orders
       */
      for {
        user <- marketUsers
      } yield (genrateMarketOrders(marketEvent, security, user))

    }
  }

  private def genrateLimitOrders(marketEvent: MarketEvent, security: Security, user: User) = {
    marketEvent match {
      /*
       * Generate SELL limit orders when market is going up
       */
      case MarketEvent(id, name, event_type, summary, description, MarketEventType.DIRECTION_UP, intensity, asset_class, region, sector, ticker, external_url, created_at, updated) => {
        for (i <- (1 to TRADES_PER_SIM_USER)) {
          val price = marketEvent.priceMultiplier(security.price, i)
          val o = Order(None, Exchange.NASDAQ, OrderType.SELL, OrderType.LIMIT,
            user.id.get, security.id.get, 100, 100, price, "", "", "", None, None)
        }
      }

      /*
       * Generate BUY Limit orders when market is going down
       */
      case MarketEvent(id, name, event_type, summary, description, MarketEventType.DIRECTION_DOWN, intensity, asset_class, region, sector, ticker, external_url, created_at, updated) => {
        for (i <- (1 to TRADES_PER_SIM_USER)) {
          val price = marketEvent.priceMultiplier(security.price, i)
          val o = Order(None, Exchange.NASDAQ, OrderType.BUY, OrderType.LIMIT,
            user.id.get, security.id.get, 100, 100, price, "", "", "", None, None)
        }
      }
    }

  }

  private def genrateMarketOrders(marketEvent: MarketEvent, security: Security, user: User) = {

    marketEvent match {
      /*
       * Generate BUY Market orders when market is going up
       */
      case MarketEvent(id, name, event_type, summary, description, MarketEventType.DIRECTION_UP, intensity, asset_class, region, sector, ticker, external_url, created_at, updated) => {
        for (i <- (1 to TRADES_PER_SIM_USER)) {
          val o = Order(None, Exchange.NASDAQ, OrderType.BUY, OrderType.MARKET,
            user.id.get, security.id.get, 100, 100, 0, "", "", "", None, None)
        }
      }

      /*
       * Generate SELL Marker orders when market is going down
       */
      case MarketEvent(id, name, event_type, summary, description, MarketEventType.DIRECTION_DOWN, intensity, asset_class, region, sector, ticker, external_url, created_at, updated) => {
        for (i <- (1 to TRADES_PER_SIM_USER)) {
          val o = Order(None, Exchange.NASDAQ, OrderType.SELL, OrderType.MARKET,
            user.id.get, security.id.get, 100, 100, 0, "", "", "", None, None)
        }
      }
    }

  }

}