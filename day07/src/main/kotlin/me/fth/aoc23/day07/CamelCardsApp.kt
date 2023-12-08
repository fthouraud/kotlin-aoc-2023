package me.fth.aoc23.day07

import me.fth.aoc23.common.InputContext
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

typealias Card = Int

private const val JOKER_VALUE = 1

@Suppress("MagicNumber")
val CARD_FIGURES_VALUES = mapOf('A' to 14, 'K' to 13, 'Q' to 12, 'J' to 11, 'T' to 10)

@Suppress("MagicNumber")
val JOKER_AND_CARD_FIGURES_VALUES = mapOf('A' to 13, 'K' to 12, 'Q' to 11, 'T' to 10, 'J' to JOKER_VALUE)

fun main() {
  withInput(object {}.loadResource("/hands-and-bids")) {
    println("Ordered hands winnings are: ${firstPart()}")
    println("Ordered hands winnings are (with jokers): ${secondPart()}")
  }
}

context(InputContext)
fun firstPart() =
  lineSequence()
    .map { it.parseHandAndBid(CARD_FIGURES_VALUES) }
    .sortedBy { (hand, _) -> hand }
    .mapIndexed { index, (_, bid) -> (index + 1) * bid }
    .sum()

context(InputContext)
fun secondPart() =
  lineSequence()
    .map { it.parseHandAndBid(JOKER_AND_CARD_FIGURES_VALUES) }
    .map { (hand, bid) -> JokerHand(hand) to bid }
    .sortedBy { (hand, _) -> hand }
    .mapIndexed { index, (_, bid) -> (index + 1) * bid }
    .sum()

fun String.parseHandAndBid(figureValues: Map<Char, Int>) =
  split(' ').let { (hand, bid) -> hand.parseHand(figureValues) to bid.toInt() }

fun String.parseHand(figureValues: Map<Char, Int>) = Hand(map { figureValues[it] ?: it.digitToInt() })

class Hand(val cards: List<Card>) : Comparable<Hand> {
  private val cardsRepartition = cards.groupBy { it }

  @Suppress("MagicNumber")
  val type =
    when {
      cardsRepartition.size == 1 -> HandType.FiveOfAKind
      cardsRepartition.any { it.value.count() == 4 } -> HandType.FourOfAKind
      cardsRepartition.any { it.value.count() == 3 } ->
        HandType.FullHouse.takeIf { cardsRepartition.size == 2 } ?: HandType.ThreeOfAKind

      cardsRepartition.count { it.value.count() == 2 } == 2 -> HandType.TwoPairs
      cardsRepartition.any { it.value.count() == 2 } -> HandType.OnePair
      else -> HandType.HighCard
    }

  operator fun get(index: Int) = cards[index]

  override fun compareTo(other: Hand): Int {
    val comparison = type.compareTo(other.type)
    if (comparison == 0) {
      for ((index, card) in cards.withIndex()) {
        if (card != other[index]) {
          return card.compareTo(other[index])
        }
      }
    }
    return comparison
  }
}

class JokerHand(private val hand: Hand) : Comparable<JokerHand> {
  private val hasJoker
    get() = hand.cards.any { it == JOKER_VALUE }
  private val type
    get() =
      when (hand.type) {
        HandType.FourOfAKind -> HandType.FiveOfAKind.takeIf { hasJoker }
        HandType.FullHouse -> HandType.FiveOfAKind.takeIf { _ -> hand.cards.count { it == JOKER_VALUE } >= 2 }
        HandType.ThreeOfAKind -> HandType.FourOfAKind.takeIf { hasJoker }
        HandType.TwoPairs ->
          HandType.FullHouse.takeIf { _ -> hand.cards.count { it == JOKER_VALUE } == 1 }
            ?: HandType.FourOfAKind.takeIf { _ -> hand.cards.count { it == JOKER_VALUE } == 2 }

        HandType.OnePair -> HandType.ThreeOfAKind.takeIf { hasJoker }
        HandType.HighCard -> HandType.OnePair.takeIf { hasJoker }
        HandType.FiveOfAKind -> HandType.FiveOfAKind
      } ?: hand.type

  override fun compareTo(other: JokerHand): Int {
    val comparison = type.compareTo(other.type)
    if (comparison == 0) {
      for ((index, card) in hand.cards.withIndex()) {
        if (card != other.hand[index]) {
          return card.compareTo(other.hand[index])
        }
      }
    }
    return comparison
  }

  override fun toString() = hand.cards.toString()
}

enum class HandType {
  HighCard,
  OnePair,
  TwoPairs,
  ThreeOfAKind,
  FullHouse,
  FourOfAKind,
  FiveOfAKind,
}
