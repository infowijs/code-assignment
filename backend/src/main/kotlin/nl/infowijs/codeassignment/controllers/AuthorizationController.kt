package nl.infowijs.codeassignment.controllers

import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.JWTOptions
import io.vertx.ext.web.RoutingContext
import nl.infowijs.codeassignment.doa.UsersDao
import nl.infowijs.codeassignment.modules.ApiStatus
import nl.infowijs.codeassignment.modules.ApiStatusException
import nl.infowijs.codeassignment.modules.Authorization
import nl.infowijs.codeassignment.modules.WebResponse

class AuthorizationController(
  val config: JsonObject,
) : Controller() {
  suspend fun authorize(routingContext: RoutingContext) =
    catchAll(routingContext) {
      val requestBody = routingContext.body().asJsonObject()
      val username = requestBody.getString("username")
      val password = requestBody.getString("password")

      if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
        throw ApiStatusException(ApiStatus.UNAUTHORISED)
      }

      val user = UsersDao().find(username)
      val authorization = Authorization(routingContext.vertx(), config.getJsonObject("jwt", JsonObject()))
      if (user?.hashedPassword != authorization.processPassword(password)) {
        throw ApiStatusException(ApiStatus.UNAUTHORISED)
      }

      WebResponse(
        routingContext,
      ).end(
        JsonObject()
          .put(
            "token",
            authorization.provider.generateToken(
              JsonObject()
                .put("user", JsonObject().put("id", user.id.toString()).put("username", user.username))
                .put("permissions", JsonArray().add("refresh-token")),
              JWTOptions()
                .setExpiresInMinutes(60)
                .setAlgorithm("RS256")
                .setSubject(user.username)
                .setIssuer("nl.infowijs.codeassignment.refreshToken"),
            ),
          ).put("expiration_time", 3600),
        "refresh_token",
      )
    }

  suspend fun accessToken(routingContext: RoutingContext) =
    catchAll(routingContext) {
      val jwt = routingContext.user().principal()
      val issuer = jwt.getString("iss")

      if (issuer != "nl.infowijs.codeassignment.refreshToken") {
        throw ApiStatusException(ApiStatus.AUTH_NO_PERMISSIONS)
      }

      val user = UsersDao().find(routingContext.user().subject())
      val authorization = Authorization(routingContext.vertx(), config.getJsonObject("jwt", JsonObject()))

      if (user == null) {
        throw ApiStatusException(ApiStatus.UNAUTHORISED)
      }

      WebResponse(
        routingContext,
      ).end(
        JsonObject()
          .put(
            "token",
            authorization.provider.generateToken(
              JsonObject()
                .put("user", JsonObject().put("id", user.id.toString()).put("username", user.username))
                .put("permissions", user.permissions),
              JWTOptions()
                .setExpiresInMinutes(1)
                .setAlgorithm("RS256")
                .setSubject(user.username)
                .setIssuer("nl.infowijs.codeassignment.accessToken"),
            ),
          ).put("expiration_time", 60),
        "access_token",
      )
    }
}
