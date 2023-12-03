package me.fth.aoc23.day03

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.shouldBe

class SchematicLineTest : ShouldSpec({
  context("SchematicLine") {
    context("findRealPartNumbers") {
      val partNumber = 12
      val validPartNumberRanges = setOf(SchematicItem.Part(partNumber, 2..3))
      val validSymbolRanges = setOf(SchematicItem.Symbol(false, 4))
      val schematicLineWithPartNumber = SchematicLine(validPartNumberRanges, validSymbolRanges)
      val emptySchematicLine = SchematicLine(emptySet(), emptySet())

      should("find part number from itself touched by a symbol") {
        schematicLineWithPartNumber findRealPartNumbers emptySchematicLine shouldHaveSingleElement partNumber
      }
      should("not find part number from other line only") {
        (emptySchematicLine findRealPartNumbers schematicLineWithPartNumber).shouldBeEmpty()
      }
      should("find part number which is touched by a symbol from other line") {
        SchematicLine(validPartNumberRanges, emptySet()) findRealPartNumbers
          SchematicLine(emptySet(), validSymbolRanges) shouldHaveSingleElement partNumber
      }
      should("find part number from other line which is touched by a symbol of this line") {
        SchematicLine(emptySet(), validSymbolRanges) findRealPartNumbers
          SchematicLine(validPartNumberRanges, emptySet()) shouldHaveSingleElement partNumber
      }
    }
    context("findGearRatios") {
      val firstPart = SchematicItem.Part(2, 1..1)
      val secondPart = SchematicItem.Part(3, 3..3)
      val gear = SchematicItem.Symbol(true, 2)

      should("find the gear ratio from values on outer lines") {
        val previous = SchematicLine(setOf(firstPart), emptySet())
        val current = SchematicLine(emptySet(), setOf(gear))
        val next = SchematicLine(setOf(secondPart), emptySet())

        current.findGearRatios(previous, next) shouldBe 6
      }
      should("find the gear ratio from values on previous and current lines") {
        val previous = SchematicLine(setOf(firstPart), emptySet())
        val current = SchematicLine(setOf(secondPart), setOf(SchematicItem.Symbol(true, 2)))
        val next = SchematicLine(emptySet(), emptySet())

        current.findGearRatios(previous, next) shouldBe 6
      }
      should("find the gear ratio from values on current and next lines") {
        val previous = SchematicLine(emptySet(), emptySet())
        val current = SchematicLine(setOf(firstPart), setOf(SchematicItem.Symbol(true, 2)))
        val next = SchematicLine(setOf(secondPart), emptySet())

        current.findGearRatios(previous, next) shouldBe 6
      }
      should("find the gear ratio from values on current line only") {
        val previous = SchematicLine(emptySet(), emptySet())
        val current = SchematicLine(setOf(firstPart, secondPart), setOf(SchematicItem.Symbol(true, 2)))
        val next = SchematicLine(emptySet(), emptySet())

        current.findGearRatios(previous, next) shouldBe 6
      }
    }
  }
  context("Set<PartNumber>.filterPartOverlappingSymbol") {
    val partNumber = SchematicItem.Part(123, 2..4)
    val invalidPartNumber = SchematicItem.Part(456, 1..3)
    val validRange = SchematicItem.Symbol(false, 5)

    should("only get the part number which has its range overlapped with a symbol range") {
      setOf(partNumber, invalidPartNumber).filterPartOverlappingSymbol(setOf(validRange)) shouldHaveSingleElement 123
    }
    should("get an empty list when no part number is found") {
      setOf(partNumber, invalidPartNumber).filterPartOverlappingSymbol(
        setOf(SchematicItem.Symbol(false, 30)),
      ).shouldBeEmpty()
    }
  }
  context("IntRange.overlaps") {
    val range = 2..4

    should("be true when it overlaps the range from left side") {
      (0..2).overlaps(range).shouldBeTrue()
    }
    should("be true when it overlaps the range from right side") {
      (4..6).overlaps(range).shouldBeTrue()
    }
    should("be true when it is included inside the range") {
      (3..3).overlaps(range).shouldBeTrue()
    }
    should("be false when no value can be found inside the range") {
      (0..1).overlaps(range).shouldBeFalse()
    }
  }
})
