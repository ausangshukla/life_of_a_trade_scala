package com.lot

import org.scalatest._
import com.lot.order.dao.OrderDao
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.collection.immutable.Seq

abstract class BaseTest extends FlatSpec with Matchers with OptionValues with Inside with Inspectors with BeforeAndAfterEach {

  override def beforeEach() {
    Await.result(OrderDao.truncate, 5 seconds)
  }

  def wait[T](t:Future[T]) : T = {
    Await.result(t, Duration.Inf)
  }
  
  def wait[T](t:Seq[Future[T]]) = {
    val seq = Future.sequence(t)
    Await.ready(seq, Duration.Inf)
  }
}