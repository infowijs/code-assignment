package nl.infowijs.codeassignment.modules

import io.vertx.core.http.HttpHeaders
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import nl.infowijs.codeassignment.models.Model
import nl.infowijs.codeassignment.models.toJsonArray

class WebResponse(
  val routingContext: RoutingContext,
  var httpStatus: HttpStatus? = null,
) {
  private var data: JsonObject? = null
  private var included: JsonArray = JsonArray()

  private fun wrapJsonData(data: JsonArray) = JsonObject().put("data", data)

  private fun wrapJsonData(data: JsonObject) = wrapJsonData(JsonArray().add(data))

  fun addRelations(
    relationData: JsonObject,
    type: String,
  ): WebResponse = addRelations(JsonArray().add(relationData), type)

  fun addRelations(
    relationData: List<Model>,
    type: String? = null,
  ): WebResponse = addRelations(relationData.toJsonArray(), type ?: relationData.first().getType())

  fun addRelations(
    relationData: JsonArray,
    type: String,
  ): WebResponse {
    this.included
      .addAll(
        JsonArray(
          relationData.map { attributes ->
            attributes as JsonObject
            JsonApi.formatModel(attributes, type)
          },
        ),
      )

    return this
  }

  fun addData(
    data: List<Model>,
    modelType: String? = null,
  ): WebResponse =
    addData(
      data.toJsonArray(),
      modelType ?: data.first().getType(),
    )

  fun addData(
    data: JsonArray,
    modelType: String? = null,
  ): WebResponse {
    this.data =
      wrapJsonData(
        JsonArray(
          data.map { attributes ->
            attributes as JsonObject
            JsonApi.formatModel(attributes, modelType)
          },
        ),
      )
    return this
  }

  fun addData(
    data: JsonObject,
    modelType: String? = null,
  ): WebResponse {
    this.data = wrapJsonData(JsonApi.formatModel(data, modelType))

    return this
  }

  fun end(data: String) {
    routingContext
      .response()
      .putHeader(HttpHeaders.CONTENT_TYPE, "text/plain")
      .setStatusCode(200)
      .setStatusMessage("OK")
      .end(data)
  }

  /**
   * End with Data
   * Shortcut to the combined callables addData(...) and end()
   */
  fun end(
    data: List<Model>,
    modelType: String? = null,
  ) {
    addData(data, modelType)
    end()
  }

  /**
   * End with Data
   * Shortcut to the combined callables addData(...) and end()
   */
  fun end(
    data: JsonObject,
    modelType: String? = null,
  ) {
    addData(data, modelType)
    end()
  }

  /**
   * End with Data
   * Shortcut to the combined callables addData(...) and end()
   */
  fun end(
    data: JsonArray,
    modelType: String? = null,
  ) {
    addData(data, modelType)
    end()
  }

  fun endWithError(exception: Exception) = endWithError(ApiStatusException(ApiStatus.FAILED, exception.message))

  fun endWithError(apiStatusException: ApiStatusException) {
    routingContext
      .response()
      .setStatusCode(
        apiStatusException.apiStatus.httpStatus.code,
      ).setStatusMessage(apiStatusException.apiStatus.httpStatus.statusMessage)
    routingContext.response().putHeader("Content-Type", "application/vnd.api+json")
    routingContext.json(
      JsonObject().put(
        "errors",
        JsonArray().add(
          JsonObject()
            .put("status", apiStatusException.apiStatus.code)
            .put("title", apiStatusException.message),
        ),
      ),
    )
  }

  fun end() {
    httpStatus?.run {
      routingContext.response().setStatusCode(this.code).setStatusMessage(this.statusMessage)
    }
    routingContext.response().putHeader("Content-Type", "application/vnd.api+json")
    routingContext.json(
      data?.run {
        if (!included.isEmpty) {
          this.mergeIn(JsonObject().put("included", included))
        }

        this
      },
    )
  }
}
