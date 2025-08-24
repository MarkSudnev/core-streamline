plugins {
  id("pipeline-units-common-conventions")
  application
}

dependencies {
  implementation(project(":config"))
  implementation(libs.kafka.clients)
}

application {
  mainClass = "pl.sudneu.pipelineunits.omega.ServiceKt"
}
