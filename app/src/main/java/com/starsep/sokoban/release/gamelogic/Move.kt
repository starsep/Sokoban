package com.starsep.sokoban.release.gamelogic

import com.starsep.sokoban.release.res.Textures

class Move private constructor(private val direction: Direction, val push: Boolean) {
    fun reverse() = when (direction) {
        Direction.LEFT -> if (push) PUSH_RIGHT else RIGHT
        Direction.RIGHT -> if (push) PUSH_LEFT else LEFT
        Direction.DOWN -> if (push) PUSH_UP else UP
        Direction.UP -> if (push) PUSH_DOWN else DOWN
    }

    fun heroTexture() = when (direction) {
        Direction.LEFT -> Textures.heroLeft
        Direction.RIGHT -> Textures.heroRight
        Direction.UP -> Textures.heroUp
        Direction.DOWN -> Textures.heroDown
    }

    fun dx() = when (direction) {
        Direction.LEFT -> -1
        Direction.RIGHT -> 1
        Direction.UP, Direction.DOWN -> 0
    }

    fun dy() = when (direction) {
        Direction.LEFT, Direction.RIGHT -> 0
        Direction.UP -> -1
        Direction.DOWN -> 1
    }

    override fun toString() = "" + toChar()

    fun toChar() = when (direction) {
        Direction.LEFT -> if (push) 'L' else 'l'
        Direction.RIGHT -> if (push) 'R' else 'r'
        Direction.DOWN -> if (push) 'D' else 'd'
        Direction.UP -> if (push) 'U' else 'u'
    }

    enum class Direction {
        LEFT,
        RIGHT,
        DOWN,
        UP
    }

    class UnknownMoveException : Exception()

    companion object {
        val DOWN = Move(Direction.DOWN, false)
        val UP = Move(Direction.UP, false)
        val LEFT = Move(Direction.LEFT, false)
        val RIGHT = Move(Direction.RIGHT, false)
        val PUSH_DOWN = Move(Direction.DOWN, true)
        val PUSH_UP = Move(Direction.UP, true)
        val PUSH_LEFT = Move(Direction.LEFT, true)
        val PUSH_RIGHT = Move(Direction.RIGHT, true)

        fun makePush(move: Move) = when (move.direction) {
            Direction.LEFT -> PUSH_LEFT
            Direction.RIGHT -> PUSH_RIGHT
            Direction.UP -> PUSH_UP
            Direction.DOWN -> PUSH_DOWN
        }

        @Throws(UnknownMoveException::class)
        fun fromChar(c: Char) = when (c) {
            'l' -> LEFT
            'L' -> PUSH_LEFT
            'r' -> RIGHT
            'R' -> PUSH_RIGHT
            'u' -> UP
            'U' -> PUSH_UP
            'd' -> DOWN
            'D' -> PUSH_DOWN
            else -> throw UnknownMoveException().also {
                println("ASDASDSADASD $c")
            }
        }
    }

    fun toPosition() = Position(dy(), dx())
}
