package pl.sudneu.pipelineunits.alpha

import org.apache.kafka.streams.processor.api.Processor
import org.apache.kafka.streams.processor.api.ProcessorContext
import org.apache.kafka.streams.processor.api.Record
import pl.sudneu.pipelineunits.shared.TimeProvider
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

class AlphaProcessor(
  private val timeProvider: TimeProvider = TimeProvider.Default
): Processor<String, String, String, String> {

  private lateinit var context: ProcessorContext<String, String>

  override fun init(context: ProcessorContext<String, String>) {
    super.init(context)
    this.context = context
  }

  override fun process(record: Record<String, String>?) {
    record?.let { rec ->
      val timestamp = timeProvider().format(ISO_LOCAL_DATE_TIME)
      rec.headers().add("alpha", timestamp.toByteArray())
      context.forward(rec)
    }
  }
}
