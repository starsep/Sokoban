package com.starsep.sokoban.release.gamelogic.level

import android.graphics.Bitmap
import com.starsep.sokoban.release.gamelogic.Position
import com.starsep.sokoban.release.gamelogic.Tile
import com.starsep.sokoban.release.res.Textures
import java.util.*

abstract class LevelAbstract {
    abstract val tiles: CharArray
    abstract val width: Int
    abstract val player: Position

    fun tileIndex(pos: Position): Int {
        val (y, x) = pos
        return y * width + x
    }

    fun tile(pos: Position): Char {
        return tiles[tileIndex(pos)]
    }

    fun texture(pos: Position): Bitmap {
        return Textures.tile(tile(pos))
    }

    override fun toString(): String {
        var result = "" + height() + ' ' + width + '\n' +
                player.y + ' ' + player.x + '\n'
        for (i in 0 until height()) {
            for (j in 0 until width) {
                result += tile(Position(i, j))
            }
            result += "\n"
        }
        return result
    }

    fun valid(): Boolean {
        return countEndpoints() == countCrates()
    }

    private fun count(tileType: Char): Int {
        return tiles.count { it == tileType }
    }

    private fun countCrates(): Int {
        return count(Tile.crate)
    }

    private fun countEndpoints(): Int {
        return count(Tile.endpoint)
    }

    fun won(): Boolean {
        return countCrates() == 0 && countEndpoints() == 0
    }

    private fun validTile(pos: Position): Boolean {
        val (y, x) = pos
        return x in 0 until width && y in 0 until height()
    }

    protected fun canMove(pos: Position): Boolean {
        return validTile(pos) && Tile.isWalkable(tile(pos))
    }

    protected fun isCrate(pos: Position): Boolean {
        return validTile(pos) && Tile.isCrate(tile(pos))
    }

    fun height(): Int {
        return tiles.size / width
    }

    override fun equals(other: Any?): Boolean {
        if (other !is ImmutableLevel) {
            return false
        }
        return width == other.width &&
                Arrays.equals(tiles, other.tiles)
    }

    fun positions(): List<Position> {
        val res = mutableListOf<Position>()
        for (y in 0 until height()) {
            (0 until width).mapTo(res) { Position(y, it) }
        }
        return res
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }
}
