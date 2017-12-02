package com.starsep.sokoban.mvc

import com.starsep.sokoban.gamelogic.Gameplay

interface GameController : ContextGetter {
    fun onStatsChanged()

    fun onGamePause()

    fun onSaveGame(game: Gameplay)

    fun onNewGame()

    fun editMode(): Boolean
}
