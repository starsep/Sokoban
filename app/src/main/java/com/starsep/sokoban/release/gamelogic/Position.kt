package com.starsep.sokoban.release.gamelogic

data class Position @JvmOverloads constructor(val y: Int = 0, val x: Int = 0) {
    infix operator fun plus(other: Position) = Position(y + other.y, x + other.x)
}
