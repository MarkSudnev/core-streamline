package pl.sudneu.pipelineunits.omega

import org.http4k.config.EnvironmentKey
import org.http4k.lens.nonBlankString
import org.http4k.lens.of

object OmegaConfig {
  val KAFKA_GROUP_ID by EnvironmentKey.nonBlankString().of().required()
  val OUTPUT_FILE by EnvironmentKey.nonBlankString().of().required()
}
