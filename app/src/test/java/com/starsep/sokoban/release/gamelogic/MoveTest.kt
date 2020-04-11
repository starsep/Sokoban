package com.starsep.sokoban.release.gamelogic

import com.starsep.sokoban.release.gamelogic.Move.Companion.DOWN
import com.starsep.sokoban.release.gamelogic.Move.Companion.LEFT
import com.starsep.sokoban.release.gamelogic.Move.Companion.PUSH_DOWN
import com.starsep.sokoban.release.gamelogic.Move.Companion.PUSH_LEFT
import com.starsep.sokoban.release.gamelogic.Move.Companion.PUSH_RIGHT
import com.starsep.sokoban.release.gamelogic.Move.Companion.PUSH_UP
import com.starsep.sokoban.release.gamelogic.Move.Companion.RIGHT
import com.starsep.sokoban.release.gamelogic.Move.Companion.UP
import com.starsep.sokoban.release.gamelogic.Move.Companion.fromChar
import org.junit.Assert.assertEquals
import org.junit.Test

class MoveTest {
    @Test
    fun toStringFromCharIsIdentity() {
        val moves =
            arrayOf(
                DOWN,
                LEFT,
                RIGHT,
                UP,
                PUSH_DOWN,
                PUSH_LEFT,
                PUSH_RIGHT,
                PUSH_UP
            )
        moves.forEach { move ->
            assertEquals(move, fromChar(move.toChar()))
        }
    }

    @Test
    fun `test reverse`() {
        val cases = arrayOf(
            DOWN to UP,
            LEFT to RIGHT,
            RIGHT to LEFT,
            UP to DOWN,
            PUSH_DOWN to PUSH_UP,
            PUSH_LEFT to PUSH_RIGHT,
            PUSH_RIGHT to PUSH_LEFT,
            PUSH_UP to PUSH_DOWN
        )

        cases.forEach { (move, expected) ->
            assertEquals(expected, move.reverse())
        }
    }

    @Test
    fun `test toPosition`() {
        val cases = arrayOf(
            DOWN to Position(y = 1, x = 0),
            LEFT to Position(y = 0, x = -1),
            RIGHT to Position(y = 0, x = 1),
            UP to Position(y = -1, x = 0),
            PUSH_DOWN to Position(y = 1, x = 0),
            PUSH_LEFT to Position(y = 0, x = -1),
            PUSH_RIGHT to Position(y = 0, x = 1),
            PUSH_UP to Position(y = -1, x = 0)
        )

        cases.forEach { (move, expected) ->
            assertEquals(expected, move.toPosition())
        }
    }
}
