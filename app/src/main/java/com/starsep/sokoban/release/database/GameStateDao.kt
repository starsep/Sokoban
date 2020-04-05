package com.starsep.sokoban.release.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.starsep.sokoban.release.model.GameState

@Dao
interface GameStateDao {
    @Query("SELECT * FROM gameState")
    fun getGameState(): List<GameState>

    @Insert
    fun insertGameState(gameState: GameState)

    @Query("DELETE FROM gameState")
    fun deleteAllGameState()
}
