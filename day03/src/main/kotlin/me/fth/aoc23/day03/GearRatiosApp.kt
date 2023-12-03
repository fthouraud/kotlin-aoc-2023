package me.fth.aoc23.day03

import me.fth.aoc23.common.InputContext
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

fun main() {
  withInput(object {}.loadResource("/engine-schematic")) {
    println("Sum valid part numbers is: ${sumPartNumbers()}")
    println("Sum of gear ratios is: ${sumGearRatios()}")
  }
}

context(InputContext)
fun sumPartNumbers(): Int {
  return lineSequence()
    .map(String::parseSchematicLine)
    .zipWithNext { current, next -> current findRealPartNumbers next }
    .flatten()
    .sum()
}

context(InputContext)
fun sumGearRatios(): Int {
  return lineSequence()
    .map(String::parseSchematicLine)
    .windowed(size = 3, partialWindows = true) { lines ->
      lines.takeIf { it.size >= 2 }
        ?.let { (previous, current) -> current.findGearRatios(previous, lines.lastOrNull()) }
        ?: 0
    }
    .sum()
}

fun String.parseSchematicLine(): SchematicLine {
  var partNumberFirstIndex: Int? = null
  val partNumbers: MutableSet<SchematicItem.Part> = mutableSetOf()
  val symbols: MutableSet<SchematicItem.Symbol> = mutableSetOf()
  forEachIndexed { index, c ->
    if (c.isDigit() && partNumberFirstIndex == null) partNumberFirstIndex = index
    if (c.isSchematicSymbol()) symbols += SchematicItem.Symbol(c.isGearSymbol(), index)

    if (index == lastIndex || !c.isDigit()) {
      val closingIndex = if (c.isDigit() && index == lastIndex) index + 1 else index
      partNumberFirstIndex?.let { firstIndex ->
        val partNumberRange = firstIndex..<closingIndex
        partNumbers += SchematicItem.Part(slice(partNumberRange).toInt(), partNumberRange)
      }
      partNumberFirstIndex = null
    }
  }
  return SchematicLine(partNumbers, symbols)
}

fun Char.isSchematicSymbol() = this != '.' && !isDigit()

fun Char.isGearSymbol() = this == '*'
