package nl.infowijs.codeassignment.doa

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.infowijs.codeassignment.models.Model
import nl.infowijs.codeassignment.models.UserModel
import java.lang.Thread.sleep
import java.util.UUID

var users =
  mutableListOf(
    UserModel(
      UUID.fromString("74B8A217-FE7B-4A7B-95EF-63FB1E849456"),
      "user@infowijs.nl",
      "bccff2c52366c3daf8bf6a6fd69756bf633bb4c25a9e96e71a597603aaf1e296",
      listOf("general-access", "administrator"),
    ),
  )

class UsersDao : Dao {
  override suspend fun list(): List<UserModel> {
    withContext(Dispatchers.IO) {
      sleep((50..500).shuffled().first().toLong())
    } // Simulate network delay
    return users
  }

  override suspend fun add(obj: Model): Boolean {
    TODO("Not yet implemented")
  }

  override suspend fun update(obj: Model): Boolean {
    TODO("Not yet implemented")
  }

  suspend fun find(username: String): UserModel? {
    withContext(Dispatchers.IO) {
      sleep((50..500).shuffled().first().toLong())
    } // Simulate network delay
    return users.find { it.username == username }
  }

  override suspend fun delete(obj: UUID): Boolean {
    TODO("Not yet implemented")
  }
}
