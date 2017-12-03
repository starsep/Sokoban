package com.starsep.sokoban.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.starsep.sokoban.Sokoban
import com.starsep.sokoban.gamelogic.*
import com.starsep.sokoban.gamelogic.level.Level
import com.starsep.sokoban.gamelogic.level.LevelLoader
import com.starsep.sokoban.gamelogic.level.getDefaultLevel
import java.io.IOException

class GameModel : ViewModel() {
    val statsLive: MutableLiveData<HighScore> = MutableLiveData()
    val levelNumberLive: MutableLiveData<Int> = MutableLiveData()
    val levelLive: MutableLiveData<Level> = MutableLiveData()
    val movesLive: MutableLiveData<Moves> = MutableLiveData()
    val wonLive: MutableLiveData<Boolean> = MutableLiveData()

    init {
        statsLive.value = HighScore()
        levelNumberLive.value = 1
        levelLive.value = getDefaultLevel()
        movesLive.value = Moves()
        wonLive.value = false
    }

    fun stats() : HighScore {
        return statsLive.value ?: HighScore()
    }

    fun levelNumber() : Int {
        return levelNumberLive.value ?: 0
    }

    fun level() : Level {
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

    fun repeatLevel(ctx: Context) {
        levelLive.value = try {
            Level(LevelLoader.load(ctx, levelNumber()))
        } catch (e: IOException) {
            Log.e(Sokoban.TAG, "Load error ${levelNumber()}.level) :<")
            getDefaultLevel()
        }
    }

    fun undoMove() {
        stats().moves--
        stats().pushes--
    }

    fun makeMove(move: Move) {
        val moveMade = level().move(move)
        moveMade?.let {
            addMove(moveMade)
            stats().moves++
            if (moveMade.push) {
                stats().pushes++
            }
            updateStats()
            updateLevel()
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
        repeatLevel(ctx)
    }

    private fun sendHighScore() {
        // TODO
//        val databaseManager = DatabaseManager.instance(viewListener!!.ctx)
//        databaseManager.addHighScore(statsLive)
    }

    private fun getHighScore(hash: Int): HighScore? {
        // TODO
//        val databaseManager = DatabaseManager.instance(viewListener!!.ctx)
//        return databaseManager.getHighScore(hash)
        return null
    }

    fun onSecondElapsed() {
        statsLive.value = HighScore()
    }
}
