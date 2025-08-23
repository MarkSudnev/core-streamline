plugins {
  id("pipeline-stream-units-common-conventions")
  application
}

dependencies {
  implementation(project(":config"))
  implementation(project(":shared"))
}

application {
  mainClass = "pl.sudneu.pipelineunits.beta.ServiceKt"
}
