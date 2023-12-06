package me.fth.aoc23.day06

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

class WaitForItAppTest : ShouldSpec({
  context("multiplicationOfPossibleWinningMoves") {
    should("get a value of 288") {
      withInput(loadResource("/race-statistics")) {
        val times = sequenceOf(7L, 15, 30)
        val distances = sequenceOf(9L, 40, 200)

        multiplicationOfPossibleWinningMoves(times, distances) shouldBe 288
      }
    }
  }
  context("countWinningMoves") {
    should("get 71503 possible winning moves") {
      countWinningMoves(71_530, 940_200) shouldBe 71_503
    }
  }
})
