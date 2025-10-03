package pl.sudneu.pipelineunits.sigma

import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.asFailure
import dev.forkhandles.result4k.asSuccess
import io.kotest.matchers.shouldBe
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.INTERNAL_SERVER_ERROR
import org.http4k.filter.ServerFilters
import org.http4k.kotest.shouldHaveBody
import org.http4k.kotest.shouldHaveStatus
import org.junit.jupiter.api.Test

class AcceptMessageRouteShould {

  private val exampleRequestBody = """{"message": "one"}"""

  @Test
  fun `return CREATED status when message successfully accepted`() {
    val route = ServerFilters.CatchLensFailure(AcceptMessageRoute { Unit.asSuccess() })
    route(Request(POST, "/api/v1/messages").body(exampleRequestBody)) shouldHaveStatus CREATED
  }

  @Test
  fun `return BAD_REQUEST when message is missed`() {
    val route = ServerFilters.CatchLensFailure(AcceptMessageRoute { Unit.asSuccess() })
    route(Request(POST, "/api/v1/messages").body("""{"message": ""}""")) shouldHaveStatus BAD_REQUEST
  }

  @Test
  fun `call message handler`() {
    val handler = DummyMessageHandler()
    val route = ServerFilters.CatchLensFailure(AcceptMessageRoute(handler))
    route(Request(POST, "/api/v1/messages").body(exampleRequestBody))
    handler.wasCalled shouldBe true
  }

  @Test
  fun `return INTERNAL_SERVER_ERROR when handler is failed`() {
    val handler = MessageHandler { SigmaError("some-error").asFailure() }
    val route = ServerFilters.CatchLensFailure(AcceptMessageRoute(handler))
    val response = route(Request(POST, "/api/v1/messages").body(exampleRequestBody))
    response shouldHaveStatus INTERNAL_SERVER_ERROR
    response shouldHaveBody "some-error"
  }

  @Test
  fun `return INTERNAL_SERVER_ERROR when handler throws exception`() {
    val handler = MessageHandler { error("some-error") }
    val route = ServerFilters.CatchLensFailure(AcceptMessageRoute(handler))
    val response = route(Request(POST, "/api/v1/messages").body(exampleRequestBody))
    response shouldHaveStatus INTERNAL_SERVER_ERROR
    response shouldHaveBody "IllegalStateException: some-error"
  }


}

private class DummyMessageHandler: MessageHandler {

  private var _wasCalled = false
  val wasCalled get() = _wasCalled

  override fun handle(message: String): Result<Unit, SigmaError> {
    _wasCalled = true
    return Unit.asSuccess()
  }
}
