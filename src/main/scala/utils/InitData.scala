package utils

import com.lot.ordermanager.dao.OrderDao
import com.lot.ordermanager.model.OrderType
import org.joda.time.DateTime
import com.lot.ordermanager.model.Order

object InitData {

  def init() = {
    val dao = new OrderDao()
    val o1 = Order(Some(1), OrderType.BUY, OrderType.MARKET, 1, 10, 100, 102.3, new DateTime())
    val o2 = Order(Some(2), OrderType.SELL, OrderType.MARKET, 1, 10, 120, 103.3, new DateTime())
    val o3 = Order(Some(3), OrderType.BUY, OrderType.LIMIT, 1, 10, 150, 98.3, new DateTime())
    dao.save(o1)
    dao.save(o2)
    dao.save(o3)
  }
}