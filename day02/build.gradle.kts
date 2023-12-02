plugins {
  id("me.fth.aoc23.kotlin-application-conventions")
}

application {
  mainClass.set("me.fth.aoc23.day02.CubeConundrumAppKt")
}

dependencies {
  implementation(project(":common"))

  testImplementation(libs.bundles.kotest)
}