package com.starsep.sokoban.release.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.starsep.sokoban.release.gamelogic.HighScore

@Dao
interface HighScoreDao {
    @Query("SELECT * FROM highscore WHERE levelHash = :levelHash AND levelNumber = :levelNumber")
    fun getHighScore(levelHash: Int, levelNumber: Int): List<HighScore>

    @Query("SELECT levelNumber FROM highscore")
    fun solvedLevels(): List<Int>

    @Update
    fun updateHighScore(highScore: HighScore)

    @Insert
    fun insertHighScore(highScore: HighScore)
}