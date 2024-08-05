package nl.infowijs.codeassignment.modules

import io.vertx.core.json.JsonObject
import java.util.UUID

fun JsonObject.getUUID(
  key: String,
  defaultValue: UUID?,
): UUID? =
  try {
    UUID.fromString(this.getString(key, defaultValue.toString()))
  } catch (e: IllegalArgumentException) {
    null
  } catch (e: NullPointerException) {
    null
  }

fun JsonObject.getUUID(key: String): UUID? = this.getUUID(key, null)

fun JsonObject.putUUID(
  key: String,
  value: UUID,
): JsonObject = this.put(key, value.toString())
