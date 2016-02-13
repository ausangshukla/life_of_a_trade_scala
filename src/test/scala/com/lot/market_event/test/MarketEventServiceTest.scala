import scala.language.existentials
import com.lot.marketEvent.service.MarketEventRestService
import com.lot.test.BaseTest
import spray.testkit.ScalatestRouteTest
import com.lot.marketEvent.dao.MarketEventDao
import com.lot.generators.MarketEventFactory
import sun.security.provider.certpath.OCSPResponse.ResponseStatus
import com.lot.marketEvent.model.MarketEvent
import spray.httpx.SprayJsonSupport._

class MarketEventServiceTest extends BaseTest
    with ScalatestRouteTest 
    with MarketEventRestService  {

  val actorFactoryRef = system
  import com.lot.marketEvent.model.MarketEventJsonProtocol._
  
  
  "A MarketEventRestService" should s"return ok for a get request on $REST_ENDPOINT/id" in {
    
    /*
     * Create an entity
     */
    val marketEvent = MarketEventFactory.generate(name="Name1", summary="This is a test event")
    /*
     * Save it
     */
    val fSaved = MarketEventDao.save(marketEvent)
    val saved = wait(fSaved)
    
    Get(s"/$REST_ENDPOINT") ~> endpoints ~> check {
      
      println(responseAs[String])
      
      val rest_response = responseAs[Seq[MarketEvent]]
      
      println(rest_response(0))
      println(saved)
      
      assert(rest_response(0) == saved)
    }
  }
  
  
  
}