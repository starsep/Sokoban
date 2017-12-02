package com.starsep.sokoban.gamelogic

internal object LevelConverter {
    abstract class LevelConverterException : Exception()

    class NoPlayerTileException : LevelConverterException()

    class ManyPlayerTilesException : LevelConverterException()

    class UnknownTileException : LevelConverterException()

    @Throws(LevelConverter.LevelConverterException::class)
    fun convert(data: CharArray, width: Int, number: Int): Level {
        val player = findPlayer(data, width)
        convertTiles(data)
        return Level(data, width, player, number)
    }

    @Throws(LevelConverter.LevelConverterException::class)
    private fun convertTiles(data: CharArray) {
        for (i in data.indices) {
            data[i] = tileMapping(data[i])
        }
    }

    object CommonLevelFormat {
        val wall = '#'
        val player = '@'
        val playerOnEndpoint = '+'
        val crate = '$'
        val crateOnEndpoint = '*'
        val endpoint = '.'
        val floor = ' '
    }

    private fun tileWithoutPlayer(tile: Char): Char {
        return when (tile) {
            CommonLevelFormat.player -> CommonLevelFormat.floor
            CommonLevelFormat.playerOnEndpoint -> CommonLevelFormat.endpoint
            else -> tile
        }
    }

    @Throws(LevelConverter.LevelConverterException::class)
    private fun tileMapping(tile: Char): Char {
        return when (tile) {
            CommonLevelFormat.crate -> Tile.crate
            CommonLevelFormat.crateOnEndpoint -> Tile.crateOnEndpoint
            CommonLevelFormat.endpoint -> Tile.endpoint
            CommonLevelFormat.floor -> Tile.ground
            CommonLevelFormat.wall -> Tile.wall
            else -> throw UnknownTileException()
        }
    }

    private fun isPlayerTile(tile: Char): Boolean {
        return tileWithoutPlayer(tile) != tile
    }

    @Throws(LevelConverter.NoPlayerTileException::class, LevelConverter.ManyPlayerTilesException::class)
    private fun findPlayer(data: CharArray, width: Int): Position {
        var result: Position? = null
        for (i in data.indices) {
            if (isPlayerTile(data[i])) {
                if (result != null) {
                    throw ManyPlayerTilesException()
                }
                data[i] = tileWithoutPlayer(data[i])
                result = Position(i / width, i % width)
            }
        }
        if (result == null) {
            throw NoPlayerTileException()
        }
        return result
    }
}
