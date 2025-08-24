package pl.sudneu.pipelineunits.omega

import io.kotest.matchers.shouldBe
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.junit.jupiter.api.Test
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_BOOTSTRAP_SERVERS
import pl.sudneu.pipelineunits.omega.OmegaConfig.KAFKA_GROUP_ID

class OmegaConfigShould {

  @Test
  fun `be converted to properties`() {
    val properties = testEnvironment.toConsumerProperties()

    with(properties) {
      this[ConsumerConfig.GROUP_ID_CONFIG] shouldBe testEnvironment[KAFKA_GROUP_ID]
      this[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] shouldBe testEnvironment[KAFKA_BOOTSTRAP_SERVERS]
    }
  }
}
