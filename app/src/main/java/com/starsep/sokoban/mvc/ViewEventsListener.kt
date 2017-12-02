package com.starsep.sokoban.mvc

import com.starsep.sokoban.gamelogic.HighScore

interface ViewEventsListener : ContextGetter {
    fun onUpdate()

    fun showWinDialog(levelNumber: Int, stats: HighScore, highScore: HighScore)
}
