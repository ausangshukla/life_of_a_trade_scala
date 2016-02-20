import scala.language.existentials
import com.lot.marketEvent.service.MarketEventRestService
import com.lot.test.BaseTest
import spray.testkit.ScalatestRouteTest
import com.lot.marketEvent.dao.MarketEventDao
import com.lot.generators.MarketEventFactory
import sun.security.provider.certpath.OCSPResponse.ResponseStatus
import com.lot.marketEvent.model.MarketEvent
import spray.httpx.SprayJsonSupport._
import scala.collection.mutable.ListBuffer
import com.lot.test.FailingTest

class MarketEventServiceTest extends BaseTest
    with ScalatestRouteTest
    with MarketEventRestService {

  val actorFactoryRef = system
  import com.lot.marketEvent.model.MarketEventJsonProtocol._

  "A MarketEventRestService" should s"return the MarketEvent on $REST_ENDPOINT/id" in {

    /*
     * Create an entity
     */
    val marketEvent = MarketEventFactory.generate(name = "Name1", summary = "This is a test event")
    /*
     * Save it
     */
    val fSaved = MarketEventDao.save(marketEvent)
    val saved = wait(fSaved)
    val route = s"/$REST_ENDPOINT/${saved.id.get}"
    println(s"route = $route")
    Get(route) ~> endpoints ~> check {

      println(responseAs[String])

      val rest_response = responseAs[MarketEvent]

      println(rest_response)
      println(saved)

      assert(rest_response == saved)
    }
  }

  "A MarketEventRestService" should s"return all the MarketEvent on $REST_ENDPOINT" in {

    /*
     * Create an entity
     */
    val events = new ListBuffer[MarketEvent]
    for (i <- 1 to 10) {
      events += wait(MarketEventDao.save(MarketEventFactory.generate(name = s"Name$i", summary = s"This is a test $i event")))
    }

    Get(s"/$REST_ENDPOINT") ~> endpoints ~> check {

      val rest_response = responseAs[Seq[MarketEvent]]

      val mixed = events zip rest_response
      for {
        (event, restEvent) <- mixed
      } yield (assert(event == restEvent))

    }
  }

  "A MarketEventRestService" should s"create a MarketEvent on $REST_ENDPOINT" in {

    /*
     * Create an entity
     */
    val marketEvent = MarketEventFactory.generate(name = "Name1", summary = "This is a test event")

    Post(s"/$REST_ENDPOINT", marketEvent) ~> endpoints ~> check {

      println(responseAs[String])

      val rest_response = responseAs[MarketEvent]

      println(rest_response)
      val saved = wait(MarketEventDao.get(1)).get

      assert(rest_response == saved)
    }
  }

  "A MarketEventRestService" should s"update the MarketEvent on $REST_ENDPOINT"  in {

    /*
     * Create an entity
     */
    val marketEvent = MarketEventFactory.generate(name = "Name1", summary = "This is a test event")
    /*
     * Save it
     */
    val fSaved = MarketEventDao.save(marketEvent)
    val saved = wait(fSaved)

    val modified = saved.copy(name = "Updated", summary = "Updated this event")

    Put(s"/$REST_ENDPOINT", modified) ~> endpoints ~> check {

      Get(s"/$REST_ENDPOINT/${saved.id.get}") ~> endpoints ~> check {
        val dbEntity = wait(MarketEventDao.get(saved.id.get)).get
        val rest_response = responseAs[MarketEvent]
        println(rest_response)
        println(modified)
        assert(rest_response == modified.copy(created_at=dbEntity.created_at, updated_at=dbEntity.updated_at))
      }

    }
  }
  
   "A MarketEventRestService" should s"delete a MarketEvent on $REST_ENDPOINT"  in {

    /*
     * Create an entity
     */
    val marketEvent = MarketEventFactory.generate(name = "Name1", summary = "This is a test event")

    val fSaved = MarketEventDao.save(marketEvent)
    val saved = wait(fSaved)
    
    Delete(s"/$REST_ENDPOINT/${saved.id.get}") ~> endpoints ~> check {

      println(responseAs[String])
      
      val saved = wait(MarketEventDao.get(1))
      status.intValue should be (200)
      assert(None == saved)
    }
  }

}