package nl.infowijs.codeassignment.data

import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.CoroutineVerticle
import nl.infowijs.codeassignment.doa.MessagesDao
import nl.infowijs.codeassignment.models.toJsonArray
import nl.infowijs.codeassignment.modules.coroutineLocalConsumer

class MessagesDataVerticle : CoroutineVerticle() {
  companion object {
    const val LIST_MESSAGES = "data.messages.list"
  }

  override suspend fun start() {
    super.start()

    vertx.eventBus().coroutineLocalConsumer(coroutineContext, LIST_MESSAGES, ::listMessages)
  }

  private suspend fun listMessages(message: Message<JsonObject>) {
    val messages = MessagesDao().list()

    message.reply(messages.toJsonArray())
  }
}
