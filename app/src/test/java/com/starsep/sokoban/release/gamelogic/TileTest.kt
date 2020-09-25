package com.starsep.sokoban.release.gamelogic

import com.starsep.sokoban.release.gamelogic.Tile.WALKABLE_MASK
import com.starsep.sokoban.release.gamelogic.Tile.crate
import com.starsep.sokoban.release.gamelogic.Tile.crateOnEndpoint
import com.starsep.sokoban.release.gamelogic.Tile.endpoint
import com.starsep.sokoban.release.gamelogic.Tile.grass
import com.starsep.sokoban.release.gamelogic.Tile.ground
import com.starsep.sokoban.release.gamelogic.Tile.isCrate
import com.starsep.sokoban.release.gamelogic.Tile.isWalkable
import com.starsep.sokoban.release.gamelogic.Tile.mask
import com.starsep.sokoban.release.gamelogic.Tile.maskToChar
import com.starsep.sokoban.release.gamelogic.Tile.wall
import org.junit.Assert
import org.junit.Test

class TileTest {
    @Test
    fun crateIsCrate() {
        Assert.assertTrue(isCrate(crate))
    }

    @Test
    fun crateIsNotWalkable() {
        Assert.assertFalse(isWalkable(crate))
    }

    @Test
    fun groundAndGrassIsWalkable() {
        Assert.assertTrue(isWalkable(ground))
        Assert.assertTrue(isWalkable(grass))
    }

    @Test
    fun maskToMaskToCharIsIdentity() {
        val tiles = charArrayOf(
            crate,
            grass,
            ground,
            endpoint,
            crateOnEndpoint,
            wall
        )
        for (tile in tiles) {
            Assert.assertEquals(
                maskToChar(
                    mask(
                        tile
                    )
                ),
                tile
            )
        }
    }

    @Test
    fun defaultMaskWalkable() {
        Assert.assertEquals(
            mask('~'),
            WALKABLE_MASK
        )
    }

    @Test
    fun defaultMaskToCharGrass() {
        Assert.assertEquals(
            maskToChar(1337),
            grass
        )
    }
}
