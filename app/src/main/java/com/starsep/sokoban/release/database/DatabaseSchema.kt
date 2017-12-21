package com.starsep.sokoban.release.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.starsep.sokoban.release.gamelogic.HighScore
import com.starsep.sokoban.release.model.GameState

@Database(entities = [(GameState::class), (HighScore::class)], version = 1)
abstract class DatabaseSchema : RoomDatabase() {
    abstract fun gameStateDao(): GameStateDao
    abstract fun highScoreDao(): HighScoreDao
}