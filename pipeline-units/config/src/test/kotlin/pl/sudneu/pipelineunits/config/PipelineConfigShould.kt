package pl.sudneu.pipelineunits.config

import io.kotest.matchers.shouldBe
import org.apache.kafka.streams.StreamsConfig
import org.junit.jupiter.api.Test
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_APPLICATION_ID
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_BOOTSTRAP_SERVERS
import java.util.*

class PipelineConfigShould {

  @Test
  fun `be converted to properties`() {
    val properties: Properties = testEnvironment.toProperties()

    with(properties) {
      properties[StreamsConfig.APPLICATION_ID_CONFIG] shouldBe testEnvironment[KAFKA_APPLICATION_ID]
      properties[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] shouldBe testEnvironment[KAFKA_BOOTSTRAP_SERVERS]
    }
  }
}
