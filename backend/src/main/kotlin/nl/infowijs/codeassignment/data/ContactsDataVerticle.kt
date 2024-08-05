package nl.infowijs.codeassignment.data

import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.CoroutineVerticle
import nl.infowijs.codeassignment.doa.ContactsDao
import nl.infowijs.codeassignment.models.PersonModel
import nl.infowijs.codeassignment.models.toJsonArray
import nl.infowijs.codeassignment.modules.coroutineLocalConsumer

class ContactsDataVerticle : CoroutineVerticle() {
  companion object {
    const val LIST_CONTACTS = "data.contacts.list"
    const val FIND_CONTACTS = "data.contacts.find"
    const val UPDATE = "data.contacts.update"
  }

  override suspend fun start() {
    super.start()

    vertx.eventBus().coroutineLocalConsumer(coroutineContext, LIST_CONTACTS, ::listContacts)
    vertx.eventBus().coroutineLocalConsumer(coroutineContext, FIND_CONTACTS, ::findContacts)
    vertx.eventBus().coroutineLocalConsumer(coroutineContext, UPDATE, ::updateContact)
  }

  private suspend fun listContacts(message: Message<JsonObject>) {
    val onlyShowFavorites = message.body().getBoolean("favorites", false)
    message.reply(
      ContactsDao()
        .list()
        .let {
          if (onlyShowFavorites) it.filter { contact -> contact.favorite ?: false } else it
        }.toJsonArray(),
    )
  }

  private suspend fun findContacts(message: Message<JsonObject>) {
    val request = message.body()

    message.reply(
      ContactsDao()
        .list()
        .filter {
          return@filter request.getJsonArray("id").contains(it.id.toString())
        }.toJsonArray(),
    )
  }

  private suspend fun updateContact(message: Message<JsonObject>) {
    val request = message.body()

    val person = PersonModel.from(request)

    message.reply(
      ContactsDao()
        .update(person),
    )
  }
}
