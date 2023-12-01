package me.fth.aoc23.day01

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.datatest.WithDataTestName
import io.kotest.datatest.withData
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

class CalibrationValueAppTest : ShouldSpec({
    data class ExpectedCalibration(val input: String, val value: String) : WithDataTestName {
        override fun dataTestName(): String = "should get $value when input is $input"
    }

    context("calculateCalibrationValue") {
        should("be equal to 142 using numbers only parsing") {
            withInput(loadResource("/simple-calibration")) {
                calculateCalibrationValue(String::parseNumberOnlyCalibrationValue) shouldBeExactly 142
            }
        }
        should("be equal to 281 using alphanumeric parsing") {
            withInput(loadResource("/complex-calibration")) {
                calculateCalibrationValue(String::parseAlphanumericCalibrationValue) shouldBeExactly 281
            }
        }
    }
    context("String.parseCalibrationValue") {
        withData(
            ExpectedCalibration("1abc2", "12"),
            ExpectedCalibration("pqr3stu8vwx", "38"),
            ExpectedCalibration("a1b2c3d4e5f", "15"),
            ExpectedCalibration("treb7uchet", "77"),
        ) { (input, expectedValue) ->
            input.parseNumberOnlyCalibrationValue() shouldBe expectedValue
        }
    }
    context("String.replaceSpelledOutNumbers") {
        withData(
            ExpectedCalibration("two1nine", "29"),
            ExpectedCalibration("eightwothree", "83"),
            ExpectedCalibration("abcone2threexyz", "13"),
            ExpectedCalibration("xtwone3four", "24"),
            ExpectedCalibration("4nineeightseven2", "42"),
            ExpectedCalibration("zoneight234", "14"),
            ExpectedCalibration("7pqrstsixteen", "76"),
        ) { (input, expectedValue) ->
            input.parseAlphanumericCalibrationValue() shouldBe expectedValue
        }
    }
})
