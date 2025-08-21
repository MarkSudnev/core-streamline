package pl.sudneu.pipelineunits.shared

import java.time.LocalDateTime

fun interface TimeProvider {
  operator fun invoke(): LocalDateTime

  object Default: TimeProvider {
    override fun invoke(): LocalDateTime =
      LocalDateTime.now()
  }

  companion object
}


