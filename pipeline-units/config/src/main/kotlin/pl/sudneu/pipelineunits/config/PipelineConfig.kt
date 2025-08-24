package pl.sudneu.pipelineunits.config

import org.apache.kafka.streams.StreamsConfig
import org.http4k.config.Environment
import org.http4k.config.EnvironmentKey
import org.http4k.lens.csv
import org.http4k.lens.nonBlankString
import org.http4k.lens.of
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_APPLICATION_ID
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_BOOTSTRAP_SERVERS
import java.util.*

object PipelineConfig {
  val KAFKA_BOOTSTRAP_SERVERS by EnvironmentKey.csv(",").of().required()
  val KAFKA_APPLICATION_ID by EnvironmentKey.nonBlankString().of().required()
  val KAFKA_TOPIC_IN by EnvironmentKey.nonBlankString().of().required()
  val KAFKA_TOPIC_OUT by EnvironmentKey.nonBlankString().of().required()
}

fun Environment.toStreamProperties() =
  Properties().also { props ->
    props[StreamsConfig.APPLICATION_ID_CONFIG] = this[KAFKA_APPLICATION_ID]
    props[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = this[KAFKA_BOOTSTRAP_SERVERS]
  }
