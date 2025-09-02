package pl.sudneu.pipelineunits.omega

import dev.forkhandles.result4k.allValues
import dev.forkhandles.result4k.mapAllValues
import dev.forkhandles.result4k.peek
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.common.errors.WakeupException
import java.time.Duration

class OmegaMessageHandler(
  val consumer: Consumer<String, String>,
  val processor: OmegaProcessor
) {

  fun listen(topicName: String) {
    consumer.subscribe(setOf(topicName))
    Runtime.getRuntime().addShutdownHook(Thread { stop() })
    try {
      consume(Duration.ofMillis(100))
    } catch (_: WakeupException) {
    } catch (e: Exception) {

    } finally {
      consumer.close()
    }
  }

  private fun consume(duration: Duration) {
    while (true) {
      handle(consumer.poll(duration))
    }
  }

  private fun handle(records: ConsumerRecords<String, String>) {
    records
      .map { it.value() }
      .mapAllValues { v -> processor.process(v) }
      .peek {
        if (it.isNotEmpty()) {
          consumer.commitSync()
        }
      }
  }

  private fun stop() {
    consumer.wakeup()
  }
}
