package me.fth.aoc23.day05

import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

fun main() {
  withInput(object {}.loadResource("/almanac")) {
    val almanac = Almanac.parse(content)
    almanac.findLowestSeedDestination().also { (seed, destination) ->
      println("Lowest destination for seed $seed is: $destination")
    }
    almanac.findLowestSeedDestinationWithRangeOfSeeds().let { (seed, destination) ->
      println("Lowest destination for seed $seed is: $destination")
    }
  }
}

const val SEEDS_GROUP = "seeds"
const val MAP_GROUP = "map"
const val COORDINATES_GROUP = "coordinates"
val PARSING_REGEX = "(seeds: (?<$SEEDS_GROUP>([0-9]+ ?)+)|(?<$MAP_GROUP>[a-z-]+) map:\\s(?<$COORDINATES_GROUP>([0-9]+\\s?)+))".toRegex()

class Almanac(private val seeds: Iterable<Long>, private val instructions: Set<Instruction>) {
  fun findLowestSeedDestination() =
    seeds.minByOrNull { seed -> instructions.fold(seed) { origin, instruction -> instruction.findDestination(origin) } }
      ?.let { it to instructions.fold(it) { origin, instruction -> instruction.findDestination(origin) } }
      ?: error("Cannot find the lowest destination.")

  fun findLowestSeedDestinationWithRangeOfSeeds() =
    seeds.windowed(size = 2, step = 2) { (start, length) -> start..<start + length }
      .map { Almanac(it, instructions).findLowestSeedDestination() }
      .minByOrNull { (_, destination) -> destination }
      ?: error("Cannot find the lowest destination.")

  companion object {
    fun parse(raw: String): Almanac {
      val results = PARSING_REGEX.findAll(raw)
      val seeds = results.first().groups[SEEDS_GROUP]?.run { value.split(" ").map(String::toLong) }.orEmpty()
      val instructions = results.drop(1).map(Instruction.Companion::parse).toSet()
      return Almanac(seeds, instructions)
    }
  }
}

class Instruction(val from: String, val to: String, val ranges: Set<SeedRange>) {
  fun findDestination(origin: Long) = ranges.find { range -> origin in range }?.get(origin) ?: origin

  companion object {
    fun parse(result: MatchResult): Instruction {
      val (from, _, to) =
        result.groups[MAP_GROUP]?.run { value.split('-') } ?: error("Unexpected null origin and destination")
      val instructions =
        result.groups[COORDINATES_GROUP]?.run {
          value.split('\n').filterNot(String::isBlank).map(SeedRange.Companion::parse)
        }.orEmpty()
      return Instruction(from, to, instructions.toSet())
    }
  }
}

class SeedRange(private val sourceStart: Long, private val destinationStart: Long, private val length: Long) {
  operator fun contains(origin: Long) = origin >= sourceStart && origin < sourceStart + length
  operator fun get(origin: Long) = destinationStart + (origin - sourceStart)

  companion object {
    fun parse(raw: String) =
      raw.split(' ')
        .map(String::toLong)
        .let { (destination, source, length) -> SeedRange(source, destination, length) }
  }
}
