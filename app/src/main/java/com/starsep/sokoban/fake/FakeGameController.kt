package com.starsep.sokoban.fake

import android.content.Context
import com.starsep.sokoban.gamelogic.Gameplay

import com.starsep.sokoban.mvc.GameController

class FakeGameController(override val ctx: Context) : GameController {
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