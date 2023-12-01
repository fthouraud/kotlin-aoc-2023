package me.fth.aoc23.common.extensions

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.throwable.shouldHaveMessage

class AnyTest : ShouldSpec({
    context("Any.loadResource") {
        should("return a URL to an existing file") {
            loadResource("/test-input").shouldNotBeNull()
        }
        should("throw when the resource does not exist") {
            shouldThrow<IllegalStateException> {
                loadResource("/missing-input")
            } shouldHaveMessage "Resource not found: /missing-input"
        }
    }
})
