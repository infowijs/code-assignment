package nl.infowijs.codeassignment.modules

import io.vertx.core.eventbus.EventBus
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.authorization.PermissionBasedAuthorization
import io.vertx.ext.auth.jwt.authorization.JWTAuthorization
import io.vertx.ext.web.Route
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.AuthorizationHandler
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun <T> EventBus.coroutineLocalConsumer(
  context: CoroutineContext,
  address: String,
  fn: suspend (Message<T>) -> Unit,
) = localConsumer<T>(address) { message ->
  coroutineConsumerHandler(context, fn, message)
}

private fun <T> coroutineConsumerHandler(
  context: CoroutineContext,
  fn: suspend (Message<T>) -> Unit,
  message: Message<T>,
) {
  GlobalScope.launch(context) {
    try {
      fn(message)
    } catch (e: ApiStatusException) {
      message.fail(e.apiStatus.code, e.customMessage ?: e.apiStatus.message)
    } catch (e: Exception) {
      message.fail(e.hashCode(), e.toString())
    }
  }
}

/**
 * Handler for Public Routes
 */
fun Route.coroutineHandler(fn: suspend (RoutingContext) -> Unit): Route {
  handler { ctx ->
    GlobalScope.launch(ctx.vertx().dispatcher()) {
      try {
        fn(ctx)
      } catch (e: Exception) {
        ctx.fail(e)
      }
    }
  }
  return this
}

/**
 * Handler for Private Routes
 */
fun Route.coroutineHandler(
  permission: String,
  fn: suspend (RoutingContext) -> Unit,
): Route {
  handler { ctx ->
    Authorization(
      ctx.vertx(),
      ctx
        .vertx()
        .orCreateContext
        .config()
        .getJsonObject("jwt", JsonObject()),
    ).authHandler
      .handle(ctx)
  }

  handler { ctx ->
    AuthorizationHandler
      .create(PermissionBasedAuthorization.create(permission))
      .addAuthorizationProvider(JWTAuthorization.create("/"))
      .handle(ctx)
  }
  handler { ctx ->
    GlobalScope.launch(ctx.vertx().dispatcher()) {
      try {
        if (permission.isNotBlank() && ctx.user() == null) {
          throw ApiStatusException(ApiStatus.UNAUTHORISED)
        }

        fn(ctx)
      } catch (e: ApiStatusException) {
        WebResponse(ctx).endWithError(e)
      } catch (e: Exception) {
        WebResponse(ctx).endWithError(e)
      }
    }
  }
  return this
}
