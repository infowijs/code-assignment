package nl.infowijs.codeassignment.doa

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.infowijs.codeassignment.models.Model
import nl.infowijs.codeassignment.models.PersonModel
import java.lang.Thread.sleep
import java.util.UUID

var contacts =
  mutableListOf(
    PersonModel(
      UUID.fromString("74B8A217-FE7B-4A7B-95EF-63FB1E849456"),
      "Hans van Maanen",
      "https://randomuser.me/api/portraits/men/12.jpg",
      "hans@email.com",
      favorite = true,
    ),
    PersonModel(
      UUID.fromString("12DFF063-E1FC-4E9E-AF27-DBF2AA2047AC"),
      "Belinda de Vries",
      "https://randomuser.me/api/portraits/women/12.jpg",
      phone = "+31612345678",
    ),
    PersonModel(
      UUID.fromString("0AC4DF4E-4231-452E-B84D-EFC8E2D649F2"),
      "Gerrit Groot",
      "https://randomuser.me/api/portraits/men/24.jpg",
      "gerritemail.com",
      "06 - 00 000 000",
    ),
    PersonModel(
      UUID.fromString("E1C8F6E8-6A6F-4D7D-8E3D-5E9E6F7D8C9A"),
      "Femke de Vries",
      "https://randomuser.me/api/portraits/women/6.jpg",
      "femke_de_vriezzzz@gmail.com",
    ),
    PersonModel(
      UUID.fromString("D7E9F8C7-6A6F-4D7D-8E3D-5E9E6F7D8C9A"),
      "Timo Barbegøs",
      "https://randomuser.me/api/portraits/men/2.jpg",
      "timo@finlønds.nl",
    ),
    PersonModel(
      UUID.fromString("13A69CAD-2160-4AB1-9D43-F6F38E700D01"),
      "Thom van Infowijs",
      "https://randomuser.me/api/portraits/men/3.jpg",
      "thomvaninfowijs@hotmail.tk",
    ),
    PersonModel(
      UUID.fromString("50601046-F382-4964-A9B5-0B07FBF73774"),
      "Chloë de la Rojas Vargas",
      "https://randomuser.me/api/portraits/women/9.jpg",
    ),
    PersonModel(
      UUID.fromString("29EB5501-E7AA-4815-9210-C39CE3F442F8"),
      "Tropical Danny",
      "https://3voor12.vpro.nl/.imaging/mte/3v12/opengraph/dam/3voor12/Guillermo.jpg/jcr:content/Guillermo.jpg",
      "danny@toppertje.nl",
      favorite = true,
    ),
    PersonModel(
      UUID.fromString("F53C0272-0436-4A3D-A1F2-FADA87D99AE4"),
      "Monique Willems",
      "https://randomuser.me/api/portraits/women/24.jpg",
      "monique@email.com",
      favorite = true,
    ),
  )

class ContactsDao : Dao {
  override suspend fun list(): List<PersonModel> {
    withContext(Dispatchers.IO) {
      sleep((50..500).shuffled().first().toLong())
    } // Simulate network delay
    return contacts
  }

  override suspend fun add(obj: Model): Boolean {
    TODO("Not yet implemented")
  }

  override suspend fun update(obj: Model): Boolean {
    withContext(Dispatchers.IO) {
      sleep((50..500).shuffled().first().toLong())
    } // Simulate network delay

    obj as PersonModel
    val contact =
      contacts
        .find {
          it.id == obj.id
        }?.let {
          it.favorite = obj.favorite
        }

    return contact != null
  }

  override suspend fun delete(obj: UUID): Boolean {
    TODO("Not yet implemented")
  }
}
