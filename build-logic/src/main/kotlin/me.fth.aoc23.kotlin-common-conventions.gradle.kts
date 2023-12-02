import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.jetbrains.kotlin.jvm")
  id("io.github.detekt.gradle.compiler-plugin")
}

repositories {
  mavenCentral()
}

dependencies {
  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.3")
}

tasks {
  withType<Test> {
    useJUnitPlatform()
  }
  withType<KotlinCompile>().configureEach {
    compilerOptions {
      allWarningsAsErrors = true
      freeCompilerArgs = listOf("-Xcontext-receivers")
    }
  }
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
  }
}

detekt {
  allRules = true
  config.setFrom("$rootDir/build-logic/detekt.yaml")
  autoCorrect = true
}
