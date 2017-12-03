package com.starsep.sokoban.gamelogic

data class Position @JvmOverloads constructor(val y: Int = 0, val x: Int = 0) {
    operator infix fun plus(other: Position) : Position {
        return Position(y + other.y, x + other.x)
    }
    fun copyOf(): Position {
        return Position(y, x)
    }
}
