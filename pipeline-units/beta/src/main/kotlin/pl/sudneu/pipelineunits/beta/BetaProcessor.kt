package pl.sudneu.pipelineunits.beta

import org.apache.kafka.streams.processor.api.Processor
import org.apache.kafka.streams.processor.api.Record

class BetaProcessor: Processor<String, String, String, String> {

  override fun process(record: Record<String, String>?) {
    TODO("Not yet implemented")
  }
}
