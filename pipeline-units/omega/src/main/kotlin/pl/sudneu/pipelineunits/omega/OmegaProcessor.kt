package pl.sudneu.pipelineunits.omega

import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.flatMap
import dev.forkhandles.result4k.map
import dev.forkhandles.result4k.mapFailure
import dev.forkhandles.result4k.resultFrom
import java.io.File

fun interface OmegaProcessor {

  fun process(message: String): Result<Unit, OmegaError>

  companion object
}

fun HtmlCollectorProcessor(
  outputLocation: File,
  storage: MutableList<String>,
  templatePath: String = "omega-report.html"
): OmegaProcessor =
  OmegaProcessor { message: String ->
    storage.add(message)
    val messages = storage.joinToString("") { "<p>$it</p>" }
    readTemplate(templatePath)
      .map { template -> template.replace("messages-placeholder", messages) }
      .flatMap { report -> outputLocation.writeReport(report) }
  }

private fun readTemplate(templatePath: String): Result<String, OmegaError> = resultFrom {
  ClassLoader.getSystemResource(templatePath).readText()
}.mapFailure { e -> OmegaError("'$templatePath' is not found") }

private fun File.writeReport(report: String): Result<Unit, OmegaError> = resultFrom {
  writeText(report)
}.mapFailure { e -> OmegaError("'${this.absolutePath}' is not accessible") }


fun OmegaProcessor.Companion.collectAsHtmlReport(
  outputLocation: File,
  storage: MutableList<String>
) = HtmlCollectorProcessor(outputLocation, storage)
