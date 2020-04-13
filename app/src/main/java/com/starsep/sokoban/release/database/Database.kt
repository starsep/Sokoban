package com.starsep.sokoban.release.database

import android.content.Context
import androidx.room.Room

object Database {
    private lateinit var db: DatabaseSchema
    private const val DATABASE_FILENAME = "Sokoban.sqlite"

    val highScoreDao get() = db.highScoreDao()
    val gameStateDao get() = db.gameStateDao()

    fun initDb(ctx: Context) {
        if (!::db.isInitialized) {
            db = Room.databaseBuilder(ctx, DatabaseSchema::class.java, DATABASE_FILENAME).build()
        }
    }

    fun close() {
        if (::db.isInitialized) {
            db.close()
        }
    }
}
