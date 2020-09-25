package com.starsep.sokoban.release.model

import androidx.room.Entity
import com.starsep.sokoban.release.gamelogic.Move

@Entity(tableName = "gameState", primaryKeys = [("levelNumber")])
data class GameState(
    val time: Int,
    val levelNumber: Int,
    val movesList: String
) {
    companion object {
        fun createGameState(time: Int, levelNumber: Int, moves: List<Move>) = GameState(
            time = time,
            levelNumber = levelNumber,
            movesList = moves.joinToString("") { it.toString() }
        )
    }
}
