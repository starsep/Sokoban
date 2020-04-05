package com.starsep.sokoban.release.database

import android.content.Context
import androidx.room.Room
import com.starsep.sokoban.release.gamelogic.HighScore
import com.starsep.sokoban.release.model.GameState

object Database {
    private lateinit var instance: DatabaseSchema
    private const val DATABASE_FILENAME = "Sokoban.sqlite"

    fun initDb(ctx: Context) {
        if (!::instance.isInitialized) {
            instance = Room.databaseBuilder(ctx, DatabaseSchema::class.java, DATABASE_FILENAME)
                    .allowMainThreadQueries() // TODO: remove, make queries from other threads
                    .build()
        }
    }

    private val db: DatabaseSchema get() = instance

    fun highScore(levelHash: Int, levelNumber: Int) = db
        .highScoreDao()
        .getHighScore(levelHash, levelNumber)
        .firstOrNull()

    private fun updateHighScore(highScore: HighScore): Boolean {
        val oldScore = highScore(highScore.levelHash, highScore.levelNumber)
        if (oldScore != null) {
            oldScore.improve(highScore)
            db.highScoreDao().updateHighScore(highScore)
            return true
        }
        return false
    }

    fun addHighScore(highScore: HighScore) {
        if (updateHighScore(highScore)) {
            return
        }
        db.highScoreDao().insertHighScore(highScore)
    }

    fun solvedLevels() = db.highScoreDao().solvedLevels()

    fun setCurrentGame(ctx: Context, gameState: GameState) {
        with(db.gameStateDao()) {
            deleteAllGameState()
            insertGameState(gameState)
        }
    }

    fun getCurrentGame() = db.gameStateDao().getGameState().firstOrNull()

    fun close() {
        if (::instance.isInitialized) {
            instance.close()
        }
    }
}
