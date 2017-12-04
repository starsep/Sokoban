package com.starsep.sokoban.release.model

import android.arch.persistence.room.Entity

@Entity(tableName = "gameState", primaryKeys = [("levelNumber")])
data class GameState(
        val time: Int,
        val levelNumber: Int,
        val movesList: String)