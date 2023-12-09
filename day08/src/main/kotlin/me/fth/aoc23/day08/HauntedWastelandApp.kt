package me.fth.aoc23.day08

import me.fth.aoc23.common.InputContext
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput
import java.math.BigInteger

fun main() {
  withInput(object {}.loadResource("/maps-pouch")) {
    println("Final destination found in '${firstPart()}' steps.")
    println("Final destination found in '${secondPart()}' steps.")
  }
}

private const val START_LOCATION = "AAA"
private const val END_LOCATION = "ZZZ"
private const val LEFT_DIRECTION = 'L'

context(InputContext)
fun firstPart() =
  lines().let { lines ->
    val directions = lines.first().parseDirections()
    val destinationsPerLocation = lines.drop(2).associate(String::parseDestinationsPerLocation)
    var currentLocation = START_LOCATION

    directions.indexOfFirst { direction ->
      currentLocation = destinationsPerLocation.getValue(currentLocation).selectDestination(direction)
      currentLocation == END_LOCATION
    } + 1
  }

context(InputContext)
fun secondPart(): BigInteger {
  val (directions, destinationsPerLocation) =
    lines().let { lines -> lines.first() to lines.drop(2).associate(String::parseDestinationsPerLocation) }
  var iterations = 0
  var currentLocations = destinationsPerLocation.keys.filter { it.endsWith('A') }
  val iterationsPerLocation = mutableMapOf<Int, Int>()

  do {
    for (direction in directions) {
      iterations += 1
      currentLocations =
        currentLocations.map { location -> destinationsPerLocation.getValue(location).selectDestination(direction) }
          .onEachIndexed { i, v -> if (v.endsWith('Z')) iterationsPerLocation.putIfAbsent(i, iterations) }
      if (iterationsPerLocation.size == currentLocations.size) break
    }
  } while (iterationsPerLocation.size != currentLocations.size)

  return iterationsPerLocation.values.leastCommonMultiplier()
}

fun String.parseDirections() = sequence { while (true) yieldAll(asSequence()) }

fun String.parseDestinationsPerLocation() =
  split('=').let { (location, instruction) -> location.trim() to instruction.trim() }

fun String.selectDestination(direction: Char) =
  if (direction == LEFT_DIRECTION) leftDestination else rightDestination

val String.leftDestination: String
  get() = substring(LEFT_DESTINATION_RANGE)

@Suppress("MagicNumber")
val LEFT_DESTINATION_RANGE = 1..3
val String.rightDestination: String
  get() = substring(RIGHT_DESTINATION_RANGE)

@Suppress("MagicNumber")
val RIGHT_DESTINATION_RANGE = 6..8

fun Collection<Int>.leastCommonMultiplier() = map { it.toBigInteger() }.reduce { lcm, i -> lcm * i / lcm.gcd(i) }
