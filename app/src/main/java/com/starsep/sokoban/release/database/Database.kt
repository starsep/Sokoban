package com.starsep.sokoban.release.database

import android.arch.persistence.room.Room
import android.content.Context
import com.starsep.sokoban.release.gamelogic.HighScore
import com.starsep.sokoban.release.model.GameState

object Database {
    private lateinit var instance: DatabaseSchema
    private const val DATABASE_FILENAME = "Sokoban.sqlite"

    private fun db(ctx: Context): DatabaseSchema {
        if (!::instance.isInitialized) {
            instance = Room.databaseBuilder(ctx, DatabaseSchema::class.java, DATABASE_FILENAME)
                    .allowMainThreadQueries() // TODO: remove, make queries from other threads
                    .build()
        }
        return instance
    }

    fun highScore(ctx: Context, levelHash: Int, levelNumber: Int): HighScore? {
        return db(ctx)
                .highScoreDao()
                .getHighScore(levelHash, levelNumber)
                .firstOrNull()
    }

    private fun updateHighScore(ctx: Context, highScore: HighScore): Boolean {
        val oldScore = highScore(ctx, highScore.levelHash, highScore.levelNumber)
        if (oldScore != null) {
            oldScore.improve(highScore)
            db(ctx).highScoreDao().updateHighScore(highScore)
            return true
        }
        return false
    }

    fun addHighScore(ctx: Context, highScore: HighScore) {
        if (updateHighScore(ctx, highScore)) {
            return
        }
        db(ctx).highScoreDao().insertHighScore(highScore)
    }

    fun solvedLevels(ctx: Context): List<Int> {
        return db(ctx).highScoreDao().solvedLevels()
    }

    fun setCurrentGame(ctx: Context, gameState: GameState) {
        with(db(ctx).gameStateDao()) {
            deleteAllGameState()
            insertGameState(gameState)
        }
    }

    fun getCurrentGame(ctx: Context): GameState? {
        return db(ctx).gameStateDao().getGameState().firstOrNull()
    }

    fun close() {
        if (::instance.isInitialized) {
            instance.close()
        }
    }
}
