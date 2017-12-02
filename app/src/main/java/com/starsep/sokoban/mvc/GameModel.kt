package com.starsep.sokoban.mvc

import com.starsep.sokoban.gamelogic.HighScore
import com.starsep.sokoban.gamelogic.Level
import com.starsep.sokoban.gamelogic.Move
import com.starsep.sokoban.gamelogic.Position

interface GameModel {
    fun onPush()

    fun onMove()

    fun onWin()

    fun moveUp()

    fun moveDown()

    fun moveLeft()

    fun moveRight()

    fun repeatLevel()

    fun undoMove()

    fun stats(): HighScore

    fun player(): Position

    fun level(): Level

    fun nextLevel()

    fun lastMove(): Move

    fun onUndoMove()

    fun onUndoPush()

    fun onSecondElapsed()
}
