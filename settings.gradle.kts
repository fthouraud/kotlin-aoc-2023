pluginManagement {
  includeBuild("build-logic")
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "kotlin-aoc-2023"

include("common", "day01", "day02", "day03", "day04", "day05")
