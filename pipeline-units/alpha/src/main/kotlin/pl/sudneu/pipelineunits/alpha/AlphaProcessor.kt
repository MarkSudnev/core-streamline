package pl.sudneu.pipelineunits.alpha

import org.apache.kafka.streams.processor.api.Processor
import org.apache.kafka.streams.processor.api.ProcessorContext
import org.apache.kafka.streams.processor.api.Record

class AlphaProcessor: Processor<String, String, String, String> {

  private lateinit var context: ProcessorContext<String, String>

  override fun init(context: ProcessorContext<String, String>) {
    super.init(context)
    this.context = context
  }

  override fun process(record: Record<String, String>) {
    context.forward(record)
  }
}
