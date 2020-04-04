package com.starsep.sokoban.release.gamelogic

import android.util.Log

import com.starsep.sokoban.release.Sokoban

object Tile {
    val WALKABLE_MASK = 0
    val wall = 'W'
    val crate = '#'
    val ground = '.'
    val endpoint = 'X'
    val grass = '_'
    val crateOnEndpoint = '&'
    private val GRASS_MASK = 0
    private val SOLID_MASK = 1
    private val GROUND_MASK = 2
    private val ENDPOINT_MASK = 4
    private val CRATE_MASK = 8

    fun isWalkable(c: Char): Boolean {
        return mask(c) and SOLID_MASK == WALKABLE_MASK
    }

    fun isCrate(c: Char): Boolean {
        return mask(c) and CRATE_MASK == CRATE_MASK
    }

    fun mask(c: Char): Int {
        return when (c) {
            wall -> SOLID_MASK
            crate -> GROUND_MASK or CRATE_MASK or SOLID_MASK
            ground -> GROUND_MASK
            endpoint -> GROUND_MASK or ENDPOINT_MASK
            grass -> GRASS_MASK
            crateOnEndpoint -> mask(endpoint) or mask(crate)
            else -> {
                Log.e(Sokoban.TAG, "Tile.mask: " + "Unknown tile " + c)
                WALKABLE_MASK
            }
        }
    }

    fun maskToChar(m: Int): Char {
        val tiles = charArrayOf(wall, crate, ground, endpoint, grass, crateOnEndpoint)
        tiles
                .filter { m == mask(it) }
                .forEach { return it }
        Log.e(Sokoban.TAG, "Tile.maskToChar: " + "Unknown mask " + m)
        return grass
    }

    fun isGrass(c: Char): Boolean {
        return c == grass
    }

    private fun crateMask(): Int {
        return Tile.CRATE_MASK or Tile.SOLID_MASK
    }

    fun withoutCrate(tile: Char): Char {
        return maskToChar(mask(tile) xor crateMask())
    }

    fun withCrate(tile: Char): Char {
        return maskToChar(mask(tile) or crateMask())
    }


}
