package nl.infowijs.codeassignment.models

import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import nl.infowijs.codeassignment.modules.JsonApi
import java.util.UUID

interface Model {
  val id: UUID?

  fun getType(): String =
    this::class.java.simpleName
      .replace("Model", "")
      .lowercase()

  fun toJsonObject(): JsonObject

  fun JsonObject.putRelation(
    relationName: String,
    modelType: String,
    id: UUID,
  ): JsonObject {
    val relationships = this.getJsonObject("relationships", JsonObject())
    relationships.mergeIn(JsonApi.formatRelationship(relationName, modelType, id))

    this.put(
      "relationships",
      relationships,
    )

    return this
  }
}

interface ModelFactory<out Model> {
  fun from(data: JsonObject): Model

  fun getType(): String =
    this::class.java.enclosingClass.simpleName
      .replace("Model", "")
      .lowercase()

  fun JsonObject.getRelation(relationName: String): UUID? =
    JsonApi.from(
      this
        .getJsonObject("relationships", JsonObject()),
      relationName,
    )
}

fun List<Model>.toJsonArray(): JsonArray = JsonArray(this.map { it.toJsonObject() })
