package nl.infowijs.codeassignment.modules

class ApiStatus {
  val code: Int
  var message: String = ""
  val httpStatus: HttpStatus

  /**
   * Create an [ApiStatus] object with unique number and Error Message
   *
   * @param code Unique Error status number
   * @param message The Error Message
   */
  constructor(code: Int, message: String, httpStatus: HttpStatus = HttpStatus.InternalServerError) {
    this.code = code
    this.message = message
    this.httpStatus = httpStatus
  }

  /**
   * Create an [ApiStatus] object with unique number and copied Error Message
   *
   * @param code Unique Error status number
   * @param extendFrom Pass on an [ApiStatus] object to use its error message
   */
  constructor(code: Int, extendFrom: ApiStatus) {
    this.code = code
    this.message = extendFrom.message
    this.httpStatus = extendFrom.httpStatus
  }

  constructor(code: Int) {
    this.code = code
    this.message = ""
    this.httpStatus = HttpStatus.InternalServerError
  }

  fun message(vararg values: String): String = String.format(this.message, *values)

  companion object {
    val EXTERNAL_ERROR = ApiStatus(1, "An external problem has caused an error")
    val UUID_MISSING = ApiStatus(2, "The object could not be found.")
    val UUID_MISMATCH = ApiStatus(3, "The object could not be found.")
    val DATE_MISMATCH = ApiStatus(4, "An incorrect date has been provided.")

    val FAILED = ApiStatus(10, "Something went wrong")
    val FAILED_FIND = ApiStatus(11, FAILED)

    val HTTP_CLASS_CAST_EXCEPTION =
      ApiStatus(
        21,
        "An error occurred while processing the request. The JSON Object contains an incorrect data type.",
        HttpStatus.BadRequest,
      )
    val HTTP_PAGE_NOT_FOUND = ApiStatus(22, "The page you are looking for does not exist.")

    val UNAUTHORISED = ApiStatus(30, "You do not have access to this resource.", HttpStatus.Unauthorized)
    val AUTH_NO_PERMISSIONS = ApiStatus(31, "You do not have access to this resource.", HttpStatus.Forbidden)
    val JWKS_NO_CONFIG = ApiStatus(40, "The JWKS is not configured.", HttpStatus.Teapot)
  }
}
