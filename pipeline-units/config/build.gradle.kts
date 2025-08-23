plugins {
  id("pipeline-stream-units-common-conventions")
}

dependencies {
  testImplementation(libs.kafka.clients)
}
