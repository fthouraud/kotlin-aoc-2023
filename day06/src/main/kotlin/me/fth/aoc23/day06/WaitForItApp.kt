package me.fth.aoc23.day06

import me.fth.aoc23.common.InputContext
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

fun main() {
  withInput(object {}.loadResource("/race-statistics")) {
    val (times, distances) = parseRacesStatistics()
    println("Multiplication of possible winning moves is: ${multiplicationOfPossibleWinningMoves(times, distances)}")
    val (time, distance) = parseSingleRaceStatistics()
    println("Single race winning moves are: ${countWinningMoves(time, distance)}")
  }
}

context(InputContext)
fun parseRacesStatistics(): Pair<Sequence<Long>, Sequence<Long>> =
  lines().map { NUMBERS_ONLY_REGEX.findAll(it).map { result -> result.value.toLong() } }
    .let { (time, distance) -> time to distance }

val NUMBERS_ONLY_REGEX = "[0-9]+".toRegex()

context(InputContext)
fun parseSingleRaceStatistics(): Pair<Long, Long> =
  lines().map { it.replace(EXCEPT_NUMBER_REGEX, "").toLong() }
    .let { (time, distance) -> time to distance }

val EXCEPT_NUMBER_REGEX = "[^0-9]".toRegex()

fun multiplicationOfPossibleWinningMoves(times: Sequence<Long>, distances: Sequence<Long>) =
  times.foldIndexed(1) { index, score, time -> countWinningMoves(time, distances.elementAt(index)) * score }

fun countWinningMoves(maxTime: Long, distanceToBeat: Long): Int =
  (1..<maxTime).let { possibleHoldingTimes ->
    val firstWinningRaceIndex = possibleHoldingTimes.indexOfFirst(isGettingCarFurther(maxTime, distanceToBeat))
    val lastWinningRaceIndex = possibleHoldingTimes.indexOfLast(isGettingCarFurther(maxTime, distanceToBeat))
    return (firstWinningRaceIndex..lastWinningRaceIndex).count()
  }

fun isGettingCarFurther(maxTime: Long, distanceToBeat: Long) =
  { holdingTime: Long -> (maxTime - holdingTime) * holdingTime > distanceToBeat }
