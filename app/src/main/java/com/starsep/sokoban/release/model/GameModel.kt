package com.starsep.sokoban.release.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.starsep.sokoban.release.Sokoban
import com.starsep.sokoban.release.database.Database
import com.starsep.sokoban.release.gamelogic.*
import com.starsep.sokoban.release.gamelogic.level.Level
import com.starsep.sokoban.release.gamelogic.level.LevelLoader
import com.starsep.sokoban.release.gamelogic.level.getDefaultLevel
import java.io.IOException

class GameModel : ViewModel() {
    val statsLive: MutableLiveData<HighScore> = MutableLiveData()
    val levelNumberLive: MutableLiveData<Int> = MutableLiveData()
    val levelLive: MutableLiveData<Level> = MutableLiveData()
    val movesLive: MutableLiveData<Moves> = MutableLiveData()
    val wonLive: MutableLiveData<Boolean> = MutableLiveData()

    init {
        resetStats()
        levelNumberLive.value = 1
        levelLive.value = getDefaultLevel()
        movesLive.value = Moves()
        updateWon()
    }

    fun stats(): HighScore {
        return statsLive.value ?: HighScore(levelNumber())
    }

    fun levelNumber(): Int {
        return levelNumberLive.value ?: 0
    }

    fun level(): Level {
        return levelLive.value ?: getDefaultLevel()
    }

    fun player(): Position {
        return level().player
    }

    fun moves(): Moves {
        return movesLive.value ?: Moves()
    }

    private fun addMove(move: Move) {
        movesLive.value = Moves(moves().plus(move))
    }

    fun lastMove(): Move? {
        return if (moves().isNotEmpty()) {
            moves().last()
        } else {
            null
        }
    }

    @Throws(Move.UnknownMoveException::class)
    fun makeMoves(movesList: String) {
        for (c in movesList.toCharArray()) {
            makeMove(Move.fromChar(c))
        }
    }

    fun resetLevel(ctx: Context) {
        levelLive.value = try {
            Level(LevelLoader.load(ctx, levelNumber()))
        } catch (e: IOException) {
            Log.e(Sokoban.TAG, "Load error ${levelNumber()}.level) :<")
            getDefaultLevel()
        }
        resetStats()
    }

    private fun resetStats() {
        statsLive.value = HighScore(levelNumber())
    }

    fun undoMove() {
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
        val moveMade = level().move(move)
        moveMade?.let {
            addMove(moveMade)
            stats().moves++
            if (moveMade.push) {
                stats().pushes++
            }
            afterMove()
        }
    }

    private fun updateWon() {
        val won = level().won()
        if (wonLive.value != won) {
            wonLive.value = won
        }
    }

    fun moveUp() {
        makeMove(Move.UP)
    }

    fun moveDown() {
        makeMove(Move.DOWN)
    }

    fun moveLeft() {
        makeMove(Move.LEFT)
    }

    fun moveRight() {
        makeMove(Move.RIGHT)
    }

    private fun updateStats() {
        statsLive.value = stats()
    }

    private fun updateLevel() {
        levelLive.value = level()
    }

    fun nextLevel(ctx: Context) {
        levelNumberLive.value = levelNumber() + 1
        resetLevel(ctx)
    }

    fun sendHighScore(ctx: Context) {
        stats().levelHash = levelHash()
        stats().levelNumber = levelNumber()
        Database.addHighScore(ctx, stats())
    }

    fun highScore(ctx: Context): HighScore? {
        return Database.highScore(ctx, level().hashCode(), levelNumber())
    }

    fun onSecondElapsed() {
        stats().time++
        updateStats()
    }

    fun startLevel(ctx: Context, levelNumber: Int) {
        levelNumberLive.value = levelNumber
        resetLevel(ctx)
    }

    private fun levelHash(): Int {
        return level().hashCode()
    }

    fun setTime(time: Int) {
        resetStats()
        stats().time = time
    }

    fun gameState(): GameState {
        return GameState(stats().time, levelNumber(), moves().toString())
    }
}
