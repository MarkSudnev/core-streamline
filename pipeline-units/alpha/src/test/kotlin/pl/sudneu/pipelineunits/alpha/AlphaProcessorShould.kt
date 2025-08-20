package pl.sudneu.pipelineunits.alpha


import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.apache.kafka.streams.processor.api.MockProcessorContext
import org.apache.kafka.streams.processor.api.Record
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class AlphaProcessorShould {

  private val context = MockProcessorContext<String, String>()
  private val timeProvider: TimeProvider = { LocalDateTime.parse("2025-01-01T09:00:00") }

  @AfterEach
  fun teardown() {
    context.resetForwards()
  }

  @Test
  fun `process record`() {
    val processor = AlphaProcessor(timeProvider).apply { init(context) }
    val timestamp = System.currentTimeMillis()
    val record = Record("Key", "Value", timestamp)
    processor.process(record)

    with(context.forwarded().single().record()) {
      key() shouldBe "Key"
      value() shouldBe "Value"
    }
  }

  @Test
  fun `add headers`() {
    val processor = AlphaProcessor(timeProvider).apply { init(context) }
    val timestamp = System.currentTimeMillis()
    val record = Record("Key", "Value", timestamp)
    processor.process(record)

    val header = context.forwarded().single().record().headers().single()
    header.key() shouldBe "alpha"
    String(header.value()) shouldBe "2025-01-01T09:00:00"
  }

  @Test
  fun `not process when record is missing`() {
    val processor = AlphaProcessor(timeProvider).apply { init(context) }
    processor.process(null)

    context.forwarded().shouldBeEmpty()
  }
}


