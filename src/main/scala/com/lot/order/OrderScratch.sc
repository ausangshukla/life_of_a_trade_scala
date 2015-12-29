import com.lot.order.dao.OrderDao
import com.lot.order.model.Order
import org.joda.time.DateTime
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.util.Timeout
import scala.collection.immutable.Vector
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization.read
import org.json4s.native.Serialization.write

object OrderScratch {

  import com.lot.order.model.OrderJsonProtocol._
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  implicit val timeout = Timeout(5 seconds)       //> timeout  : akka.util.Timeout = Timeout(5 seconds)

  val dao = new OrderDao()                        //> dao  : com.lot.order.dao.OrderDao = com.lot.order.dao.OrderDao
                                                  //| @6a4f0359
  dao.createTables()

  val o1 = Order(None, "Buy", "Market", 1, 2, 100, 0, new DateTime())
  println(o1.created_at)
  dao.save(o1)
  val f1 = dao.get(1)
  val o2 = Await.result(f1, timeout.duration).asInstanceOf[Vector[Order]]

  println(o2)

  val f = dao.list

  val result = Await.result(f, timeout.duration).asInstanceOf[Vector[Order]]

}