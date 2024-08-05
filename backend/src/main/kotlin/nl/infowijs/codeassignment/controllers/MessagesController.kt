package nl.infowijs.codeassignment.controllers

import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.coAwait
import nl.infowijs.codeassignment.data.ContactsDataVerticle
import nl.infowijs.codeassignment.data.MessagesDataVerticle
import nl.infowijs.codeassignment.models.MessageModel
import nl.infowijs.codeassignment.models.PersonModel
import nl.infowijs.codeassignment.modules.WebResponse

class MessagesController : Controller() {
  suspend fun listMessages(routingContext: RoutingContext) =
    catchAll(routingContext) {
      val messages =
        routingContext
          .vertx()
          .eventBus()
          .request<JsonArray>(MessagesDataVerticle.LIST_MESSAGES, null)
          .coAwait()
          .body()
          .map {
            it as JsonObject
            MessageModel.from(it)
          }

      val authorsRelationRequest =
        JsonObject()
          .put(
            "id",
            JsonArray(
              messages
                .map {
                  it.personId.toString()
                }.toSet()
                .toList(),
            ),
          )

      val authors =
        routingContext
          .vertx()
          .eventBus()
          .request<JsonArray>(ContactsDataVerticle.FIND_CONTACTS, authorsRelationRequest)
          .coAwait()
          .body()
          .map {
            it as JsonObject
            PersonModel.from(it)
          }

      WebResponse(routingContext)
        .addData(messages)
        .addRelations(authors)
        .end()
    }

  suspend fun privateRoute(routingContext: RoutingContext) =
    catchAll(routingContext) {
      WebResponse(routingContext)
        .end(JsonObject().put("demo", "works"), "demo")
    }
}
