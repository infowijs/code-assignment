package nl.infowijs.codeassignment

import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.coAwait
import nl.infowijs.codeassignment.controllers.AuthorizationController
import nl.infowijs.codeassignment.controllers.ContactsController
import nl.infowijs.codeassignment.controllers.HealthController
import nl.infowijs.codeassignment.controllers.MessagesController
import nl.infowijs.codeassignment.data.ContactsDataVerticle
import nl.infowijs.codeassignment.modules.coroutineHandler

class MainVerticle : CoroutineVerticle() {
  private fun createRouter() =
    Router.router(vertx).apply {
      val messagesController = MessagesController()
      val contactsController = ContactsController()
      val authorizationController = AuthorizationController(config)

      post().handler(BodyHandler.create())
      route()
        .handler {
          it.put("config", config)
          it.next()
        }

      get("/healthz").handler(HealthController.healthCheck)
      get("/chat").coroutineHandler(messagesController::listMessages)
      get("/people").coroutineHandler(contactsController::listContacts)
      post("/people/:id/favorite").coroutineHandler(contactsController::markFavorite)

      post("/authorize").coroutineHandler(authorizationController::authorize)
      post("/token")
        .coroutineHandler("refresh-token", authorizationController::accessToken)
      get("/private/demo")
        .coroutineHandler("general-access", messagesController::privateRoute)
    }

  private suspend fun deployVerticles() {
    vertx.deployVerticle(ContactsDataVerticle()).coAwait()
  }

  override suspend fun start() {
    deployVerticles()
    val httpConfig = config.getJsonObject("http", JsonObject())
    val port = httpConfig.getInteger("port", 8888)

    vertx
      .createHttpServer()
      .requestHandler(createRouter())
      .listen(port) { http ->
        if (!http.succeeded()) {
          throw Exception("Failed to start HTTP server: ${http.cause().message}")
        }

        println("HTTP server started on port $port")
      }
  }
}
