package com.lot.order.tests


import scala.language.existentials
import com.lot.order.service.OrderRestService
import com.lot.test.BaseTest
import spray.testkit.ScalatestRouteTest
import com.lot.order.dao.OrderDao
import com.lot.generators.OrderFactory
import sun.security.provider.certpath.OCSPResponse.ResponseStatus
import com.lot.order.model.Order
import spray.httpx.SprayJsonSupport._
import scala.collection.mutable.ListBuffer
import com.lot.test.FailingTest
import com.lot.security.dao.SecurityDao
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class OrderServiceTest extends BaseTest
    with ScalatestRouteTest
    with OrderRestService {

  val actorFactoryRef = system
  val sec = Await.result(SecurityDao.first(), Duration.Inf)
  val security_id = sec.get.id.get
  
  
  import com.lot.order.model.OrderJsonProtocol._

  "A OrderRestService" should s"return the Order on $REST_ENDPOINT/id" in {

    /*
     * Create an entity
     */
    val order = OrderFactory.generate(security_id = security_id)
    /*
     * Save it
     */
    val fSaved = OrderDao.save(order)
    val saved = wait(fSaved)
    val route = s"/$REST_ENDPOINT/${saved.id.get}"
    println(s"route = $route")
    Get(route) ~> endpoints ~> check {

      println(responseAs[String])

      val rest_response = responseAs[Order]

      println(rest_response)
      println(saved)

      assert(rest_response == saved)
    }
  }

  "A OrderRestService" should s"return all the Order on $REST_ENDPOINT" in {

    /*
     * Create an entity
     */
    val events = new ListBuffer[Order]
    for (i <- 1 to 10) {
      events += wait(OrderDao.save(OrderFactory.generate(security_id = security_id)))
    }

    Get(s"/$REST_ENDPOINT") ~> endpoints ~> check {

      val rest_response = responseAs[Seq[Order]]

      val mixed = events zip rest_response
      for {
        (event, restEvent) <- mixed
      } yield (assert(event == restEvent))

    }
  }

  "A OrderRestService" should s"create a Order on $REST_ENDPOINT" in {

    /*
     * Create an entity
     */
    val order = OrderFactory.generate(security_id = security_id)

    Post(s"/$REST_ENDPOINT", order) ~> endpoints ~> check {

      println(responseAs[String])

      val rest_response = responseAs[Order]

      println(rest_response)
      val saved = wait(OrderDao.get(1)).get

      assert(rest_response == saved)
    }
  }

  "A OrderRestService" should s"update the Order on $REST_ENDPOINT" taggedAs(FailingTest) in {

    /*
     * Create an entity
     */
    val order = OrderFactory.generate(security_id = security_id)
    /*
     * Save it
     */
    val fSaved = OrderDao.save(order)
    val saved = wait(fSaved)

    val modified = OrderFactory.generate(security_id = security_id).copy(id = saved.id)

    Put(s"/$REST_ENDPOINT", modified) ~> endpoints ~> check {

      Thread.sleep(1000)
      Get(s"/$REST_ENDPOINT/${saved.id.get}") ~> endpoints ~> check {
        val dbEntity = wait(OrderDao.get(saved.id.get)).get
        val rest_response = responseAs[Order]
        println(rest_response)
        println(modified)
        assert(rest_response == modified.copy(created_at=dbEntity.created_at, updated_at=dbEntity.updated_at))
      }

    }
  }
  
   "A OrderRestService" should s"delete a Order on $REST_ENDPOINT" in {

    /*
     * Create an entity
     */
    val order = OrderFactory.generate(security_id = security_id)

    val fSaved = OrderDao.save(order)
    val saved = wait(fSaved)
    
    Delete(s"/$REST_ENDPOINT/${saved.id.get}") ~> endpoints ~> check {

      println(responseAs[String])
      
      val saved = wait(OrderDao.get(1))
      status.intValue should be (200)
      assert(None == saved)
    }
  }

}


