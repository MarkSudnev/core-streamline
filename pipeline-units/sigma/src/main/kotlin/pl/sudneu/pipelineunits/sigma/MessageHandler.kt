package pl.sudneu.pipelineunits.sigma

import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.mapFailure
import dev.forkhandles.result4k.resultFrom
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord

fun interface MessageHandler {

  fun handle(message: String): Result<Unit, SigmaError>

  companion object
}

fun KafkaMessageHandler(
  producer: Producer<String, String>,
  topic: String
): MessageHandler {
  return MessageHandler { message ->
    val record = ProducerRecord<String, String>(topic, message)
    resultFrom {
      producer.send(record).get()
      producer.flush()
    }.mapFailure { SigmaError(it.toSigmaMessage()) }
  }
}
