package pl.sudneu.pipelineunits.sigma

import org.http4k.config.EnvironmentKey
import org.http4k.lens.nonBlankString
import org.http4k.lens.of
import org.http4k.lens.port

object SigmaConfig {
  val KAFKA_TOPIC by EnvironmentKey.nonBlankString().of().required()
  val PORT by EnvironmentKey.port().of().required()
}
