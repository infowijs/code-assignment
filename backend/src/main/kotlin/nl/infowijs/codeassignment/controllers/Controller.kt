package nl.infowijs.codeassignment.controllers

import io.vertx.ext.web.RoutingContext
import nl.infowijs.codeassignment.modules.ApiStatus
import nl.infowijs.codeassignment.modules.ApiStatusException
import nl.infowijs.codeassignment.modules.HttpStatus
import nl.infowijs.codeassignment.modules.WebResponse

open class Controller {
  protected fun processContext(routingContext: RoutingContext) {
    // We're silly and you found this ;)
    routingContext
      .response()
      .setStatusCode(HttpStatus.Teapot.code)
      .setStatusMessage(HttpStatus.Teapot.statusMessage)
  }

  protected suspend fun catchAll(
    routingContext: RoutingContext,
    fn: suspend () -> Unit,
  ) {
    try {
      fn()
    } catch (e: Throwable) {
      val exception =
        when (e) {
          is ClassCastException -> ApiStatusException(ApiStatus.HTTP_CLASS_CAST_EXCEPTION)
          is ApiStatusException -> e
          else -> ApiStatusException(ApiStatus.FAILED, e.message)
        }
      return WebResponse(
        routingContext,
      ).endWithError(exception)
    }
  }
}
