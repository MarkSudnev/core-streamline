package pl.sudneu.pipelineunits.sigma

import dev.forkhandles.result4k.kotest.shouldBeFailure
import dev.forkhandles.result4k.kotest.shouldBeSuccess
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.Cluster
import org.junit.jupiter.api.Test
import java.util.concurrent.Future

class KafkaMessageHandlerShould {

  private val producer = MockProducer(
    Cluster.empty(),
    false,
    null,
    org.apache.kafka.common.serialization.StringSerializer(),
    org.apache.kafka.common.serialization.StringSerializer()
  )

  @Test
  fun `accept and message to producer`() {
    val message = "Sit nisi sollicitudin non."
    val handler = KafkaMessageHandler(producer, "test-topic")
    handler.handle(message).shouldBeSuccess()
    producer.history().shouldNotBeEmpty()
  }

  @Test
  fun `return failure when kafka producer cannot send a message`() {
    val message = "Sit nisi sollicitudin non."
    val failingProducer = FailureProducer()
    val handler = KafkaMessageHandler(failingProducer, "test-topic")
    handler.handle(message) shouldBeFailure { error ->
      error.reason shouldBe "RuntimeException: some-error"
    }
  }
}

class FailureProducer(): MockProducer<String, String>() {

  override fun send(record: ProducerRecord<String?, String?>?): Future<RecordMetadata?>? {
    throw RuntimeException("some-error")
  }
}
