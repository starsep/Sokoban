package com.starsep.sokoban.gamelogic

import android.graphics.Bitmap

import com.starsep.sokoban.mvc.GameModel
import com.starsep.sokoban.res.Textures

import java.util.ArrayList
import java.util.Arrays

class Level(data: CharArray, val width: Int, player: Position, private val number: Int) {
    val tiles: CharArray = CharArray(data.size)
    val player: Position
    private val hash: Int
    private val moves: MutableList<Move>
    @Transient var gameModel: GameModel? = null

    init {
        System.arraycopy(data, 0, tiles, 0, data.size)
        this.player = Position(player.y, player.x)
        hash = toString().hashCode()
        moves = ArrayList()
    }

    fun texture(y: Int, x: Int): Bitmap {
        return Textures.tile(tile(y, x))
    }

    fun height(): Int {
        return tiles.size / width
    }

    private fun makeMove(move: Move, undo: Boolean = false) {
        player.x += move.dx()
        player.y += move.dy()
        if (!undo) {
            addMove(move)
            gameModel?.onMove()
            if (won()) {
                gameModel?.onWin()
            }
        } else {
            gameModel?.onUndoMove()
        }
    }

    private fun addMove(move: Move) {
        moves.add(move)
    }

    private fun push(x: Int, y: Int, newX: Int, newY: Int, undo: Boolean = false) {
        val oldTile = tile(y, x)
        val newTile = tile(newY, newX)
        setTile(newY, newX, Tile.withCrate(newTile))
        setTile(y, x, Tile.withoutCrate(oldTile))
        if (undo) {
            gameModel?.onUndoPush()
        } else {
            gameModel?.onPush()
        }
    }

    private fun setTile(y: Int, x: Int, c: Char) {
        tiles[tileIndex(y, x)] = c
    }

    private fun validTile(x: Int, y: Int): Boolean {
        return x in 0..(width - 1) && y >= 0 && y < height()
    }

    private fun canMove(x: Int, y: Int): Boolean {
        return validTile(x, y) && Tile.isWalkable(tile(y, x))
    }

    private fun isCrate(x: Int, y: Int): Boolean {
        return validTile(x, y) && Tile.isCrate(tile(y, x))
    }

    fun won(): Boolean {
        return countCrates() == 0 && countEndpoints() == 0
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

    fun valid(): Boolean {
        return countEndpoints() == countCrates()
    }

    override fun toString(): String {
        var result = "" + height() + ' ' + width + '\n' +
                player.y + ' ' + player.x + '\n'
        for (i in 0 until height()) {
            for (j in 0 until width) {
                result += tile(i, j)
            }
            result += "\n"
        }
        return result
    }

    fun hash(): Int {
        return hash
    }

    private fun tileIndex(y: Int, x: Int): Int {
        return y * width + x
    }

    fun tile(y: Int, x: Int): Char {
        return tiles[tileIndex(y, x)]
    }

    fun undoMove() {
        if (moves.isEmpty()) {
            return
        }
        val toUndo = moves[moves.size - 1]
        moves.removeAt(moves.size - 1)
        if (toUndo.push) {
            push(player.x + toUndo.dx(), player.y + toUndo.dy(), player.x, player.y, true)
        }
        makeMove(toUndo.reverse(), true)
    }

    fun lastMove(): Move {
        return if (moves.isEmpty()) {
            Move.DOWN
        } else moves[moves.size - 1]
    }

    fun move(move: Move) {
        val x = player.x + move.dx()
        val y = player.y + move.dy()
        if (isCrate(x, y) && canMove(x + move.dx(), y + move.dy())) {
            push(x, y, x + move.dx(), y + move.dy())
            makeMove(Move.makePush(move))
        } else if (canMove(x, y)) {
            makeMove(move)
        }
    }

    fun movesString(): String {
        var result = ""
        for (move in moves) {
            result += move.toString()
        }
        return result
    }

    @Throws(Move.UnknownMoveException::class)
    fun makeMoves(movesList: String) {
        for (c in movesList.toCharArray()) {
            move(Move.fromChar(c))
        }
    }

    fun number(): Int {
        return number
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Level) {
            return false
        }
        return number == other.number &&
                width == other.width &&
                Arrays.equals(tiles, other.tiles) &&
                moves == other.moves
    }

    companion object {

        fun getDefaultLevel(levelNumber: Int): Level {
            val data = ("###" + "#.#" + "###").toCharArray()
            return Level(data, 3, Position(1, 1), levelNumber)
        }
    }
}
