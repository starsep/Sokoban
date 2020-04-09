package com.starsep.sokoban.release.gamelogic.level

import android.content.Context

interface LevelLoader {
    fun load(context: Context, levelNumber: Int): ImmutableLevel
}