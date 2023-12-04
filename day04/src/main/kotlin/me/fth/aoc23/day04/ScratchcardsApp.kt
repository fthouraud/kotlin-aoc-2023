package me.fth.aoc23.day04

import me.fth.aoc23.common.InputContext
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput
import kotlin.math.pow

typealias Card = Pair<Set<String>, Set<String>>

fun main() {
  withInput(object {}.loadResource("/cards")) {
    println("Sum of cards score: ${sumCardsScores()}")
    println("Total of played cards is: ${countTotalPlayedCards()}")
  }
}

context(InputContext)
fun sumCardsScores() = lines().sumOf { it.parseCard().score }

context(InputContext)
fun countTotalPlayedCards(): Int {
  val cards = lines().map { it.parseCard() }
  val cachedResults = mutableMapOf<Int, Set<Int>>()
  val cardsToPlay = lines().indices.toMutableList()
  val cardCopies = mutableListOf<Int>()
  var cardsCount = 0
  do {
    cardsToPlay.forEach { cardIndex ->
      cardsCount += 1
      cachedResults.computeIfAbsent(cardIndex) { _ ->
        cards[cardIndex].winningNumbers.mapIndexedNotNull { index, _ ->
          (index + 1 + cardIndex).takeIf { nextCardIndex -> nextCardIndex <= cards.lastIndex }
        }.toSet()
      }
      cardCopies += cachedResults[cardIndex].orEmpty()
    }
    cardsToPlay.clear()
    cardsToPlay += cardCopies
    cardCopies.clear()
  } while (cardsToPlay.isNotEmpty())
  return cardsCount
}

val CARD_PARSING_REGEX = "Card [ 0-9]+: (?<winning>[0-9 ]+)+\\|(?<own>[0-9 ]+)+".toRegex()
val SPACES_REGEX = "\\s+".toRegex()

fun String.parseCard() =
  CARD_PARSING_REGEX.findAll(this)
    .first()
    .let { it.parseCardNumbers("winning") to it.parseCardNumbers("own") }

fun MatchResult.parseCardNumbers(groupName: String) =
  groups[groupName]?.run { value.trim().split(SPACES_REGEX).toSet() }.orEmpty()

val Card.winningNumbers: Set<String>
  get() = second.intersect(first)

val Card.score: Int
  get() = winningNumbers.count().takeIf { it > 0 }?.let { count -> 2.0.pow(count - 1).toInt() } ?: 0
