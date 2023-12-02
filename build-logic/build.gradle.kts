plugins {
  `kotlin-dsl`
}

repositories {
  gradlePluginPortal()
}

dependencies {
  implementation(libs.kotlin.gradle.plugin)
  implementation(libs.detekt.compiler.plugin)
  implementation(libs.detekt.formatting)
}
