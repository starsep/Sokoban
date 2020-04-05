package com.starsep.sokoban.release.gamelogic

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test

class HighScoreTest {
    @Test
    fun gsonHighscore() {
        val highScore = HighScore(0, 0, 0, 0, 0)
        val gson = Gson()
        val jsonString = gson.toJson(highScore)
        val fromJson = gson.fromJson(jsonString, HighScore::class.java)
        Assert.assertTrue(highScore.equals(fromJson))
    }
}
