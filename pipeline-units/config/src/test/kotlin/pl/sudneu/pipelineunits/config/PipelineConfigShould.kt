package pl.sudneu.pipelineunits.config

import io.kotest.matchers.shouldBe
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.junit.jupiter.api.Test
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_BOOTSTRAP_SERVERS
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_GROUP_ID
import java.util.*

class PipelineConfigShould {

  @Test
  fun `be converted to properties`() {
    val properties: Properties = testEnvironment.toProperties()

    with(properties) {
      properties[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] shouldBe testEnvironment[KAFKA_BOOTSTRAP_SERVERS]
      properties[ConsumerConfig.GROUP_ID_CONFIG] shouldBe testEnvironment[KAFKA_GROUP_ID]
    }
  }
}
