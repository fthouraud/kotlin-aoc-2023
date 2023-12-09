package me.fth.aoc23.day09

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

class MirageMaintenanceAppTest : ShouldSpec({
  context("firstPart") {
    should("have a sum of 114") {
      withInput(loadResource("/oasis-report")) {
        firstPart() shouldBe 114
      }
    }
  }
  context("secondPart") {
    should("have a sum of 2") {
      withInput(loadResource("/oasis-report")) {
        secondPart() shouldBe 2
      }
    }
  }
})
