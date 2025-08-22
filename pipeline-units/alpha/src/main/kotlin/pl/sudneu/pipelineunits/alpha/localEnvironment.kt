package pl.sudneu.pipelineunits.alpha

import org.http4k.config.Environment
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_APPLICATION_ID
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_BOOTSTRAP_SERVERS
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_TOPIC_IN
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_TOPIC_OUT

internal val localEnvironment = Environment.defaults(
  KAFKA_BOOTSTRAP_SERVERS of listOf("localhost:29092"),
  KAFKA_APPLICATION_ID of "alpha-unit",
  KAFKA_TOPIC_IN of "first",
  KAFKA_TOPIC_OUT of "second"
)
