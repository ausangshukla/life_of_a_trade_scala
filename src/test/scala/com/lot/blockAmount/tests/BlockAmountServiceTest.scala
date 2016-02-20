package com.lot.blockAmount.tests


import scala.language.existentials
import com.lot.blockAmount.service.BlockAmountRestService
import com.lot.test.BaseTest
import spray.testkit.ScalatestRouteTest
import com.lot.blockAmount.dao.BlockAmountDao
import com.lot.generators.BlockAmountFactory
import sun.security.provider.certpath.OCSPResponse.ResponseStatus
import com.lot.blockAmount.model.BlockAmount
import spray.httpx.SprayJsonSupport._
import scala.collection.mutable.ListBuffer
import com.lot.test.FailingTest

class BlockAmountServiceTest extends BaseTest
    with ScalatestRouteTest
    with BlockAmountRestService {

  val actorFactoryRef = system
  import com.lot.blockAmount.model.BlockAmountJsonProtocol._

  "A BlockAmountRestService" should s"return the BlockAmount on $REST_ENDPOINT/id" in {

    /*
     * Create an entity
     */
    val blockAmount = BlockAmountFactory.generate()
    /*
     * Save it
     */
    val fSaved = BlockAmountDao.save(blockAmount)
    val saved = wait(fSaved)
    val route = s"/$REST_ENDPOINT/${saved.id.get}"
    println(s"route = $route")
    Get(route) ~> endpoints ~> check {

      println(responseAs[String])

      val rest_response = responseAs[BlockAmount]

      println(rest_response)
      println(saved)

      assert(rest_response == saved)
    }
  }

  "A BlockAmountRestService" should s"return all the BlockAmount on $REST_ENDPOINT" in {

    /*
     * Create an entity
     */
    val events = new ListBuffer[BlockAmount]
    for (i <- 1 to 10) {
      events += wait(BlockAmountDao.save(BlockAmountFactory.generate()))
    }

    Get(s"/$REST_ENDPOINT") ~> endpoints ~> check {

      val rest_response = responseAs[Seq[BlockAmount]]

      val mixed = events zip rest_response
      for {
        (event, restEvent) <- mixed
      } yield (assert(event == restEvent))

    }
  }

  "A BlockAmountRestService" should s"create a BlockAmount on $REST_ENDPOINT" in {

    /*
     * Create an entity
     */
    val blockAmount = BlockAmountFactory.generate()

    Post(s"/$REST_ENDPOINT", blockAmount) ~> endpoints ~> check {

      println(responseAs[String])

      val rest_response = responseAs[BlockAmount]

      println(rest_response)
      val saved = wait(BlockAmountDao.get(1)).get

      assert(rest_response == saved)
    }
  }

  "A BlockAmountRestService" should s"update the BlockAmount on $REST_ENDPOINT" taggedAs(FailingTest) in {

    /*
     * Create an entity
     */
    val blockAmount = BlockAmountFactory.generate()
    /*
     * Save it
     */
    val fSaved = BlockAmountDao.save(blockAmount)
    val saved = wait(fSaved)

    val modified = saved.copy(actual_amount_charged = 1000, status="Changed")

    Put(s"/$REST_ENDPOINT", modified) ~> endpoints ~> check {

      Thread.sleep(1000)
      Get(s"/$REST_ENDPOINT/${saved.id.get}") ~> endpoints ~> check {
        val dbEntity = wait(BlockAmountDao.get(saved.id.get)).get
        val rest_response = responseAs[BlockAmount]
        println(rest_response)
        println(modified)
        assert(rest_response == modified.copy(created_at=dbEntity.created_at, updated_at=dbEntity.updated_at))
      }

    }
  }
  
   "A BlockAmountRestService" should s"delete a BlockAmount on $REST_ENDPOINT" in {

    /*
     * Create an entity
     */
    val blockAmount = BlockAmountFactory.generate()

    val fSaved = BlockAmountDao.save(blockAmount)
    val saved = wait(fSaved)
    
    Delete(s"/$REST_ENDPOINT/${saved.id.get}") ~> endpoints ~> check {

      println(responseAs[String])
      
      val saved = wait(BlockAmountDao.get(1))
      status.intValue should be (200)
      assert(None == saved)
    }
  }

}


