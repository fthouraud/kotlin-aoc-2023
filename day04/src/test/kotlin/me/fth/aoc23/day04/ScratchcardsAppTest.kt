package me.fth.aoc23.day04

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

class ScratchcardsAppTest : ShouldSpec({
  context("sumCardScores") {
    should("get a score of 13") {
      withInput(loadResource("/cards")) {
        sumCardsScores() shouldBe 13
      }
    }
    should("get 30 as the total number of played cards") {
      withInput(loadResource("/cards")) {
        countTotalPlayedCards() shouldBe 30
      }
    }
  }
  context("String.parseCard") {
    should("get 5 winning numbers and 8 own numbers") {
      val card = "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1"

      card.parseCard() should { (winningNumbers, ownNumbers) ->
        winningNumbers shouldContainExactly setOf("1", "21", "53", "59", "44")
        ownNumbers shouldContainExactly setOf("69", "82", "63", "72", "16", "21", "14", "1")
      }
    }
  }
  context("Card.winningNumbers") {
    should("return a list of common numbers") {
      val winningNumbers = setOf("1", "2", "3")
      val ownedNumbers = setOf("2", "3", "4")

      (winningNumbers to ownedNumbers).winningNumbers shouldBe setOf("2", "3")
    }
  }
  context("Card.score") {
    should("return 2^6 when it owns 6 winning numbers") {
      val winningNumbers = setOf("1", "2", "3", "4", "5", "6", "7")
      val ownedNumbers = setOf("2", "3", "4", "5", "6", "7")

      (winningNumbers to ownedNumbers).score shouldBe 32
    }
    should("return 2^1 when it owns 1 winning numbers") {
      val winningNumbers = setOf("1", "2", "3", "4", "5")
      val ownedNumbers = setOf("4")

      (winningNumbers to ownedNumbers).score shouldBe 1
    }
    should("return 0 when it owns no winning number") {
      val winningNumbers = setOf("1", "2", "3", "4", "5")
      val ownedNumbers = emptySet<String>()

      (winningNumbers to ownedNumbers).score shouldBe 0
    }
  }
})
