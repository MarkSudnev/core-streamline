package pl.sudneu.pipelineunits.alpha

import dev.forkhandles.fabrikate.Fabrikate
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.apache.kafka.common.serialization.Serdes.StringSerde
import org.apache.kafka.streams.TopologyTestDriver
import org.apache.kafka.streams.test.TestRecord
import org.junit.jupiter.api.Test
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_TOPIC_IN
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_TOPIC_OUT
import pl.sudneu.pipelineunits.config.toStreamProperties

class ServiceTest {

  @Test
  fun `should stream record from input to output topic`() {
    val key: String = Fabrikate().random()
    val value: String = Fabrikate().random()
    val topology = AlphaTopology(localEnvironment)
    val testStream = TopologyTestDriver(topology, localEnvironment.toStreamProperties())
    val inputTopic = testStream.createInputTopic(
      localEnvironment[KAFKA_TOPIC_IN],
      StringSerde().serializer(),
      StringSerde().serializer()
    )
    val outputTopic = testStream.createOutputTopic(
      localEnvironment[KAFKA_TOPIC_OUT],
      StringSerde().deserializer(),
      StringSerde().deserializer()
    )
    inputTopic.pipeInput(TestRecord(key, value))

    val record = outputTopic.readRecord()
    record.key shouldBe key
    record.value shouldBe value

    val header = record.headers.single()
    header.key() shouldBe "alpha"
    String(header.value()).shouldNotBeNull()
  }
}
