package com.starsep.sokoban.release.gamelogic

import com.google.gson.Gson
import com.starsep.sokoban.release.gamelogic.Move.Companion.LEFT
import com.starsep.sokoban.release.gamelogic.Move.Companion.RIGHT
import com.starsep.sokoban.release.gamelogic.level.ImmutableLevel
import com.starsep.sokoban.release.gamelogic.level.Level
import com.starsep.sokoban.release.gamelogic.level.getDefaultLevel
import org.junit.Assert
import org.junit.Test

class LevelTest {
    @Test
    fun moveTest() {
        val data = ("WWWWW" + "W...W" + "WWWWW").toCharArray()
        val level =
            Level(
                ImmutableLevel(
                    data,
                    5,
                    Position(1, 1)
                )
            )
        level.move(RIGHT)
        val dataAfterMove = ("WWWWW" + "W...W" + "WWWWW").toCharArray()
        Assert.assertArrayEquals(level.tiles, dataAfterMove)
        Assert.assertEquals(
            Position(1, 2),
            level.player
        )
    }

    @Test
    fun pushTest() {
        val data = ("WWWWW" + "W.#.W" + "WWWWW").toCharArray()
        val level =
            Level(
                ImmutableLevel(
                    data,
                    5,
                    Position(1, 1)
                )
            )
        level.move(RIGHT)
        val dataAfterMove = ("WWWWW" + "W..#W" + "WWWWW").toCharArray()
        Assert.assertArrayEquals(level.tiles, dataAfterMove)
        Assert.assertEquals(
            Position(1, 2),
            level.player
        )
    }

    @Test
    fun pushTestWithWin() {
        val data = ("WWWWWW" + "W.#.XW" + "WWWWWW").toCharArray()
        val level =
            Level(
                ImmutableLevel(
                    data,
                    6,
                    Position(1, 1)
                )
            )
        level.move(RIGHT)
        level.move(RIGHT)
        val dataAfterMove = ("WWWWWW" + "W...&W" + "WWWWWW").toCharArray()
        Assert.assertArrayEquals(level.tiles, dataAfterMove)
        Assert.assertEquals(
            Position(1, 3),
            level.player
        )
    }

    @Test
    fun gsonLevel() {
        val level = getDefaultLevel()
        level.move(LEFT)
        val gson = Gson()
        val jsonString = gson.toJson(level)
        val fromJson =
            gson.fromJson(
                jsonString,
                Level::class.java
            )
        Assert.assertTrue(level.equals(fromJson))
    }
}
