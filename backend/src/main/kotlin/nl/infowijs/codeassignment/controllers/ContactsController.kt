package nl.infowijs.codeassignment.controllers

import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.coAwait
import nl.infowijs.codeassignment.data.ContactsDataVerticle
import nl.infowijs.codeassignment.models.PersonModel
import nl.infowijs.codeassignment.modules.ApiStatus
import nl.infowijs.codeassignment.modules.ApiStatusException
import nl.infowijs.codeassignment.modules.WebResponse
import nl.infowijs.codeassignment.modules.toUUID

class ContactsController : Controller() {
  suspend fun listContacts(routingContext: RoutingContext) =
    catchAll(routingContext) {
      val onlyShowFavorites = routingContext.queryParam("favorites").contains("true")

      val contacts =
        routingContext
          .vertx()
          .eventBus()
          .request<JsonArray>(ContactsDataVerticle.LIST_CONTACTS, JsonObject().put("favorites", onlyShowFavorites))
          .coAwait()
          .body()
          .map {
            it as JsonObject
            PersonModel.from(it)
          }

      WebResponse(routingContext)
        .end(contacts, "Person")
    }

  suspend fun markFavorite(routingContext: RoutingContext) =
    catchAll(routingContext) {
      val id = routingContext.pathParam("id").toUUID()
      val person = PersonModel(id = id, favorite = true)

      processContext(routingContext)

      val response =
        routingContext
          .vertx()
          .eventBus()
          .request<Boolean>(ContactsDataVerticle.UPDATE, person.toJsonObject())
          .coAwait()
          .body()

      if (!response) {
        throw ApiStatusException(ApiStatus.FAILED)
      }

      WebResponse(routingContext).end(JsonObject().put("success", true))
    }
}
