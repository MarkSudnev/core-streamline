package pl.sudneu.pipelineunits.alpha

import org.apache.kafka.streams.processor.api.Processor
import org.apache.kafka.streams.processor.api.Record

class AlphaProcessor: Processor<String, String, String, String> {

  override fun process(record: Record<String, String>) {
    TODO("Not yet implemented")
  }
}
