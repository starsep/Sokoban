package com.starsep.sokoban.mvc

import com.starsep.sokoban.gamelogic.HighScore
import com.starsep.sokoban.gamelogic.Level
import com.starsep.sokoban.gamelogic.Move
import com.starsep.sokoban.gamelogic.Position

open class FakeGameModel : GameModel {
    override fun onPush() {

    }

    override fun onMove() {

    }

    override fun onWin() {

    }

    override fun moveUp() {

    }

    override fun moveDown() {

    }

    override fun moveLeft() {

    }

    override fun moveRight() {

    }

    override fun repeatLevel() {

    }

    override fun undoMove() {

    }

    override fun stats(): HighScore {
        return HighScore(0, 0, 0, 0, 0)
    }

    override fun player(): Position {
        return Position()
    }

    override fun level(): Level {
        return Level.getDefaultLevel(0)
    }

    override fun nextLevel() {

    }

    override fun lastMove(): Move {
        return Move.DOWN
    }

    override fun onUndoMove() {

    }

    override fun onUndoPush() {

    }

    override fun onSecondElapsed() {

    }
}
