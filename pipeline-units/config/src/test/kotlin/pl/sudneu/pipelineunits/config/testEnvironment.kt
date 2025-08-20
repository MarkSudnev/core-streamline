package pl.sudneu.pipelineunits.config

import org.http4k.config.Environment
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_BOOTSTRAP_SERVERS
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_GROUP_ID
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_TOPIC_IN

val testEnvironment = Environment.defaults(
  KAFKA_BOOTSTRAP_SERVERS of listOf("localhost:2092"),
  KAFKA_GROUP_ID of "group-id",
  KAFKA_TOPIC_IN of "metadata-topic",
)
