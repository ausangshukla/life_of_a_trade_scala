package com.lot

import scala.concurrent.{ ExecutionContext, Future }
import spray.routing.{ AuthenticationFailedRejection, RequestContext }
import spray.routing.authentication.{ Authentication, ContextAuthenticator }
import com.lot.user.model.User
import com.lot.user.dao.UserDao

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
    
    // Specific to Devise Token Auth - https://github.com/lynndylanhurley/devise_token_auth
    def fromHeader(): TokenExtractor = { context: RequestContext =>
      val access_token = context.request.headers.find(h => h.name == "access-token").map(_.value)
      val uid = context.request.headers.find(h => h.name == "uid").map(_.value)
      (access_token, uid)
    }

    // Not used
    def fromQueryString(): TokenExtractor = { context: RequestContext =>
       (context.request.uri.query.get("access-token"), context.request.uri.query.get("uid"))
    }
  }

  class TokenAuthenticator[T](extractor: TokenExtraction.TokenExtractor, authenticator: ((String,String) => Future[Option[T]]))(implicit executionContext: ExecutionContext) extends ContextAuthenticator[T] {

    import AuthenticationFailedRejection._

    def apply(context: RequestContext): Future[Authentication[T]] =
      extractor(context) match {
        case (None, None) =>
          Future(
            Left(AuthenticationFailedRejection(CredentialsMissing, List())))
        case (Some(token), Some(uid)) =>
          authenticator(token, uid) map {
            case Some(t) =>
              Right(t)
            case None =>
              Left(AuthenticationFailedRejection(CredentialsRejected, List()))
          }
      }

  }

  def apply[T]()(authenticator: ((String,String) => Future[Option[T]]))(implicit executionContext: ExecutionContext) = {

    def extractor(context: RequestContext) =
      TokenExtraction.fromHeader()(context)

    new TokenAuthenticator(extractor, authenticator)
  }

}