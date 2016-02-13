package com.lot.blockAmount.service

import com.lot.BaseService
import com.lot.blockAmount.dao.BlockAmountDao
import com.lot.blockAmount.model.BlockAmount
import com.lot.blockAmount.model.BlockAmountJsonProtocol
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * The service that provides the REST interface for BlockAmount 
 */
object BlockAmountService extends BaseService {
  
  /**
   * For JSON serialization/deserialization
   */
  import com.lot.Json4sProtocol._

  /**
   * The DAO for DB access to BlockAmount
   */
  val dao = BlockAmountDao

  /**
   * Returns the list of blockAmounts
   */
  val list = getJson {
    path("blockAmounts") {
      complete(dao.list)
    }
  }

  /**
   * Returns a specific blockAmount identified by the id
   */
  val details = getJson {
    path("blockAmounts" / IntNumber) { id =>
      {
        complete(dao.get(id))
      }
    }
  }

  /**
   * Creates a new blockAmount
   */
  val create = postJson {
    path("blockAmounts") {
      entity(as[BlockAmount]) { blockAmount =>
        {
          complete(dao.save(blockAmount))
        }
      }
    }
  }
  
  /**
   * Updates an existing blockAmount identified by the id
   */
  val update = putJson {
    path("blockAmounts" / IntNumber) { id =>
      entity(as[BlockAmount]) { blockAmount =>
        {
          complete(dao.update(blockAmount))
        }
      }
    }
  }
  
  /**
   * Deletes the blockAmount identified by the id
   */
  val destroy = deleteJson {
    path("blockAmounts" / IntNumber) { id =>

      complete(dao.delete(id))

    }
  }

  /**
   * The list of methods which are exposed as the endpoint for this service
   */
  val endpoints =
    list ~ details ~ create ~ update ~ destroy

}