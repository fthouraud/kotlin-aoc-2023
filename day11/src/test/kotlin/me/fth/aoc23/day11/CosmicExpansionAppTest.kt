package me.fth.aoc23.day11

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

class CosmicExpansionAppTest : ShouldSpec({
  context("firstPart") {
    should("found a sum of distances between galaxies of 374") {
      withInput(loadResource("/image")) {
        firstPart() shouldBe 374
      }
    }
  }
  context("secondPart") {
    should("found a sum of distances between galaxies of 8410") {
      withInput(loadResource("/image")) {
        secondPart() shouldBe 82_000_210
      }
    }
  }
})
