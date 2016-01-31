package com.lot.user.service

import com.lot.BaseService
import com.lot.user.dao.UserDao
import com.lot.user.model.User
import com.lot.user.model.UserJsonProtocol
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * The service that provides the REST interface for User 
 */
object UserService extends BaseService {
  
  /**
   * For JSON serialization/deserialization
   */
  import com.lot.Json4sProtocol._

  /**
   * The DAO for DB access to User
   */
  val dao = UserDao

  /**
   * Returns the list of users
   */
  val list = getJson {
    path("users") {
      complete(dao.list)
    }
  }

  /**
   * Returns a specific user identified by the id
   */
  val details = getJson {
    path("users" / IntNumber) { id =>
      {
        complete(dao.get(id))
      }
    }
  }

  /**
   * Creates a new user
   */
  val create = postJson {
    path("users") {
      entity(as[User]) { user =>
        {
          complete(dao.save(user))
        }
      }
    }
  }
  
  /**
   * Updates an existing user identified by the id
   */
  val update = putJson {
    path("users" / IntNumber) { id =>
      entity(as[User]) { user =>
        {
          complete(dao.update(user))
        }
      }
    }
  }
  
  /**
   * Deletes the user identified by the id
   */
  val destroy = deleteJson {
    path("users" / IntNumber) { id =>

      complete(dao.delete(id))

    }
  }

  /**
   * The list of methods which are exposed as the endpoint for this service
   */
  val endpoints =
    list ~ details ~ create ~ update ~ destroy

}