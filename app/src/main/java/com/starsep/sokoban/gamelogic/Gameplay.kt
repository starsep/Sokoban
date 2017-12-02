package com.starsep.sokoban.gamelogic

import android.util.Log

import com.starsep.sokoban.Sokoban
import com.starsep.sokoban.database.DatabaseManager
import com.starsep.sokoban.mvc.GameController
import com.starsep.sokoban.mvc.GameModel
import com.starsep.sokoban.mvc.ViewEventsListener

import java.io.IOException

class Gameplay(levelNumber: Int, override var gameController: GameController) : GameModel {
    override var viewListener: ViewEventsListener? = null
        set(value) {
            field = value
            updateView()
        }
    private lateinit var stats: HighScore
    private lateinit var currentLevel: Level

    init {
        loadLevel(levelNumber)
    }

    private fun saveGame() {
        gameController.onSaveGame(this)
    }

    private fun onNewGame() {
        gameController.onNewGame()
    }

    private fun loadLevel(number: Int) {
        if (gameController.editMode()) {
            currentLevel = Level.getDefaultLevel(number)
            return
        }
        currentLevel = try {
            LevelLoader.load(gameController.ctx,
                    "levels/$number.level", this, number)
        } catch (e: IOException) {
            Log.e(Sokoban.TAG, "Load error ($number.level) :<")
            Level.getDefaultLevel(number)
        }

        stats = HighScore(currentLevel.hash(), currentLevel.number(), 0, 0, 0)
        saveGame()
        onNewGame()
    }

    private fun updateView() {
        viewListener?.onUpdate()
    }

    override fun onPush() {
        stats.pushes++
    }

    override fun onMove() {
        stats.moves++
        saveGame()
        updateView()
    }

    override fun onWin() {
        // Log.d(Sokoban.TAG, getHighScore(currentLevel.hash()).toString());
        if (viewListener != null) {
            pauseGame()
            viewListener?.showWinDialog(level().number(), stats, getHighScore(level().hash())!!)
            sendHighScore()
        } else {
            nextLevel()
        }
    }

    override fun moveUp() {
        level().move(Move.UP)
    }

    override fun moveDown() {
        level().move(Move.DOWN)
    }

    override fun moveLeft() {
        level().move(Move.LEFT)
    }

    override fun moveRight() {
        level().move(Move.RIGHT)
    }

    override fun repeatLevel() {
        loadLevel(level().number())
        updateView()
    }

    override fun undoMove() {
        level().undoMove()
        updateView()
    }

    override fun stats(): HighScore {
        return stats
    }

    override fun player(): Position {
        return level().player()
    }

    override fun level(): Level {
        return currentLevel
    }

    override fun nextLevel() {
        if (level().won()) {
            loadLevel(level().number() + 1)
            updateView()
        }
    }

    override fun lastMove(): Move {
        return level().lastMove()
    }

    override fun onUndoMove() {
        stats().moves--
        updateView()
    }

    override fun onUndoPush() {
        stats().pushes--
        updateView()
    }

    override fun onSecondElapsed() {
        stats.time++
        updateView()
    }

    private fun sendHighScore() {
        val databaseManager = DatabaseManager.instance(viewListener!!.ctx)
        databaseManager.addHighScore(stats)
    }

    private fun getHighScore(hash: Int): HighScore? {
        val databaseManager = DatabaseManager.instance(viewListener!!.ctx)
        return databaseManager.getHighScore(hash)
    }

    private fun pauseGame() {
        gameController.onGamePause()
    }

    fun movesString(): String {
        return level().movesString()
    }

    @Throws(Move.UnknownMoveException::class)
    fun makeMoves(moves: String) {
        level().makeMoves(moves)
    }
}
