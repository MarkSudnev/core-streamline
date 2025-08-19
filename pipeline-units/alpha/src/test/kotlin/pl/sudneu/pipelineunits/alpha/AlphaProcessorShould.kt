package pl.sudneu.pipelineunits.alpha


import io.kotest.matchers.shouldBe
import org.apache.kafka.streams.processor.api.MockProcessorContext
import org.apache.kafka.streams.processor.api.Record
import org.junit.jupiter.api.Test

class AlphaProcessorShould {

  private val context = MockProcessorContext<String, String>()

  @Test
  fun `process record`() {
    val processor = AlphaProcessor().apply { init(context) }
    val timestamp = System.currentTimeMillis()
    val record = Record("Key", "Value", timestamp)
    processor.process(record)

    with(context.forwarded().single().record()) {
      key() shouldBe "Key"
      value() shouldBe "Value"
    }
  }
}


