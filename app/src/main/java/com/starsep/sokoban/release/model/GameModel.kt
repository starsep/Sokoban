package com.starsep.sokoban.release.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.starsep.sokoban.release.controls.ControlListener
import com.starsep.sokoban.release.database.Database
import com.starsep.sokoban.release.gamelogic.*
import com.starsep.sokoban.release.gamelogic.level.Level
import com.starsep.sokoban.release.gamelogic.level.LevelLoader
import com.starsep.sokoban.release.gamelogic.level.ResourceLevelLoader
import com.starsep.sokoban.release.gamelogic.level.getDefaultLevel
import com.starsep.sokoban.release.model.GameState.Companion.createGameState
import java.io.IOException
import timber.log.Timber

class GameModel(private val context: Context, private val levelLoader: LevelLoader = ResourceLevelLoader) : ViewModel(), ControlListener {
    val statsLive: MutableLiveData<HighScore> = MutableLiveData()
    val levelNumberLive: MutableLiveData<Int> = MutableLiveData()
    val levelLive: MutableLiveData<Level> = MutableLiveData()
    val movesLive: MutableLiveData<ArrayList<Move>> = MutableLiveData()
    val wonLive: MutableLiveData<Boolean> = MutableLiveData()

    init {
        resetStats()
        levelNumberLive.value = 1
        levelLive.value = getDefaultLevel()
        movesLive.value = ArrayList()
        updateWon()
    }

    fun stats() = statsLive.value ?: HighScore(levelNumber())
    fun levelNumber() = levelNumberLive.value ?: 0
    fun level() = levelLive.value ?: getDefaultLevel()
    fun player() = level().player
    fun moves() = movesLive.value ?: ArrayList()

    private fun addMove(move: Move) {
        movesLive.value = ArrayList(moves().plus(move))
    }

    fun lastMove() = moves().lastOrNull()

    @Throws(Move.UnknownMoveException::class)
    fun makeMoves(movesList: String) {
        for (c in movesList.toCharArray()) {
            makeMove(Move.fromChar(c))
        }
    }

    override fun onResetLevel() {
        levelLive.value = try {
            Level(levelLoader.load(context, levelNumber()))
        } catch (e: IOException) {
            Timber.e("Load error ${levelNumber()}.level) :<")
            getDefaultLevel()
        }
        resetStats()
        movesLive.value = ArrayList()
    }

    private fun resetStats() {
        statsLive.value = HighScore(levelNumber())
    }

    override fun onUndoMove() {
        val toUndo = lastMove()
        toUndo?.let {
            moves().removeAt(moves().lastIndex)
            level().undo(toUndo)
            stats().moves--
            if (toUndo.push) {
                stats().pushes--
            }
            afterMove()
        }
    }

    private fun afterMove() {
        updateWon()
        updateStats()
        updateLevel()
    }

    fun makeMove(move: Move) {
        val moveMade = level().move(move) ?: return
        addMove(moveMade)
        stats().moves++
        if (moveMade.push) {
            stats().pushes++
        }
        afterMove()
    }

    private fun updateWon() {
        val won = level().won()
        if (wonLive.value != won) {
            wonLive.value = won
        }
    }

    override fun onMoveUp() = makeMove(Move.UP)
    override fun onMoveDown() = makeMove(Move.DOWN)
    override fun onMoveLeft() = makeMove(Move.LEFT)
    override fun onMoveRight() = makeMove(Move.RIGHT)

    private fun updateStats() {
        statsLive.value = stats()
    }

    private fun updateLevel() {
        levelLive.value = level()
    }

    fun nextLevel() {
        levelNumberLive.value = levelNumber() + 1
        onResetLevel()
    }

    fun sendHighScore() {
        stats().levelHash = levelHash()
        stats().levelNumber = levelNumber()
        Database.addHighScore(stats())
    }

    fun highScore() = Database.highScore(level().hashCode(), levelNumber())

    fun onSecondElapsed() {
        stats().time++
        updateStats()
    }

    fun startLevel(levelNumber: Int) {
        levelNumberLive.value = levelNumber
        onResetLevel()
    }

    private fun levelHash() = level().hashCode()

    fun setTime(time: Int) {
        resetStats()
        stats().time = time
    }

    fun gameState() = createGameState(stats().time, levelNumber(), moves())
}
