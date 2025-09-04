package pl.sudneu.pipelineunits.omega

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.http4k.config.Environment
import org.http4k.config.EnvironmentKey
import org.http4k.lens.nonBlankString
import org.http4k.lens.of
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_BOOTSTRAP_SERVERS
import pl.sudneu.pipelineunits.omega.OmegaConfig.KAFKA_GROUP_ID
import java.io.File
import java.util.*

object OmegaConfig {
  val KAFKA_GROUP_ID by EnvironmentKey.nonBlankString().of().required()
  val KAFKA_TOPIC by EnvironmentKey.nonBlankString().of().required()
  val OUTPUT_FILE by EnvironmentKey.map(::File, File::getAbsolutePath).of().required()
}

internal fun Environment.toConsumerProperties(): Properties =
  Properties().also {
    it[ConsumerConfig.GROUP_ID_CONFIG] = this[KAFKA_GROUP_ID]
    it[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = this[KAFKA_BOOTSTRAP_SERVERS]
  }
