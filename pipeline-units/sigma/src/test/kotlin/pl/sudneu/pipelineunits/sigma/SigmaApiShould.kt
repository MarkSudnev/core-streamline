package pl.sudneu.pipelineunits.sigma

import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.kotest.shouldNotHaveStatus
import org.junit.jupiter.api.Test

class SigmaApiShould {

  private val api = SigmaApi()

  @Test
  fun `have route to accept message`() {
    api(Request(POST, "/api/v1/messages").body("")) shouldNotHaveStatus NOT_FOUND
  }
}
