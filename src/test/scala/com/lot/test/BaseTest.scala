package com.lot.test

import org.scalatest._
import com.lot.order.dao.OrderDao
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.collection.immutable.Seq
import org.scalatest.Tag
import com.lot.trade.dao.TradeDao
import com.lot.marketEvent.dao.MarketEventDao
import com.lot.marketEvent.dao.TriggeredEventDao
import com.lot.position.dao.PositionDao
import com.lot.blockAmount.dao.BlockAmountDao

object FailingTest extends Tag("FailingTest")
object NewTest extends Tag("NewTest")

abstract class BaseTest extends FlatSpec with Matchers with OptionValues
    with Inside with Inspectors with BeforeAndAfterEach {

  override def beforeEach() {
    println("************ Truncating Tables ************")
    Await.result(OrderDao.truncate, Duration.Inf)
    Await.result(MarketEventDao.truncate, Duration.Inf)
    Await.result(TradeDao.truncate, Duration.Inf)
    Await.result(TriggeredEventDao.truncate, Duration.Inf)
    Await.result(PositionDao.truncate, Duration.Inf)
    Await.result(BlockAmountDao.truncate, Duration.Inf)
  }

  def wait[T](t: Future[T]): T = {
    Await.result(t, Duration.Inf)
  }

  def wait[T](t: Seq[Future[T]]) = {
    val seq = Future.sequence(t)
    val f = Await.ready(seq, Duration.Inf)
  }
 
}