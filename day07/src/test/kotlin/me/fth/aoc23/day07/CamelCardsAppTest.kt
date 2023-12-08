package me.fth.aoc23.day07

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

class CamelCardsAppTest : ShouldSpec({
  context("firstPart") {
    should("get a score of 6440") {
      withInput(loadResource("/hands-and-bids")) {
        firstPart() shouldBe 6440
      }
    }
  }
  context("secondPart") {
    should("get a score of 5905") {
      withInput(loadResource("/hands-and-bids")) {
        secondPart() shouldBe 5905
      }
    }
  }
  context("Hand.type") {
    should("be FiveOfAKind when there's the same card 5 times") {
      Hand(listOf(10, 10, 10, 10, 10)).type shouldBe HandType.FiveOfAKind
    }
    should("be FourOfAKind when there's the same card 4 times") {
      Hand(listOf(10, 10, 8, 10, 10)).type shouldBe HandType.FourOfAKind
    }
    should("be FullHouse when there's two cards 3 times and 2 times") {
      Hand(listOf(10, 10, 8, 8, 10)).type shouldBe HandType.FullHouse
    }
    should("be ThreeOfAKind when there's one same card 3 times and any two other cards") {
      Hand(listOf(10, 10, 8, 1, 10)).type shouldBe HandType.ThreeOfAKind
    }
    should("be TwoPairs when there's two cards 2 times and another one") {
      Hand(listOf(10, 10, 8, 1, 8)).type shouldBe HandType.TwoPairs
    }
    should("be OnePair when there's a same card 2 times") {
      Hand(listOf(11, 10, 8, 1, 8)).type shouldBe HandType.OnePair
    }
    should("be HighCard when there's five different cards") {
      Hand(listOf(11, 10, 14, 1, 8)).type shouldBe HandType.HighCard
    }
  }
  context("Hand.compareTo") {
    val firstHandSameType = Hand(listOf(10, 10, 14, 10, 10))
    val secondHandSameType = Hand(listOf(10, 10, 9, 10, 10))
    val firstHand = Hand(listOf(4, 4, 3, 2, 1))
    val secondHand = Hand(listOf(5, 4, 3, 2, 1))

    should("return 1 when this hand has a greater third card and of same type") {
      firstHandSameType.compareTo(secondHandSameType) shouldBe 1
    }
    should("return 1 when this hand has a greater type") {
      firstHand.compareTo(secondHand) shouldBe 1
    }
    should("return -1 when the other hand has a greater third card and of same type") {
      secondHandSameType.compareTo(firstHandSameType) shouldBe -1
    }
    should("return -1 when the other hand has a greater type") {
      secondHand.compareTo(firstHand) shouldBe -1
    }
    should("return 0 when both hands are identical and of same type") {
      secondHandSameType.compareTo(secondHandSameType) shouldBe 0
    }
    should("not return 0 when types are different") {
      firstHand.compareTo(Hand(listOf(5, 5, 4, 3, 2))) shouldNotBe 0
    }
  }
})
