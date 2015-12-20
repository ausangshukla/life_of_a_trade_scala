import com.lot.ordermanager.dao.OrderDao
import com.lot.ordermanager.model.Order
import org.joda.time.DateTime
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.util.Timeout
import scala.collection.immutable.Vector
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization.read
import org.json4s.native.Serialization.write

object Test {

  import com.lot.ordermanager.model.OrderJsonProtocol._
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  implicit val timeout = Timeout(5 seconds)       //> timeout  : akka.util.Timeout = Timeout(5 seconds)

  val dao = new OrderDao()                        //> dao  : com.lot.ordermanager.dao.OrderDao = com.lot.ordermanager.dao.OrderDao
                                                  //| @6a4f0359
  dao.createTables()                              //> SLF4J: Class path contains multiple SLF4J bindings.
                                                  //| SLF4J: Found binding in [jar:file:/home/thimmaiah/tools/activator-dist-1.3.5
                                                  //| /repository/ch.qos.logback/logback-classic/1.1.3/jars/logback-classic.jar!/o
                                                  //| rg/slf4j/impl/StaticLoggerBinder.class]
                                                  //| SLF4J: Found binding in [jar:file:/home/thimmaiah/tools/activator-dist-1.3.5
                                                  //| /repository/org.slf4j/slf4j-nop/1.6.4/jars/slf4j-nop.jar!/org/slf4j/impl/Sta
                                                  //| ticLoggerBinder.class]
                                                  //| SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanat
                                                  //| ion.
                                                  //| SLF4J: Actual binding is of type [ch.qos.logback.classic.util.ContextSelecto
                                                  //| rStaticBinder]
                                                  //| 12/19 23:37:52 DEBUG[main] s.b.D.action - #1: [fused] andThen
                                                  //|       1: schema.create [create table `ORDERS` (`id` INTEGER NOT NULL AUTO_IN
                                                  //| CREMENT PRIMARY KEY,`buy_sell` TEXT NOT NULL,`order_type` TEXT NOT NULL,`use
                                                  //| r_id` INTEGER NOT NULL,`security_id` INTEGER NOT NULL,`quantity` DOUBLE NOT 
                                                  //| NULL,`price` DOUBLE NOT NULL,`created_at` TIME
                                                  //| Output exceeds cutoff limit.

  val o1 = Order(None, "Buy", "Market", 1, 2, 100, 0, new DateTime())
                                                  //> o1  : com.lot.ordermanager.model.Order = Order(None,Buy,Market,1,2,100.0,0.0
                                                  //| ,2015-12-19T23:37:52.186-05:00)
  println(o1.created_at)                          //> 2015-12-19T23:37:52.186-05:00
  dao.save(o1)                                    //> 12/19 23:37:52 DEBUG[main] s.b.D.action - #1: += [insert into `ORDERS` (`buy
                                                  //| _sell`,`order_type`,`user_id`,`security_id`,`quantity`,`price`,`created_at`)
                                                  //|   values (?,?,?,?,?,?,?)]
                                                  //| res1: scala.concurrent.Future[Int] = scala.concurrent.impl.Promise$DefaultPr
                                                  //| omise@1dcfae07
  val f1 = dao.get(1)                             //> 12/19 23:37:52 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`bu
                                                  //| y_sell`, x2.`order_type`, x2.`user_id`, x2.`security_id`, x2.`quantity`, x2.
                                                  //| `price`, x2.`created_at` from (select x3.`security_id` as `security_id`, x3.
                                                  //| `created_at` as `created_at`, x3.`id` as `id`, x3.`price` as `price`, x3.`or
                                                  //| der_type` as `order_type`, x3.`buy_sell` as `buy_sell`, x3.`quantity` as `qu
                                                  //| antity`, x3.`user_id` as `user_id` from `ORDERS` x3 where x3.`id` = 1 limit 
                                                  //| 1) x2]
                                                  //| f1  : scala.concurrent.Future[Seq[com.lot.ordermanager.model.OrderTable#Tabl
                                                  //| eElementType]] = scala.concurrent.impl.Promise$DefaultPromise@74e07abd
  val o2 = Await.result(f1, timeout.duration).asInstanceOf[Vector[Order]]
                                                  //> o2  : scala.collection.immutable.Vector[com.lot.ordermanager.model.Order] = 
                                                  //| Vector(Order(Some(1),Buy,Market,1,2,100.0,0.0,2015-12-19T23:37:52.000-05:00)
                                                  //| )

  println(o2)                                     //> Vector(Order(Some(1),Buy,Market,1,2,100.0,0.0,2015-12-19T23:37:52.000-05:00)
                                                  //| )

  val f = dao.list                                //> 12/19 23:37:52 DEBUG[main] s.b.D.action - #1: result [select x2.`id`, x2.`bu
                                                  //| y_sell`, x2.`order_type`, x2.`user_id`, x2.`security_id`, x2.`quantity`, x2.
                                                  //| `price`, x2.`created_at` from `ORDERS` x2]
                                                  //| f  : scala.concurrent.Future[Seq[com.lot.ordermanager.model.Order]] = scala.
                                                  //| concurrent.impl.Promise$DefaultPromise@1670f2fd

  val result = Await.result(f, timeout.duration).asInstanceOf[Vector[Order]]
                                                  //> result  : scala.collection.immutable.Vector[com.lot.ordermanager.model.Order
                                                  //| ] = Vector(Order(Some(1),Buy,Market,1,2,100.0,0.0,2015-12-19T23:37:52.000-05
                                                  //| :00))

}