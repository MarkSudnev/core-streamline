package pl.sudneu.pipelineunits.beta

import org.http4k.config.Environment
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_APPLICATION_ID
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_BOOTSTRAP_SERVERS
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_TOPIC_IN
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_TOPIC_OUT

internal val localEnvironment = Environment.defaults(
  KAFKA_BOOTSTRAP_SERVERS of listOf("localhost:29092"),
  KAFKA_APPLICATION_ID of "beta-unit",
  KAFKA_TOPIC_IN of "second",
  KAFKA_TOPIC_OUT of "third"
)
