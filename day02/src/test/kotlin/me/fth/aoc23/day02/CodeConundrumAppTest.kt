package me.fth.aoc23.day02

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

class CodeConundrumAppTest : ShouldSpec({
  context("sumPossibleGameIds") {
    withInput(loadResource("/games-log")) {
      sumPossibleGameIds() shouldBe 8
    }
  }
  context("sumLowestScoresPowers") {
    withInput(loadResource("/games-log")) {
      sumHighestScoresPowers() shouldBe 2286
    }
  }
  context("GameScores.keepHighestCountOnly") {
    should("only keep 6 blue, 20 red, and 13 green") {
      listOf(
        GREEN_COLOR to 8,
        BLUE_COLOR to 6,
        RED_COLOR to 20,
        BLUE_COLOR to 5,
        RED_COLOR to 4,
        GREEN_COLOR to 13,
        GREEN_COLOR to 5,
        RED_COLOR to 1,
      ).keepHighestCountOnly() shouldBe
        mapOf(
          BLUE_COLOR to 6,
          RED_COLOR to 20,
          GREEN_COLOR to 13,
        )
    }
  }
  context("String.parseCubeScores") {
    should("return a list of cubes scores") {
      "3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green".parseCubeScores() shouldContainExactly
        listOf(
          BLUE_COLOR to 3,
          RED_COLOR to 4,
          RED_COLOR to 1,
          GREEN_COLOR to 2,
          BLUE_COLOR to 6,
          GREEN_COLOR to 2,
        )
    }
  }
  context("String.parseCubeScore") {
    should("return a pair of green and 12") {
      "12 green".parseCubeScore() should { (color, score) ->
        color shouldBe GREEN_COLOR
        score shouldBe 12
      }
    }
  }
  context("HighestScores.isPossible") {
    val scores: MutableMap<String, Int> = mutableMapOf()

    beforeEach {
      scores.clear()
      scores[RED_COLOR] = RED_LIMIT
      scores[GREEN_COLOR] = GREEN_LIMIT
      scores[BLUE_COLOR] = BLUE_LIMIT
    }

    should("be true when each score is equal to its own limit") {
      scores.isPossible() shouldBe true
    }
    should("be false when red is off limit") {
      scores[RED_COLOR] = RED_LIMIT + 1

      scores.isPossible() shouldBe false
    }
    should("be false when green is off limit") {
      scores[GREEN_COLOR] = GREEN_LIMIT + 1

      scores.isPossible() shouldBe false
    }
    should("be false when blue is off limit") {
      scores[BLUE_COLOR] = BLUE_LIMIT + 1

      scores.isPossible() shouldBe false
    }
    should("be false when all colors are off limit") {
      scores[RED_COLOR] = RED_LIMIT + 1
      scores[GREEN_COLOR] = GREEN_LIMIT + 1
      scores[BLUE_COLOR] = BLUE_LIMIT + 1

      scores.isPossible() shouldBe false
    }
  }
})
