package com.starsep.sokoban.gamelogic.level

import com.starsep.sokoban.gamelogic.Position

import java.util.Arrays

class ImmutableLevel(override val tiles: CharArray,
                     override val width: Int,
                     override val player: Position) : LevelAbstract()
