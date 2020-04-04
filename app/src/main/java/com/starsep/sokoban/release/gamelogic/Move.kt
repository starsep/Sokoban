package com.starsep.sokoban.release.gamelogic

import android.graphics.Bitmap

import com.starsep.sokoban.release.res.Textures

class Move private constructor(private val direction: Direction, val push: Boolean) {
    fun reverse(): Move {
        return when (direction) {
            Move.Direction.LEFT -> if (push) PUSH_RIGHT else RIGHT
            Move.Direction.RIGHT -> if (push) PUSH_LEFT else LEFT
            Move.Direction.DOWN -> if (push) PUSH_UP else UP
            Move.Direction.UP -> if (push) PUSH_DOWN else DOWN
        }
    }

    fun heroTexture(): Bitmap {
        return when (direction) {
            Move.Direction.LEFT -> Textures.heroLeft()
            Move.Direction.RIGHT -> Textures.heroRight()
            Move.Direction.UP -> Textures.heroUp()
            Move.Direction.DOWN -> Textures.heroDown()
        }
    }

    fun dx(): Int {
        return when (direction) {
            Move.Direction.LEFT -> -1
            Move.Direction.RIGHT -> 1
            Move.Direction.UP, Move.Direction.DOWN -> 0
        }
    }

    fun dy(): Int {
        return when (direction) {
            Move.Direction.LEFT, Move.Direction.RIGHT -> 0
            Move.Direction.UP -> -1
            Move.Direction.DOWN -> 1
        }
    }

    override fun toString(): String {
        return "" + toChar()
    }

    fun toChar(): Char {
        return when (direction) {
            Move.Direction.LEFT -> if (push) 'L' else 'l'
            Move.Direction.RIGHT -> if (push) 'R' else 'r'
            Move.Direction.DOWN -> if (push) 'D' else 'd'
            Move.Direction.UP -> if (push) 'U' else 'u'
        }
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

        fun makePush(move: Move): Move {
            return when (move.direction) {
                Move.Direction.LEFT -> PUSH_LEFT
                Move.Direction.RIGHT -> PUSH_RIGHT
                Move.Direction.UP -> PUSH_UP
                Move.Direction.DOWN -> PUSH_DOWN
            }
        }

        @Throws(UnknownMoveException::class)
        fun fromChar(c: Char): Move {
            return when (c) {
                'l' -> LEFT
                'L' -> PUSH_LEFT
                'r' -> RIGHT
                'R' -> PUSH_RIGHT
                'u' -> UP
                'U' -> PUSH_UP
                'd' -> DOWN
                'D' -> PUSH_DOWN
                else -> throw UnknownMoveException()
            }
        }
    }

    fun toPosition(): Position {
        return Position(dy(), dx())
    }
}