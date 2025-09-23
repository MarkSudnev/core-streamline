package pl.sudneu.pipelineunits.sigma

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED

fun SigmaApi(): HttpHandler = { Response(CREATED) }
