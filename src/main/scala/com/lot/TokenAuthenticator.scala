package com.lot

import scala.concurrent.{ ExecutionContext, Future }
import spray.routing.{ AuthenticationFailedRejection, RequestContext }
import spray.routing.authentication.{ Authentication, ContextAuthenticator }
import com.lot.user.model.User
import com.lot.user.dao.UserDao
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.json.JsonParser
import scala.collection.immutable.Map

/**
 * Token based authentication for Spray Routing.
 *
 * Extracts an API key from the header or querystring and authenticates requests.
 *
 * TokenAuthenticator[T] takes arguments for the named header/query string containing the API key and
 * an authenticator that returns an Option[T]. If None is returned from the authenticator, the request
 * is rejected.
 *
 * Usage:
 *
 *     val authenticator = TokenAuthenticator[User](
 *       headerName = "My-Api-Key",
 *       queryStringParameterName = "api_key"
 *     ) { key =>
 *       User.findByAPIKey(key)
 *     }
 *
 *     def auth: Directive1[User] = authenticate(authenticator)
 *
 *     val home = path("home") {
 *       auth { user =>
 *         get {
 *           complete("OK")
 *         }
 *       }
 *     }
 */

object TokenAuthenticator {

  object TokenExtraction {

    type TokenExtractor = RequestContext => (Option[String], Option[String])
    
    
    def fromHeader(): TokenExtractor = { context: RequestContext =>
      // Get and parse out the cookie 
      val cookieContents = findSessionIdCookieValue(context).getOrElse("{}")
      val cookie = JsonParser(cookieContents).convertTo[Map[String, String]]
      
      // Specific to Devise Token Auth - https://github.com/lynndylanhurley/devise_token_auth
      val access_token = cookie.get("access-token")
      val uid = cookie.get("uid")
      
      // Return the tokens
      println(s"fromHeader $access_token, $uid, $cookie")
      (access_token, uid)
    }

    /**
     * The devise rails app sends a cookie with the auth_headers
     * This cookie contains a URLEncoded key value pair required for authentication
     */
    private def findSessionIdCookieValue(ctx: RequestContext): Option[String] =
      ctx.request.cookies.find(_.name == "auth_headers").map(c => java.net.URLDecoder.decode(c.content, "UTF-8"))
  }

  class TokenAuthenticator[T](extractor: TokenExtraction.TokenExtractor, authenticator: ((String,String) => Future[Option[T]]))(implicit executionContext: ExecutionContext) extends ContextAuthenticator[T] {

    import AuthenticationFailedRejection._

    def apply(context: RequestContext): Future[Authentication[T]] =
      extractor(context) match {
      case (Some(token), Some(uid)) =>
          authenticator(token, uid) map {
            case Some(t) =>
              Right(t)
            case None =>
              Left(AuthenticationFailedRejection(CredentialsRejected, List()))
          }  
      case (_, _) =>
          Future(
            Left(AuthenticationFailedRejection(CredentialsMissing, List())))
        
      }

  }

  def apply[T]()(authenticator: ((String,String) => Future[Option[T]]))(implicit executionContext: ExecutionContext) = {

    def extractor(context: RequestContext) =
      TokenExtraction.fromHeader()(context)

    new TokenAuthenticator(extractor, authenticator)
  }

}