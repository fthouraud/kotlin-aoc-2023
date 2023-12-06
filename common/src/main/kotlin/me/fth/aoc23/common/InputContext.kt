package me.fth.aoc23.common

import java.net.URL

interface InputContext {
  val content: String
  fun lines(): List<String>
  fun lineSequence(): Sequence<String>
}

fun <R> withInput(input: URL, action: InputContext.() -> R) =
  with(
    object : InputContext {
      override val content = input.readText()
      override fun lines() = content.lines()
      override fun lineSequence() = content.lineSequence()
    },
    action,
  )
