package com.starsep.sokoban.mvc

import android.content.Context

import com.starsep.sokoban.gamelogic.Gameplay

interface GameController {

    val context: Context
    fun onStatsChanged()

    fun onGamePause()

    fun onSaveGame(game: Gameplay)

    fun onNewGame()

    fun editMode(): Boolean
}
