package me.fth.aoc23.common

import java.net.URL

interface InputContext {
  fun lines(): List<String>
  fun lineSequence(): Sequence<String>
}

fun <R> withInput(input: URL, action: InputContext.() -> R) =
  with(
    object : InputContext {
      override fun lines() = input.readText().lines()
      override fun lineSequence() = input.readText().lineSequence()
    },
    action,
  )
