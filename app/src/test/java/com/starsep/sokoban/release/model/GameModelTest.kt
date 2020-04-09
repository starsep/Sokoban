package com.starsep.sokoban.release.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.starsep.sokoban.release.gamelogic.DefaultLevelLoader
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class GameModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun testX() {
        val gameModel = GameModel(mock(), DefaultLevelLoader)
        gameModel.onMoveRight()

        gameModel.onResetLevel()
        assertTrue(gameModel.moves().isEmpty())
        val oldX = gameModel.player().x

        gameModel.onUndoMove()
        assertEquals(oldX, gameModel.player().x)
        assertEquals(0, gameModel.stats().moves)
    }
}