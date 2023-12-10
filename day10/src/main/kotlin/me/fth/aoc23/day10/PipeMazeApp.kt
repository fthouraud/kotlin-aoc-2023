package me.fth.aoc23.day10

import me.fth.aoc23.common.InputContext
import me.fth.aoc23.common.extensions.loadResource
import me.fth.aoc23.common.withInput

fun main() {
  withInput(object {}.loadResource("/pipes-sketch")) {
    println("Halfway steps: ${firstPart()}.")
    println("There are ${secondPart()} tile inside the loop.")
  }
}

context(InputContext)
fun firstPart(startFrom: Direction = Direction.North) =
  Sketch.parse(content).getLoopTiles(startFrom).count() / 2

context(InputContext)
fun secondPart(startFrom: Direction = Direction.North) = Sketch.parse(content).countPointsOutsideLoop(startFrom)

class Sketch(private val tiles: List<Tile>, private val start: Tile, private val length: Int) {
  fun getLoopTiles(startFrom: Direction): List<Tile> {
    val loopTiles = mutableListOf(start)
    var nextTile: Tile = start
    var forwardDirection = startFrom

    do {
      nextTile = findNextTilesFrom(nextTile, forwardDirection) ?: start
      forwardDirection = nextTile.getForwardDirectionFrom(forwardDirection) ?: Direction.North
      loopTiles += nextTile
    } while (nextTile != start)

    return loopTiles
  }

  private fun findNextTilesFrom(tile: Tile, comingFrom: Direction) =
    tiles
      .find { nextTile -> tile.isNextTo(nextTile) && tile.connectsTo(comingFrom, nextTile) }

  fun countPointsOutsideLoop(startFrom: Direction): Int {
    val loopTiles = getLoopTiles(startFrom)
    val tilesPerY = tiles.filter { it in loopTiles }.groupBy { it.y }
    val insideTiles = mutableListOf<Tile>()

    tiles.filterNot { it in loopTiles || it.x == 0 || it.x == length - 1 || it.y == 0 || it.y == length - 1 }
      .onEach { nonLoopTile ->
        val row = tilesPerY.getOrDefault(nonLoopTile.y, emptyList())
        if (row.count { it.x >= nonLoopTile.x } % 2 != 0) {
          insideTiles += nonLoopTile
        }
      }

    return insideTiles.count()
  }

  companion object {
    @Suppress("LateinitUsage")
    fun parse(sketch: String): Sketch {
      lateinit var startingTile: Tile
      var length = 0
      return sketch.lines().flatMapIndexed { y, line ->
        length = line.length
        line.mapIndexedNotNull { x, tile ->
          when (tile) {
            '|' -> Tile.Pipe(x, y, setOf(Direction.North, Direction.South))
            '-' -> Tile.Pipe(x, y, setOf(Direction.West, Direction.East))
            'L' -> Tile.Pipe(x, y, setOf(Direction.North, Direction.East))
            'J' -> Tile.Pipe(x, y, setOf(Direction.North, Direction.West))
            '7' -> Tile.Pipe(x, y, setOf(Direction.South, Direction.West))
            'F' -> Tile.Pipe(x, y, setOf(Direction.South, Direction.East))
            'S' -> Tile.Start(x, y).also { startingTile = it }
            '.' -> Tile.Grass(x, y)
            else -> null
          }
        }
      }.let { Sketch(it, startingTile, length) }
    }
  }
}

sealed class Tile(val x: Int, val y: Int, protected val connectTo: Set<Direction>) {
  class Start(x: Int, y: Int) : Tile(x, y, emptySet()) {
    override fun toString() = "Start($x, $y)"
  }

  class Pipe(x: Int, y: Int, connectTo: Set<Direction>) : Tile(x, y, connectTo) {
    override fun toString() = "Pipe($x, $y, $connectTo)"
  }

  class Grass(x: Int, y: Int) : Tile(x, y, emptySet()) {
    override fun toString() = "Grass($x, $y)"
  }

  fun isNextTo(tile: Tile) = tile.x isAdjacentTo x && tile.y == y || tile.y isAdjacentTo y && tile.x == x

  fun connectsTo(direction: Direction, other: Tile) =
    direction.connection.let { connection ->
      when (direction) {
        Direction.North -> y > other.y
        Direction.South -> y < other.y
        Direction.West -> x > other.x
        Direction.East -> x < other.x
      } && connection in other.connectTo
    }

  fun getForwardDirectionFrom(from: Direction) = connectTo.firstOrNull { it != from.connection }
}

infix fun Int.isAdjacentTo(other: Int) = other == this - 1 || other == this + 1

enum class Direction {
  North,
  West,
  South,
  East,
}

val Direction.connection
  get() =
    when (this) {
      Direction.North -> Direction.South
      Direction.South -> Direction.North
      Direction.East -> Direction.West
      Direction.West -> Direction.East
    }
