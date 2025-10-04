plugins {
  id("pipeline-units-common-conventions")
  application
}

dependencies {
  implementation(project(":config"))
  implementation(libs.http4k.bom)
  implementation(libs.http4k.core)
  implementation(libs.http4k.client.okhttp)
  implementation(libs.http4k.jackson)
  implementation(libs.http4k.undertow)
  testImplementation(libs.http4k.kotest)
}

application {
  mainClass = "pl.sudneu.pipelineunits.sigma.ServiceKt"
}
