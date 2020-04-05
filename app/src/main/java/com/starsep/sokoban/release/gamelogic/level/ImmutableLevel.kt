package com.starsep.sokoban.release.gamelogic.level

import com.starsep.sokoban.release.gamelogic.Position

class ImmutableLevel(
    override val tiles: CharArray,
    override val width: Int,
    override val player: Position
) : LevelAbstract()
