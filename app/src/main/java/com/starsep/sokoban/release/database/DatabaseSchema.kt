package com.starsep.sokoban.release.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.starsep.sokoban.release.gamelogic.HighScore
import com.starsep.sokoban.release.model.GameState

@Database(entities = [(GameState::class), (HighScore::class)], version = 1)
abstract class DatabaseSchema : RoomDatabase() {
    abstract fun gameStateDao(): GameStateDao
    abstract fun highScoreDao(): HighScoreDao
}
