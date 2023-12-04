plugins {
  id("me.fth.aoc23.kotlin-application-conventions")
}

application {
  mainClass.set("me.fth.aoc23.day04.ScratchcardsAppKt")
}

dependencies {
  implementation(project(":common"))

  testImplementation(libs.bundles.kotest)
}
