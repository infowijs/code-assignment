package nl.infowijs.codeassignment.models

import io.vertx.core.json.JsonObject
import nl.infowijs.codeassignment.modules.getUUID
import nl.infowijs.codeassignment.modules.putUUID
import java.util.UUID

data class PersonModel(
  override val id: UUID? = null,
  val name: String? = null,
  val avatar: String? = null,
  val email: String? = null,
  val phone: String? = null,
  var favorite: Boolean? = null,
) : Model {
  override fun toJsonObject(): JsonObject =
    JsonObject()
      .apply {
        if (id != null) putUUID("id", id)
        if (!email.isNullOrBlank()) put("email", email)
      }.put("name", name)
      .put("avatar", avatar)
      .put("phone", phone)
      .put("favorite", favorite ?: false)

  companion object : ModelFactory<PersonModel> {
    override fun from(data: JsonObject): PersonModel =
      PersonModel(
        id = data.getUUID("id"),
        name = data.getString("name"),
        avatar = data.getString("avatar"),
        email = data.getString("email"),
        phone = data.getString("phone"),
        favorite = data.getBoolean("favorite"),
      )
  }
}
