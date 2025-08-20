plugins {
  id("pipeline-units-common-conventions")
  application
}

dependencies {
  implementation(project(":config"))
}

application {
  mainClass = "pl.sudneu.pipelineunits.beta.ServiceKt"
}
