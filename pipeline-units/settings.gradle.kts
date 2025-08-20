rootProject.name = "pipeline-units"

include("config", "alpha", "beta")

dependencyResolutionManagement {
  repositories {
    mavenCentral()
  }
}

pluginManagement {
  includeBuild("build-logic")
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
