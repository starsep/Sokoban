package com.starsep.sokoban.release.gamelogic.level

import com.starsep.sokoban.release.gamelogic.Move
import com.starsep.sokoban.release.gamelogic.Position
import com.starsep.sokoban.release.gamelogic.Tile

class Level(private val original: ImmutableLevel) : LevelAbstract() {
    override val tiles: CharArray = original.tiles.copyOf()
    override var player: Position = original.player.copy()
    override val width: Int = original.width

    private fun setTile(pos: Position, c: Char) {
        tiles[tileIndex(pos)] = c
    }

    private fun push(from: Position, to: Position) {
        val oldTile = tile(from)
        val newTile = tile(to)
        setTile(to, Tile.withCrate(newTile))
        setTile(from, Tile.withoutCrate(oldTile))
    }

    fun move(move: Move): Move? {
        val delta = move.toPosition()
        val movePos = player + delta
        val pushPos = movePos + delta
        if (isCrate(movePos) && canMove(pushPos)) {
            push(movePos, pushPos)
            player = movePos
            return Move.makePush(move)
        } else if (canMove(movePos)) {
            player = movePos
            return move
        }
        return null
    }

    fun undo(move: Move) {
        val delta = move.toPosition()
        if (move.push && isCrate(player + delta)) {
            push(player + delta, player)
        }
        player += move.reverse().toPosition()
    }

    override fun hashCode() = original.hashCode()

    override fun equals(other: Any?) = when (other) {
        is Level -> tiles.contentEquals(other.tiles) &&
            player == other.player &&
            width == other.width
        else -> false
    }
}

fun getDefaultImmutableLevel() = ImmutableLevel(
    tiles = ("####" + "#..#" + "####").toCharArray(),
    width = 4,
    player = Position(1, 1))

fun getDefaultLevel() = Level(getDefaultImmutableLevel())
