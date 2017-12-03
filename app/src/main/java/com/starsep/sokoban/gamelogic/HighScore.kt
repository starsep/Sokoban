package com.starsep.sokoban.gamelogic

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity

@Entity
data class HighScore(@ColumnInfo(name = "time") var time: Int = 0,
                     @ColumnInfo(name = "moves") var moves: Int = 0,
                     @ColumnInfo(name = "pushes") var pushes: Int = 0) {
    fun improve(another: HighScore) {
        time = Math.min(time, another.time)
        moves = Math.min(moves, another.moves)
        pushes = Math.min(pushes, another.pushes)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is HighScore) {
            return false
        }
        return time == other.time &&
                moves == other.moves &&
                pushes == other.pushes
    }

    override fun toString(): String {
        return "HighScore(" +
                "time=" + time + ", " +
                "movesLive=" + moves + ", " +
                "pushes=" + pushes + ")"
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }
}
