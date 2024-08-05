package nl.infowijs.codeassignment.models

import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import nl.infowijs.codeassignment.modules.getUUID
import nl.infowijs.codeassignment.modules.putUUID
import java.util.UUID

data class UserModel(
  override val id: UUID? = null,
  val username: String? = null,
  val hashedPassword: String? = null,
  val permissions: List<String>? = null,
) : Model {
  override fun toJsonObject(): JsonObject =
    JsonObject()
      .apply {
        if (id != null) putUUID("id", id)
      }.put("username", username)
      .put("permissions", JsonArray(permissions))

  companion object : ModelFactory<UserModel> {
    override fun from(data: JsonObject): UserModel =
      UserModel(
        id = data.getUUID("id"),
        username = data.getString("username"),
        hashedPassword = data.getString("hashedPassword"),
        permissions = data.getJsonArray("permissions").map { it.toString() },
      )
  }
}
