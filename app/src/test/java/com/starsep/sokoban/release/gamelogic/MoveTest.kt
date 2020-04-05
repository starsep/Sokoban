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
import com.starsep.sokoban.release.gamelogic.Move.UnknownMoveException
import org.junit.Assert
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
        for (m in moves) {
            try {
                Assert.assertEquals(
                    fromChar(
                        m.toChar()
                    ), m
                )
            } catch (e: UnknownMoveException) {
                Assert.assertTrue(false)
            }
        }
    }
}
