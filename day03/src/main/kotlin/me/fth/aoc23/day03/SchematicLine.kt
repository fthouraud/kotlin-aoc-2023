package me.fth.aoc23.day03

class SchematicLine(private val partNumberRanges: Set<SchematicItem.Part>, private val symbolRanges: Set<SchematicItem.Symbol>) {
  infix fun findRealPartNumbers(otherLine: SchematicLine): List<Int> =
    buildList {
      addAll(partNumberRanges.filterPartOverlappingSymbol(symbolRanges + otherLine.symbolRanges))
      addAll(otherLine.partNumberRanges.filterPartOverlappingSymbol(symbolRanges))
    }

  fun findGearRatios(previous: SchematicLine, next: SchematicLine?) =
    symbolRanges.filter(SchematicItem.Symbol::isGear)
      .map { gear ->
        (previous.partNumberRanges + partNumberRanges + next?.partNumberRanges.orEmpty())
          .filter { part -> gear.positionRange.overlaps(part.positionRange) }
      }
      .sumOf { parts -> parts.takeIf { it.size == 2 }?.let { parts.first().number * parts.last().number } ?: 0 }
}

sealed class SchematicItem(val positionRange: IntRange) {
  class Symbol(val isGear: Boolean, position: Int) : SchematicItem((position - 1).coerceAtLeast(0)..position + 1)
  class Part(val number: Int, positionRange: IntRange) : SchematicItem(positionRange)
}

fun Set<SchematicItem.Part>.filterPartOverlappingSymbol(symbolRanges: Set<SchematicItem.Symbol>) =
  filter { partNumber -> symbolRanges.any { it.positionRange.overlaps(partNumber.positionRange) } }.map { it.number }

fun IntRange.overlaps(other: IntRange) = any { it in other }
