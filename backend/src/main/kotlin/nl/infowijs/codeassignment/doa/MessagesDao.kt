package nl.infowijs.codeassignment.doa

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.infowijs.codeassignment.models.MessageModel
import nl.infowijs.codeassignment.models.Model
import java.lang.Thread.sleep
import java.time.Instant
import java.util.UUID

class MessagesDao : Dao {
  override suspend fun list(): List<MessageModel> {
    val belinda = UUID.fromString("12DFF063-E1FC-4E9E-AF27-DBF2AA2047AC")
    val monique = UUID.fromString("F53C0272-0436-4A3D-A1F2-FADA87D99AE4")

    withContext(Dispatchers.IO) {
      sleep((50..500).shuffled().first().toLong())
    } // Simulate network delay

    return listOf(
      MessageModel(
        UUID.fromString("67F0B5B3-C07C-4527-A9CE-3020B6A7D675"),
        "Wie staat er dubbel geparkeerd in vak 14/15 met kenteken ZS-234-GA?\nIk kom mijn auto niet meer in...\n\nGraag je auto verplaatsen vóór 16:00 zodat ik weer naar huis kan!!!",
        Instant.now().minusSeconds(1500),
        monique,
      ),
      MessageModel(
        UUID.fromString("1BD493F5-EF6B-498E-BBD5-73485E542E9C"),
        "Hallo collega's,\n\nzouden jullie in week 35 de hele week een parkeerplaats voor mij willen reserveren?\n\nDank!",
        Instant.now().minusSeconds(43200),
        belinda,
      ),
    )
  }

  override suspend fun add(obj: Model): Boolean {
    TODO("Not yet implemented")
  }

  override suspend fun update(obj: Model): Boolean {
    TODO("Not yet implemented")
  }

  override suspend fun delete(obj: UUID): Boolean {
    TODO("Not yet implemented")
  }
}
