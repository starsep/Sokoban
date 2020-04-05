package com.starsep.sokoban.release.gamelogic

import android.util.Log
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
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(Log::class)
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
                ).toLong(), tile.toLong()
            )
        }
    }

    @Test
    fun defaultMaskWalkable() {
        PowerMockito.mockStatic(Log::class.java)
        Assert.assertEquals(
            mask('~').toLong(),
            WALKABLE_MASK
        )
        PowerMockito.verifyStatic()
    }

    @Test
    fun defaultMaskToCharGrass() {
        PowerMockito.mockStatic(Log::class.java)
        Assert.assertEquals(
            maskToChar(1337).toLong(),
            grass
        )
        PowerMockito.verifyStatic()
    }
}