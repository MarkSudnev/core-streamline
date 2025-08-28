package pl.sudneu.pipelineunits.omega

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecords
import java.time.Duration

class OmegaMessageHandler(
  val consumer: Consumer<String, String>,
  val processor: OmegaProcessor
) {

  fun listen(topicName: String) {

  }

  private fun consume(duration: Duration) {

  }

  private fun handle(records: ConsumerRecords<String, String>) {

  }

  private fun stop() {
    consumer.wakeup()
  }
}
