package pl.sudneu.pipelineunits.omega

import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.asFailure
import dev.forkhandles.result4k.asSuccess
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.maps.shouldHaveKey
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.clients.consumer.OffsetResetStrategy.EARLIEST
import org.apache.kafka.common.TopicPartition
import org.junit.jupiter.api.Test

class OmegaMessageHandlerShould {

  private val topicName = "test-topic"
  private val topicPartition = TopicPartition(topicName, 0)
  private val consumer = MockConsumer<String, String>(EARLIEST)

  @Test
  fun `subscribe to topic`() {
    val handler = OmegaMessageHandler(consumer) { Unit.asSuccess() }
    prepareConsumer()
    consumer.schedulePollTask { sendMessage() }
    stopConsumer()
    handler.listen(topicName)
    consumer.subscription() shouldContain topicName
  }

  @Test
  fun `call document omega handler`() {
    val sentMessage = "one two three"
    val processor = DummyOmegaProcessor()
    val handler = OmegaMessageHandler(consumer, processor)
    prepareConsumer()
    consumer.schedulePollTask { sendMessage(sentMessage) }
    stopConsumer()
    handler.listen(topicPartition.topic())

    processor.receivedMessage shouldBe sentMessage
  }

  @Test
  fun `commit handled message`() {
    val handler = OmegaMessageHandler(consumer) { Unit.asSuccess() }
    prepareConsumer()
    consumer.schedulePollTask { sendMessage() }
    lateinit var committed: MutableMap<TopicPartition, OffsetAndMetadata>
    consumer.schedulePollTask { committed = consumer.committed(setOf(topicPartition)) }
    stopConsumer()
    handler.listen(topicPartition.topic())
    committed shouldHaveSize 1
    committed shouldHaveKey topicPartition
  }

  @Test
  fun `not commit when document metadata handler is failed`() {
    val handler = OmegaMessageHandler(consumer) { unknownError.asFailure() }
    prepareConsumer()
    consumer.schedulePollTask { sendMessage() }
    lateinit var committed: MutableMap<TopicPartition, OffsetAndMetadata>
    consumer.schedulePollTask { committed = consumer.committed(setOf(topicPartition)) }
    stopConsumer()
    handler.listen(topicPartition.topic())
    committed.shouldBeEmpty()
  }

  @Test
  fun `close consumer after shutdown`() {
    val handler = OmegaMessageHandler(consumer) { Unit.asSuccess() }
    prepareConsumer()
    consumer.schedulePollTask { sendMessage() }
    stopConsumer()
    handler.listen(topicPartition.topic())
    consumer.closed() shouldBe true
  }

  private fun sendMessage(message: String = "Lorem Ipsum") {
    consumer.addRecord(
      ConsumerRecord(
        topicPartition.topic(),
        topicPartition.partition(),
        0,
        "key",
        message
      )
    )
  }

  private fun prepareConsumer() {
    val offset = 0L
    consumer.updateBeginningOffsets(mapOf(topicPartition to offset))
    consumer.schedulePollTask { consumer.rebalance(mutableListOf(topicPartition)) }
  }

  private fun stopConsumer() {
    consumer.schedulePollTask { consumer.wakeup() }
  }
}

class DummyOmegaProcessor: OmegaProcessor {

  private lateinit var _receivedMessage: String
  val receivedMessage: String get() = _receivedMessage

  override fun process(message: String): Result<Unit, OmegaError> =
    Unit.asSuccess().also { _receivedMessage = message }
}

private val unknownError = OmegaError("unknown error")
