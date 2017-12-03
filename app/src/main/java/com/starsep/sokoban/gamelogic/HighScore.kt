package com.starsep.sokoban.gamelogic

class HighScore(val hash: Int = 0, val levelNumber: Int = 0, var time: Int = 0, var moves: Int = 0, var pushes: Int = 0) {

    @Throws(HighScore.DifferentLevelsException::class)
    fun improve(another: HighScore) {
        if (hash != another.hash) {
            throw DifferentLevelsException()
        }
        time = Math.min(time, another.time)
        moves = Math.min(moves, another.moves)
        pushes = Math.min(pushes, another.pushes)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is HighScore) {
            return false
        }
        return levelNumber == other.levelNumber &&
                time == other.time &&
                moves == other.moves &&
                pushes == other.pushes &&
                hash == other.hash
    }

    override fun toString(): String {
        return "HighScore(" +
                "level=" + levelNumber + ", " +
                "hash=" + hash + ", " +
                "time=" + time + ", " +
                "movesLive=" + moves + ", " +
                "pushes=" + pushes + ")"
    }

    inner class DifferentLevelsException : Exception()
}