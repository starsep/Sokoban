package com.starsep.sokoban.release.gamelogic

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class HighScoreTest {
    @Test
    fun gsonHighscore() {
        val highScore = HighScore(levelNumber = 0, time = 0, moves = 0, pushes = 0, levelHash = 0)
        val gson = Gson()
        val jsonString = gson.toJson(highScore)
        val fromJson = gson.fromJson(jsonString, HighScore::class.java)
        assertEquals(highScore, fromJson)
    }

    @Test
    fun `test improve`() {
        val highScore = HighScore(levelNumber = 2, time = 10, moves = 42, pushes = 23, levelHash = 0)
        val another = HighScore(levelNumber = 2, time = 15, moves = 31, pushes = 31, levelHash = 0)

        highScore.improve(another)
        val expected = HighScore(levelNumber = 2, time = 10, moves = 31, pushes = 23, levelHash = 0)

        assertEquals(expected, highScore)
    }

    @Test
    fun `test toString`() {
        val highScore = HighScore(levelNumber = 2, time = 10, moves = 42, pushes = 23, levelHash = 0)

        val expected = "HighScore(time=10, movesLive=42, pushes=23)"

        assertEquals(expected, highScore.toString())
    }
}
