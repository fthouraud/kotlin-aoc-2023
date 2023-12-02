package me.fth.aoc23.day02

import me.fth.aoc23.common.InputContext
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

typealias CubeScore = Pair<String, Int>
typealias GameScores = List<CubeScore>
typealias HighestScores = Map<String, Int>

const val BLUE_COLOR = "blue"
const val GREEN_COLOR = "green"
const val RED_COLOR = "red"

fun main() {
  withInput(object {}.loadResource("/games-log")) {
    println("Sum of possible game ids is: ${sumPossibleGameIds()}")
    println("Sum of highest scores powers is: ${sumHighestScoresPowers()}")
  }
}

context(InputContext)
fun sumPossibleGameIds() =
  lines().mapIndexedNotNull { index, log ->
    index.takeIf {
      log.parseCubeScores()
        .keepHighestCountOnly()
        .isPossible()
    }?.let { it + 1 }
  }.sum()

context(InputContext)
fun sumHighestScoresPowers() =
  lines().sumOf { log ->
    log.parseCubeScores()
      .keepHighestCountOnly()
      .values
      .reduce { a, b -> a * b }
  }

fun String.parseCubeScores(): List<CubeScore> =
  COLOR_SCORE_REGEX.findAll(this).map { it.value.parseCubeScore() }.toList()

val COLOR_SCORE_REGEX = "[0-9]+ (blue|green|red)".toRegex()

fun String.parseCubeScore(): CubeScore = split(' ').let { (score, color) -> color to score.toInt() }

fun GameScores.keepHighestCountOnly() =
  groupBy({ it.first }) { it.second }.map { (color, counts) -> color to counts.max() }.toMap()

const val RED_LIMIT = 12
const val GREEN_LIMIT = 13
const val BLUE_LIMIT = 14
val SCORE_LIMITS = mapOf(RED_COLOR to RED_LIMIT, GREEN_COLOR to GREEN_LIMIT, BLUE_COLOR to BLUE_LIMIT)

fun HighestScores.isPossible() = none { (color, score) -> SCORE_LIMITS[color] ?: 0 < score }
