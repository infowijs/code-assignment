package nl.infowijs.codeassignment.doa

import nl.infowijs.codeassignment.models.Model
import java.util.UUID

interface Dao {
  suspend fun list(): List<Model>

  suspend fun get(id: UUID): Model? =
    list().find {
      it.id == id
    }

  suspend fun add(obj: Model): Boolean

  suspend fun update(obj: Model): Boolean

  suspend fun delete(obj: UUID): Boolean
}
