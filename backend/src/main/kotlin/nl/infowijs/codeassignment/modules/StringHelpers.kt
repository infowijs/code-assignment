package nl.infowijs.codeassignment.modules

import java.util.UUID

fun String.toUUID(): UUID? =
  try {
    UUID.fromString(toString())
  } catch (e: IllegalArgumentException) {
    null
  } catch (e: NullPointerException) {
    null
  }
