package me.fth.aoc23.day05

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

class SeedFertilizerAppTest : ShouldSpec({
  context("findLowestLocationNumber") {
    should("find 35 as the lowest location for seed 13") {
      withInput(loadResource("/almanac")) {
        Almanac.parse(content).findLowestSeedDestination() should { (seed, destination) ->
          seed shouldBe 13
          destination shouldBe 35
        }
      }
    }
    should("find 46 as the lowest location for seed 82") {
      withInput(loadResource("/almanac")) {
        Almanac.parse(content).findLowestSeedDestinationWithRangeOfSeeds() should { (seed, destination) ->
          seed shouldBe 82
          destination shouldBe 46
        }
      }
    }
  }
})
