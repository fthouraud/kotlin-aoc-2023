dependencyResolutionManagement {
  versionCatalogs {
    create("libs") { from(files("../gradle/libs.versions.toml")) }
  }
}

rootProject.name = "kotlin-aoc-2023-build-logic"
