package me.fth.aoc23.common

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainExactly

class InputContextTest : ShouldSpec({
  context("withInput") {
    should("be able to read input's lines") {
      withInput(javaClass.getResource("/test-input")!!) {
        lines() shouldContainExactly listOf("foo", "bar", "fizz", "buzz")
      }
    }
  }
})
