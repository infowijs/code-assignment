package nl.infowijs.codeassignment.modules

class ApiStatusException(val apiStatus: ApiStatus, val customMessage: String? = null) : Exception() {
  override val message: String
    get() = this.toString()

  override fun toString(): String {
    return if (!customMessage.isNullOrEmpty()) {
      customMessage
    } else {
      apiStatus.message
    }
  }
}
