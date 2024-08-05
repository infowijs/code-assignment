package nl.infowijs.codeassignment.models

import io.vertx.core.json.JsonObject
import nl.infowijs.codeassignment.modules.ApiStatus
import nl.infowijs.codeassignment.modules.ApiStatusException
import nl.infowijs.codeassignment.modules.getUUID
import nl.infowijs.codeassignment.modules.putUUID
import java.time.Instant
import java.util.UUID

data class MessageModel(
  override val id: UUID? = null,
  val message: String? = null,
  val datetime: Instant? = null,
  val personId: UUID? = null,
) : Model {
  override fun toJsonObject(): JsonObject =
    JsonObject()
      .apply {
        if (id != null) putUUID("id", id)
        if (personId != null) putRelation("author", PersonModel.getType(), personId)
      }.put("message", message)
      .put("datatime", datetime)

  companion object : ModelFactory<MessageModel> {
    override fun from(data: JsonObject): MessageModel =
      MessageModel(
        id = data.getUUID("id") ?: throw ApiStatusException(ApiStatus.UUID_MISSING),
        message = data.getString("message"),
        datetime =
          try {
            Instant.parse(data.getString("datetime"))
          } catch (e: Exception) {
            null
          },
        personId = data.getRelation("author") ?: throw ApiStatusException(ApiStatus.UUID_MISSING),
      )
  }
}
