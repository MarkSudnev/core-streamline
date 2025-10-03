package pl.sudneu.pipelineunits.sigma

import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.asSuccess

fun interface MessageHandler {

  fun handle(message: String): Result<Unit, SigmaError>

  companion object
}

fun KafkaMessageHandler(): MessageHandler {
  return MessageHandler { message -> Unit.asSuccess() }
}
