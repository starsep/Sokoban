package com.starsep.sokoban.release.gamelogic

import android.content.Context
import com.starsep.sokoban.release.gamelogic.level.ImmutableLevel
import com.starsep.sokoban.release.gamelogic.level.LevelLoader
import com.starsep.sokoban.release.gamelogic.level.getDefaultImmutableLevel

object DefaultLevelLoader : LevelLoader {
    override fun load(context: Context, levelNumber: Int): ImmutableLevel = getDefaultImmutableLevel()
}