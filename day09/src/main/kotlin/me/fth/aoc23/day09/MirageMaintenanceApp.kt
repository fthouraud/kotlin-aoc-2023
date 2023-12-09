package me.fth.aoc23.day09

import me.fth.aoc23.common.InputContext
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

typealias ValueHistory = List<Int>

fun main() {
  withInput(object {}.loadResource("/oasis-report")) {
    println("Sum of extrapolated next values is: ${firstPart()}.")
    println("Sum of extrapolated previous values is: ${secondPart()}.")
  }
}

context(InputContext)
fun firstPart() = lineSequence().map { it.toValueHistory().refine().extrapolateNextValue() }.sum()

context(InputContext)
fun secondPart() = lineSequence().map { it.toValueHistory().refine().extrapolatePreviousValue() }.sum()

fun String.toValueHistory(): ValueHistory = split(' ').map { it.toInt() }

fun ValueHistory.findNext() = zipWithNext { a, b -> b - a }

fun ValueHistory.hasNext() = !all { it == 0 }

fun ValueHistory.refine() =
  buildList<ValueHistory> {
    add(this@refine)
    while (last().hasNext()) {
      add(last().findNext())
    }
  }

fun List<ValueHistory>.extrapolateNextValue() = foldRight(0) { history, nextValue -> nextValue + history.last() }

fun List<ValueHistory>.extrapolatePreviousValue() =
  foldRight(0) { history, previousValue -> history.first() - previousValue }
