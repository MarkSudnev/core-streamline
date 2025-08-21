package pl.sudneu.pipelineunits.beta

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.apache.kafka.common.header.Headers
import org.apache.kafka.streams.processor.api.MockProcessorContext
import org.apache.kafka.streams.processor.api.Record
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import pl.sudneu.pipelineunits.shared.TimeProvider
import java.time.LocalDateTime

class BetaProcessorShould {

  private val context = MockProcessorContext<String, String>()
  private val timeProvider = TimeProvider { LocalDateTime.parse("2025-01-01T09:00:00") }

  @AfterEach
  fun teardown() {
    context.resetForwards()
  }

  @Test
  fun `process record`() {
    val processor = BetaProcessor(timeProvider).apply { init(context) }
    val timestamp = System.currentTimeMillis()
    val record = Record("Key", "Value", timestamp)
    processor.process(record)

    with(context.forwarded().single().record()) {
      key() shouldBe "Key"
      value() shouldBe "Value"
    }
  }

  @Test
  fun `keep existing headers`() {
    val processor = BetaProcessor(timeProvider).apply { init(context) }
    val timestamp = System.currentTimeMillis()
    val record = Record("Key", "Value", timestamp)
    record.headers().add("existing-header", "Hello".toByteArray())
    processor.process(record)

    val headers = context.forwarded().single().record().headers().toMap()
    headers.keys shouldContain "existing-header"
  }

  @Test
  fun `add headers`() {
    val processor = BetaProcessor(timeProvider).apply { init(context) }
    val timestamp = System.currentTimeMillis()
    val record = Record("Key", "Value", timestamp)
    processor.process(record)

    val header = context.forwarded().single().record().headers().single()
    header.key() shouldBe "beta"
    String(header.value()) shouldBe "2025-01-01T09:00:00"
  }

  @Test
  fun `not process when record is missing`() {
    val processor = BetaProcessor(timeProvider).apply { init(context) }
    processor.process(null)

    context.forwarded().shouldBeEmpty()
  }
}

fun Headers.toMap(): Map<String, String> =
  associate { header -> header.key() to String(header.value()) }
