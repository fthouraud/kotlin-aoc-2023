package me.fth.aoc23.day08

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

class HauntedWastelandAppTest : ShouldSpec({
  context("firstPart") {
    should("find end in 2 steps") {
      withInput(loadResource("/maps-pouch")) {
        firstPart() shouldBe 2
      }
    }
  }
  context("secondPart") {
    should("find end in 6 steps") {
      withInput(loadResource("/maps-pouch-simultaneous")) {
        secondPart() shouldBe 6.toBigInteger()
      }
    }
  }
})
