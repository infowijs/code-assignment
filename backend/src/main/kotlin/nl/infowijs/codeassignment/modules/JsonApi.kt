package nl.infowijs.codeassignment.modules

import io.vertx.core.json.JsonObject
import java.util.UUID

class JsonApi {
  companion object {
    fun formatModel(
      attributes: JsonObject,
      type: String? = null,
    ): JsonObject {
      val id = attributes.remove("id")
      return JsonObject()
        .apply {
          if (type != null) put("type", type.lowercase())
          if (id != null) put("id", id)
        }.put("attributes", attributes)
    }

    fun formatRelationship(
      name: String,
      type: String,
      id: UUID,
    ): JsonObject = JsonObject().put(name, JsonObject().put("data", JsonObject().putUUID("id", id).put("type", type.lowercase())))

    fun from(
      json: JsonObject,
      relationName: String,
    ): UUID? =
      json
        .getJsonObject(relationName, JsonObject())
        .getJsonObject("data", JsonObject())
        .getUUID("id")
  }
}
