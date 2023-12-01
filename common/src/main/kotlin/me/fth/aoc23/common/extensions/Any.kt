package me.fth.aoc23.common.extensions

fun Any.loadResource(path: String) = javaClass.getResource(path) ?: error("Resource not found: $path")
