package me.fth.aoc23.day01

import me.fth.aoc23.common.InputContext
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

fun main() {
  withInput(object {}.loadResource("/calibration")) {
    println("Calibration value is: ${calculateCalibrationValue(String::parseNumberOnlyCalibrationValue)}")
    println("Refined calibration value is: ${calculateCalibrationValue(String::parseAlphanumericCalibrationValue)}")
  }
}

context(InputContext)
fun calculateCalibrationValue(parsingMethod: String.() -> String): Int = lines().map(parsingMethod).sumOf { it.toInt() }

fun String.parseNumberOnlyCalibrationValue() =
  parseCalibrationValue(NUMBERS_ONLY_REGEX)
    .joinToString(separator = "")

fun String.parseCalibrationValue(selectionRegex: Regex) =
  selectionRegex.findAll(this)
    .let { listOf(it.first(), it.last()) }
    .map(MatchResult::value)

val NUMBERS_ONLY_REGEX = "[0-9]".toRegex()

fun String.parseAlphanumericCalibrationValue() =
  parseCalibrationValue(ALPHANUMERIC_NUMBERS_REGEX)
    .joinToString(separator = "") { value -> NUMBER_DICTIONARY[value] ?: value }

val ALPHANUMERIC_NUMBERS_REGEX = "([0-9]|one|two|three|four|five|six|seven|eight|nine)".toRegex()
val NUMBER_DICTIONARY =
  mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
  )
