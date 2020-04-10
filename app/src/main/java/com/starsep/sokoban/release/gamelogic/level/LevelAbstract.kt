package com.starsep.sokoban.release.gamelogic.level

import com.starsep.sokoban.release.gamelogic.Position
import com.starsep.sokoban.release.gamelogic.Tile
import com.starsep.sokoban.release.res.Textures

abstract class LevelAbstract {
    abstract val tiles: CharArray
    abstract val width: Int
    abstract val player: Position

    fun tileIndex(pos: Position) = pos.y * width + pos.x
    fun tile(pos: Position) = tiles[tileIndex(pos)]
    fun texture(pos: Position) = Textures.tile(tile(pos))

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

    fun valid() = countEndpoints() == countCrates()

    private fun count(tileType: Char) = tiles.count { it == tileType }
    private fun countCrates() = count(Tile.crate)
    private fun countEndpoints() = count(Tile.endpoint)

    fun won() = countCrates() == 0 && countEndpoints() == 0

    private fun validTile(pos: Position) = pos.x in 0 until width && pos.y in 0 until height()
    protected fun canMove(pos: Position) = validTile(pos) && Tile.isWalkable(tile(pos))
    protected fun isCrate(pos: Position) = validTile(pos) && Tile.isCrate(tile(pos))

    fun height() = tiles.size / width

    override fun equals(other: Any?) = when (other) {
        is ImmutableLevel -> width == other.width &&
            tiles.contentEquals(other.tiles)
        else -> false
    }

    fun positions(): List<Position> {
        val res = mutableListOf<Position>()
        for (y in 0 until height()) {
            (0 until width).mapTo(res) { Position(y, it) }
        }
        return res
    }

    override fun hashCode() = toString().hashCode()
}
