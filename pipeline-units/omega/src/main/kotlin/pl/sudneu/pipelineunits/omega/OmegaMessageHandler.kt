package pl.sudneu.pipelineunits.omega

import dev.forkhandles.result4k.peek
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.common.errors.WakeupException
import java.time.Duration

class OmegaMessageHandler(
  private val consumer: Consumer<String, String>,
  private val processor: OmegaProcessor
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
      .map { record -> record.format() }
      .forEach { v -> processor.process(v).peek { consumer.commitSync() } }
  }

  private fun stop() {
    consumer.wakeup()
  }
}

fun ConsumerRecord<String, String>.format(): String {
  val headers = headers().joinToString("") { header ->
    "[${header.key()}::${String(header.value())}]"
  }
  return "$headers ${value()}"
}
