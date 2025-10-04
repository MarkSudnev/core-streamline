package pl.sudneu.pipelineunits.sigma

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result

data class SigmaError(val reason: String)

fun <T> handleException(block: (Unit) -> Result<T, SigmaError>): Result<T, SigmaError> {
  return try {
    block(Unit)
  } catch (e: Throwable) {
    Failure(SigmaError(e.toSigmaMessage()))
  }
}

fun Throwable.toSigmaMessage(): String = "${this::class.simpleName}: ${this.message}"
