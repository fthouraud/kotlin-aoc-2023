package me.fth.aoc23.day10

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

class PipeMazeAppTest : ShouldSpec({
  context("firstPart") {
    should("be 4") {
      withInput(loadResource("/simple-pipes-sketch")) {
        firstPart(Direction.South) shouldBe 4
      }
    }
    should("be 8") {
      withInput(loadResource("/pipes-sketch")) {
        firstPart(Direction.South) shouldBe 8
      }
    }
  }
  context("secondPart") {
    should("count 4 tile inside the loop") {
      withInput(loadResource("/pipes-sketch-part-2")) {
        secondPart(Direction.South) shouldBe 4
      }
    }
    should("count 8 tile inside the loop") {
      withInput(loadResource("/larger-pipes-sketch-part-2")) {
        secondPart(Direction.South) shouldBe 8
      }
    }
    should("count 10 tile inside the loop") {
      withInput(loadResource("/larger-pipes-sketch-with-junk-pipes")) {
        secondPart(Direction.South) shouldBe 10
      }
    }
  }
  context("Int.isAdjacentTo") {
    should("be true for 0 when compared to 1") {
      (0 isAdjacentTo 1).shouldBeTrue()
    }
    should("be true for 2 when compared to 1") {
      (2 isAdjacentTo 1).shouldBeTrue()
    }
    should("be false for 1 when compared to 1") {
      (1 isAdjacentTo 1).shouldBeFalse()
    }
  }
})
