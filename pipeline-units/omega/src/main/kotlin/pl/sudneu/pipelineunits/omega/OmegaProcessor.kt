package pl.sudneu.pipelineunits.omega

import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.asSuccess
import java.io.File

fun interface OmegaProcessor {

  fun process(message: String): Result<Unit, OmegaError>

  companion object
}

fun HtmlCollectorProcessor(outputLocation: File, storage: MutableList<String>): OmegaProcessor {
  return OmegaProcessor { message: String ->
    storage.add(message)
    val template = ClassLoader.getSystemResource("omega-report.html").readText()
    val messages = storage.joinToString("") { "<p>$it</p>" }
    val report = template.replace("messages-placeholder", messages)
    outputLocation.writeText(report)
    Unit.asSuccess()
  }
}
