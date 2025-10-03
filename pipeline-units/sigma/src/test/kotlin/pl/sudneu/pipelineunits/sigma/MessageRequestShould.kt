package pl.sudneu.pipelineunits.sigma

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import org.junit.jupiter.api.Test

class MessageRequestShould {

  @Test
  fun `accept non-blank string`() {
    shouldNotThrowAny { MessageRequest("text") }
  }

  @Test
  fun `throw when empty string passed`() {
    shouldThrow<IllegalArgumentException> { MessageRequest("") }
  }

  @Test
  fun `throw when blank string passed`() {
    shouldThrow<IllegalArgumentException> { MessageRequest("   ") }
  }
}
