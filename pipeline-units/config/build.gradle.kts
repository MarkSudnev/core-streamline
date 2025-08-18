plugins {
  id("pipeline-units-common-conventions")
}

dependencies {
  testImplementation(libs.kafka.clients)
}
