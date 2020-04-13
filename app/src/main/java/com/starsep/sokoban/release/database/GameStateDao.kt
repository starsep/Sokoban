package com.starsep.sokoban.release.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.starsep.sokoban.release.model.GameState

@Dao
interface GameStateDao {
    @Query("SELECT * FROM gameState")
    suspend fun getGameState(): List<GameState>

    @Insert
    suspend fun insertGameState(gameState: GameState)

    @Query("DELETE FROM gameState")
    suspend fun deleteAllGameState()

    @Transaction
    suspend fun setCurrentGame(gameState: GameState) {
        deleteAllGameState()
        insertGameState(gameState)
    }

    suspend fun getCurrentGame() = getGameState().firstOrNull()
}
