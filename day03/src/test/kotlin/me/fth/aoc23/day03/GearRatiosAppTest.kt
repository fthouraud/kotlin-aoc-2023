package me.fth.aoc23.day03

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.char
import io.kotest.property.arbitrary.filterNot
import io.kotest.property.checkAll
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

class GearRatiosAppTest : ShouldSpec({
  context("sumPartNumbers") {
    should("sum all part numbers that are touched by a symbol") {
      withInput(loadResource("/engine-schematic")) {
        sumPartNumbers() shouldBe 4361
      }
    }
  }
  context("sumGearRatios") {
    should("sum gear ratios from schematic") {
      withInput(loadResource("/engine-schematic")) {
        sumGearRatios() shouldBe 467_835
      }
    }
  }
  context("String.parseSchematicLine") {
    should("only get 2 part number ranges and no symbol") {
      "467..114..".parseSchematicLine() shouldBeEqualToComparingFields
        SchematicLine(
          setOf(SchematicItem.Part(467, 0..<3), SchematicItem.Part(114, 5..<8)),
          emptySet(),
        )
    }
    should("get a part number range and a symbol range") {
      ".....+.58.".parseSchematicLine() shouldBeEqualToComparingFields
        SchematicLine(
          setOf(SchematicItem.Part(58, 7..<9)),
          setOf(SchematicItem.Symbol(false, 5)),
        )
    }
    should("get a part number range at the end of the line") {
      ".........1".parseSchematicLine() shouldBeEqualToComparingFields
        SchematicLine(setOf(SchematicItem.Part(1, 9..<10)), emptySet())
    }
    should("get only two symbol ranges including a gear") {
      "...\$.*....".parseSchematicLine() shouldBeEqualToComparingFields
        SchematicLine(
          emptySet(),
          setOf(
            SchematicItem.Symbol(false, 3),
            SchematicItem.Symbol(true, 5),
          ),
        )
    }
  }
  context("Char.isSchematicSymbol") {
    should("be true for any symbol which is not a digit nor a period") {
      checkAll(Arb.char().filterNot { it == '.' || it.isDigit() }) { validCharacter ->
        validCharacter.isSchematicSymbol().shouldBeTrue()
      }
    }
    should("be false when the character is a period") {
      '.'.isSchematicSymbol().shouldBeFalse()
    }
    should("be false when the character is a digit") {
      checkAll(Arb.char('0'..'9')) { digitCharacter ->
        digitCharacter.isSchematicSymbol().shouldBeFalse()
      }
    }
  }
  context("Char.isGearSymbol") {
    should("be true when character is an asterisk") {
      '*'.isGearSymbol().shouldBeTrue()
    }
    should("be false when character is anything else than an asterisk") {
      checkAll(Arb.char().filterNot { it == '*' }) { invalidCharacter ->
        invalidCharacter.isGearSymbol().shouldBeFalse()
      }
    }
  }
})
