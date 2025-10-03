package pl.sudneu.pipelineunits.sigma

import org.http4k.core.HttpHandler
import org.http4k.routing.routes

fun SigmaApi(messageHandler: MessageHandler): HttpHandler = routes(
  AcceptMessageRoute(messageHandler)
)
