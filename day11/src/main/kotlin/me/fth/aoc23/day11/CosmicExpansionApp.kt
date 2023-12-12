package me.fth.aoc23.day11

import me.fth.aoc23.common.InputContext
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput
import kotlin.math.abs

typealias Universe = List<List<Char>>
typealias Coordinates = Pair<Int, Int>

const val GALAXY = '#'
const val VOID = '.'

fun main() {
  withInput(object {}.loadResource("/image")) {
    println("Sum of distances between galaxies: ${firstPart()}.")
    println("Sum of distances between galaxies (greater expansion): ${secondPart()}.")
  }
}

context(InputContext)
fun firstPart() =
  content.parseUniverse()
    .findDistancesBetweenGalaxies()
    .sum()

const val MAXIMUM_EXPANSION = 999_999

context(InputContext)
fun secondPart() =
  content.parseUniverse()
    .findDistancesBetweenGalaxies(MAXIMUM_EXPANSION)
    .sum()

fun String.parseUniverse(): Universe = lines().map(String::toList)

val Universe.galaxies
  get() =
    foldIndexed(mutableListOf<Coordinates>()) { y, galaxies, line ->
      line.forEachIndexed { x, v -> if (v == GALAXY) galaxies += x to y }
      galaxies
    }

fun Universe.findDistancesBetweenGalaxies(expansionFactor: Int = 1): List<Long> {
  val verticalExpansion = indices.filterNot { y -> GALAXY in get(y) }
  val horizontalExpansion = first().indices.filter { x -> none { it[x] == GALAXY } }
  val allGalaxies = galaxies.map { it.adjustExpansion(horizontalExpansion, verticalExpansion, expansionFactor) }
  val reviewedPaths = mutableListOf<Pair<Coordinates, Coordinates>>()
  val pathLength = mutableListOf<Long>()
  allGalaxies.forEach { g1 ->
    allGalaxies.forEach { g2 ->
      if (g1 != g2 && g2 to g1 !in reviewedPaths) {
        pathLength += g1 distanceTo g2
        reviewedPaths += g1 to g2
      }
    }
  }
  return pathLength
}

infix fun Coordinates.distanceTo(other: Coordinates) = abs(first.toLong() - other.first) + abs(second.toLong() - other.second)

fun Coordinates.adjustExpansion(horizontalExpansion: List<Int>, verticalExpansion: List<Int>, expansionFactor: Int) =
  let { (x, y) ->
    x + horizontalExpansion.count { it < x } * expansionFactor to y + verticalExpansion.count { it < y } * expansionFactor
  }
