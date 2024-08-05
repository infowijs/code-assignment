package nl.infowijs.codeassignment.modules

import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.JWTOptions
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.auth.jwt.JWTAuthOptions
import io.vertx.ext.web.handler.JWTAuthHandler
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class Authorization(
  val vertx: Vertx,
  jwtConfig: JsonObject,
) {
  private val salt = "f66075b77f8d45d4b3d63f0dd671107346b9c269aa1c49b48cfcb75e3473774e"

  private fun hmacSha256(
    key: String,
    data: String,
  ): ByteArray {
    val hmacKey = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "HmacSHA256")
    val mac = Mac.getInstance("HmacSHA256")
    mac.init(hmacKey)
    return mac.doFinal(data.toByteArray(Charsets.UTF_8))
  }

  private fun ByteArray.toHex(): String = this.joinToString("") { "%02x".format(it) }

  fun processPassword(password: String): String = hmacSha256(salt, password).toHex()

  private val jwtAuthOptions =
    JWTAuthOptions()
      .setJwks(listOf(jwtConfig.getJsonObject("jwks") ?: throw ApiStatusException(ApiStatus.JWKS_NO_CONFIG)))
      .setJWTOptions(JWTOptions().setAlgorithm("RS256"))

  val provider: JWTAuth = JWTAuth.create(vertx, jwtAuthOptions)

  val authHandler: JWTAuthHandler = JWTAuthHandler.create(provider)
}
