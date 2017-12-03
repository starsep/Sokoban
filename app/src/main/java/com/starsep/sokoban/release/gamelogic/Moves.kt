package com.starsep.sokoban.release.gamelogic

class Moves : ArrayList<Move> {
    constructor() : super()
    constructor(collection: Collection<Move>) : super() {
        addAll(collection)
    }
    override fun toString(): String {
        var result = ""
        for (move in this) {
            result += move.toString()
        }
        return result
    }
}
