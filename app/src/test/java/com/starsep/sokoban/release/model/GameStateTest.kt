package com.starsep.sokoban.release.model

import com.starsep.sokoban.release.gamelogic.Move
import com.starsep.sokoban.release.model.GameState.Companion.createGameState
import org.junit.Assert.assertEquals
import org.junit.Test

class GameStateTest {
    @Test
    fun testCreateGameStateEmpty() {
        assertEquals(GameState(
            time = 42,
            levelNumber = 4,
            movesList = ""
        ), createGameState(
            time = 42,
            levelNumber = 4,
            moves = listOf()
        ))
    }

    @Test
    fun testCreateGameState() {
        assertEquals(GameState(
            time = 100,
            levelNumber = 5,
            movesList = "dlDUrurLR"
        ), createGameState(
            time = 100,
            levelNumber = 5,
            moves = listOf(
                Move.DOWN,
                Move.LEFT,
                Move.PUSH_DOWN,
                Move.PUSH_UP,
                Move.RIGHT,
                Move.UP,
                Move.RIGHT,
                Move.PUSH_LEFT,
                Move.PUSH_RIGHT
            )
        ))
    }
}