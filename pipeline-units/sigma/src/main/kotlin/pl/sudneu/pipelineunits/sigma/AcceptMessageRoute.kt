package pl.sudneu.pipelineunits.sigma

import dev.forkhandles.result4k.map
import dev.forkhandles.result4k.recover
import org.http4k.core.Body
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.INTERNAL_SERVER_ERROR
import org.http4k.format.Jackson.auto
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind

private val acceptMessageRequestBodyLens = Body.auto<MessageRequest>().toLens()

fun AcceptMessageRoute(handler: MessageHandler): RoutingHttpHandler =
  "/api/v1/messages" bind POST to { request ->
    handleException {
      acceptMessageRequestBodyLens(request)
        .let { body -> handler.handle(body.message) }
    }
      .map { Response(CREATED) }
      .recover { err -> Response(INTERNAL_SERVER_ERROR).body(err.reason) }
  }
