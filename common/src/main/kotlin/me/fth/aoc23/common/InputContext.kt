package me.fth.aoc23.common

import java.net.URL

fun interface InputContext {
    fun lines(): List<String>
}

fun <R> withInput(input: URL, action: InputContext.() -> R) = with(InputContext { input.readText().lines() }, action)
