package pl.sudneu.pipelineunits.omega

import dev.forkhandles.result4k.kotest.shouldBeFailure
import dev.forkhandles.result4k.kotest.shouldBeSuccess
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

class HtmlCollectorProcessorShould {

  @TempDir private lateinit var outputDirectory: Path
  private val outputFile: File
    get() = outputDirectory.resolve("omega-report.html").toFile()

  @BeforeEach
  fun setup() {
    if (!outputFile.exists()) {
      outputFile.createNewFile()
    }
  }

  @AfterEach
  fun teardown() {
    if (outputFile.exists()) {
      outputFile.delete()
    }
  }

  @Test
  fun `produce simple html report`() {
    val processor = HtmlCollectorProcessor(outputFile, mutableListOf())
    processor.process("Lorem Ipsum").shouldBeSuccess()

    outputFile.readText() shouldBe expectedSingleMessageOutput
  }

  @Test
  fun `collect messages`() {
    val processor = HtmlCollectorProcessor(outputFile, mutableListOf())
    processor.process("First message").shouldBeSuccess()
    processor.process("Second message").shouldBeSuccess()

    outputFile.readText() shouldBe expectedMultiMessageOutput
  }

  @Test
  fun `return failure when template is missing`() {
    val processor = HtmlCollectorProcessor(
      outputFile,
      mutableListOf(),
      "missing-file.html"
    )
    processor.process("Lorem Ipsum") shouldBeFailure OmegaError("'missing-file.html' is not found")
  }

  @Test
  fun `return failure when output location is not accessible`() {
    val processor = HtmlCollectorProcessor(
      File("/path/to/nowhere"),
      mutableListOf()
    )
    processor.process("Lorem Ipsum") shouldBeFailure OmegaError("'/path/to/nowhere' is not accessible")
  }
}

private val expectedSingleMessageOutput = """
  <!DOCTYPE html>
  <html lang="en">
  <head>
    <meta charset="UTF-8">
    <title>Collected Message</title>
  </head>
  <body>
    <div>
      <h4>Collected messages</h4>
      <div>
        <p>Lorem Ipsum</p>
      </div>
    </div>
  </body>
  </html>
  
  """.trimIndent()

private val expectedMultiMessageOutput = """
  <!DOCTYPE html>
  <html lang="en">
  <head>
    <meta charset="UTF-8">
    <title>Collected Message</title>
  </head>
  <body>
    <div>
      <h4>Collected messages</h4>
      <div>
        <p>First message</p><p>Second message</p>
      </div>
    </div>
  </body>
  </html>
  
  """.trimIndent()
