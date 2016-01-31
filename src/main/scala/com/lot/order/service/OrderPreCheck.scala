
import akka.actor.Actor
import akka.actor.ActorLogging
import com.lot.exchange.Message.NewOrder
import com.lot.exchange.Message.ModifyOrder
import com.lot.exchange.Message.CancelOrder
import com.lot.order.model.Order
import scala.concurrent.duration._
import akka.util.Timeout
import scala.concurrent.Await
import akka.actor.ActorRef
import com.lot.security.model.PriceMessage
import com.lot.security.model.Price
import akka.pattern.ask
import scala.concurrent.ExecutionContext.Implicits.global
import com.lot.user.dao.UserDao

class OrderPreCheck(securityManager: ActorRef, userManager: ActorRef) extends Actor with ActorLogging {

  def receive = {
    case NewOrder(order, at)    => { handleNewOrder(order) }
    case CancelOrder(order, at) => { handleNewOrder(order) }
    case _                      => {}
  }

  /**
   * Ensure that the amount required for the order is blocked in the user account
   */
  def handleNewOrder(order: Order) = {
    /*
     * Load the price of the security from the securityManager
     */
    implicit val timeout = Timeout(5 second)
    /*
     * Ask for the price
     */
    val futurePrice = securityManager ? PriceMessage.Get(Price(order.security_id, 0.0))
    futurePrice.map { priceMsg =>
      /*
       * We get a PriceMessage.Value which has a Price in it
       */
      val price = priceMsg.asInstanceOf[PriceMessage.Value].price 
      val amount = price.price * order.quantity
      
      
    }
  }

}
