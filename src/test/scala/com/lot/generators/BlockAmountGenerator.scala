package com.lot.generators

import org.scalacheck.Gen.choose
import org.scalacheck.Gen.oneOf
import com.lot.exchange.Exchange
import com.lot.order.model.Order
import com.lot.order.model.OrderType
import com.lot.blockAmount.model.BlockAmount
import org.joda.time.DateTime
import com.lot.blockAmount.model.BlockAmountType

object BlockAmountFactory {
  
  def generate(id: Option[Long] = None,
    user_id: Long = choose(1L, 50L).sample.get,  
    order_id: Long = choose(1L, 50L).sample.get,  
    blocked_amount: Double = 100 * choose(1L, 50L).sample.get,  
    actual_amount_charged: Double = 100* choose(1L, 50L).sample.get,  
    status: String=oneOf(BlockAmountType.TYPES).sample.get, 
    created_at: Option[DateTime]=None, 
    updated_at: Option[DateTime]=None) = {
    
    BlockAmount(None, user_id, order_id, blocked_amount, actual_amount_charged, status, None, None)
  }
  
   
}

