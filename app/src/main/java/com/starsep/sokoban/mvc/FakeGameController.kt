package com.starsep.sokoban.mvc

import android.content.Context

import com.starsep.sokoban.gamelogic.Gameplay

class FakeGameController(override val context: Context) : GameController {
    override fun onStatsChanged() {

    }

    override fun onGamePause() {

    }

    override fun onSaveGame(game: Gameplay) {

    }

    override fun onNewGame() {

    }

    override fun editMode(): Boolean {
        return false
    }
}