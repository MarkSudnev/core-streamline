package pl.sudneu.pipelineunits.sigma

import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.routing.routes

fun SigmaApi(messageHandler: MessageHandler): HttpHandler {
  val routingHttpHandler = routes(
    AcceptMessageRoute(messageHandler)
  )
  return ServerFilters.CatchLensFailure.then(routingHttpHandler)
}
