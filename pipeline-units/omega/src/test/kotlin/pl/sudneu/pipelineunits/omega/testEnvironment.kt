package pl.sudneu.pipelineunits.omega

import org.http4k.config.Environment
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_BOOTSTRAP_SERVERS
import pl.sudneu.pipelineunits.omega.OmegaConfig.KAFKA_GROUP_ID
import pl.sudneu.pipelineunits.omega.OmegaConfig.KAFKA_TOPIC

internal val testEnvironment = Environment.defaults(
  KAFKA_GROUP_ID of "omega",
  KAFKA_BOOTSTRAP_SERVERS of listOf("localhost:9092"),
  KAFKA_TOPIC of "test-topic"
)
