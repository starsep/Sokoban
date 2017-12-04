package com.starsep.sokoban.release.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.starsep.sokoban.release.model.GameState

@Dao
interface GameStateDao {
    @Query("SELECT * FROM gameState")
    fun getGameState() : List<GameState>

    @Insert
    fun insertGameState(gameState: GameState)

    @Query("DELETE FROM gameState")
    fun deleteAllGameState()
}
