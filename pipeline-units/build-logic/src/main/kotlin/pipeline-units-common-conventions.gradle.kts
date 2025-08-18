import org.gradle.accessors.dm.LibrariesForLibs

plugins {
  kotlin("jvm")
}

val libs = the<LibrariesForLibs>()

dependencies {
  implementation(libs.result4k)
  implementation(libs.http4k.config)
  implementation(libs.kafka.clients)
  implementation(libs.kafka.streams)
  implementation(libs.kafka.connect.json)
  implementation(libs.slf4j.simple)

  testImplementation(libs.kotlin.test)
  testImplementation(libs.kotest.assertions)
  testImplementation(libs.result4k.kotest)
  testImplementation(libs.junit.jupiter.engine)
  testImplementation(libs.junit.jupiter.params)
  testImplementation(libs.fabrikate4k)
  testImplementation(libs.mockk)
}

val jvmVersion: String by project

java.toolchain.languageVersion.set(JavaLanguageVersion.of(jvmVersion))
kotlin.jvmToolchain(jvmVersion.toInt())

tasks.named<Test>("test") {
  useJUnitPlatform()
}
