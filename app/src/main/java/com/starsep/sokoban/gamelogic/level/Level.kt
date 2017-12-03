package com.starsep.sokoban.gamelogic.level

import com.starsep.sokoban.gamelogic.Move
import com.starsep.sokoban.gamelogic.Position
import com.starsep.sokoban.gamelogic.Tile

class Level(original: ImmutableLevel) : LevelAbstract() {
    override var player: Position = original.player.copyOf()
    override val width: Int = original.width
    override val tiles: CharArray = original.tiles.copyOf()
    private fun setTile(pos: Position, c: Char) {
        tiles[tileIndex(pos)] = c
    }

    private fun push(from: Position, to: Position, undo: Boolean = false) {
        val oldTile = tile(from)
        val newTile = tile(to)
        setTile(to, Tile.withCrate(newTile))
        setTile(from, Tile.withoutCrate(oldTile))
    }

    fun move(move: Move) : Move? {
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
}

fun getDefaultLevel(): Level {
    return Level(ImmutableLevel(("###" + "#.#" + "###").toCharArray(),
            3,
            Position(1, 1)))
}