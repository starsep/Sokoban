package com.starsep.sokoban.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.starsep.sokoban.Sokoban
import com.starsep.sokoban.fake.FakeGameController
import com.starsep.sokoban.gamelogic.Gameplay
import com.starsep.sokoban.gamelogic.HighScore
import com.starsep.sokoban.gamelogic.Move
import java.util.*

class DatabaseManager private constructor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val solvedLevels: List<Int>
        get() {
            val db = readableDatabase
            val selectLevelsSolved = "SELECT " + COLUMN_LEVEL_NUMBER + " FROM " + TABLE_HIGH_SCORES +
                    " WHERE " + TRUE_CONDITION + ";"
            val cursor = db.rawQuery(selectLevelsSolved, null)
            val result = ArrayList<Int>()
            while (cursor.moveToNext()) {
                result.add(cursor.getInt(cursor.getColumnIndex(COLUMN_LEVEL_NUMBER)))
            }
            db.close()
            cursor.close()
            return result
        }


    private fun createTableCurrentGame(db: SQLiteDatabase) {
        val query = "CREATE TABLE " + TABLE_CURRENT_GAME + "(" +
                COLUMN_ID + " " + TYPE_ID + ", " +
                COLUMN_HASH + " " + TYPE_INTEGER + ", " +
                COLUMN_LEVEL_NUMBER + " " + TYPE_INTEGER + ", " +
                COLUMN_TIME + " " + TYPE_INTEGER + ", " +
                COLUMN_MOVES_LIST + " " + TYPE_TEXT +
                ");"
        db.execSQL(query)
    }

    private fun createTableHighScores(db: SQLiteDatabase) {
        val query = "CREATE TABLE " + TABLE_HIGH_SCORES + "(" +
                COLUMN_ID + " " + TYPE_ID + ", " +
                COLUMN_HASH + " " + TYPE_INTEGER + ", " +
                COLUMN_LEVEL_NUMBER + " " + TYPE_INTEGER + ", " +
                COLUMN_TIME + " " + TYPE_INTEGER + ", " +
                COLUMN_MOVES + " " + TYPE_INTEGER + ", " +
                COLUMN_PUSHES + " " + TYPE_INTEGER +
                ");"
        db.execSQL(query)
    }

    override fun onCreate(db: SQLiteDatabase) {
        createTableHighScores(db)
        createTableCurrentGame(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        migrateTableHighScores(db, oldVersion, newVersion)
        migrateTableCurrentGame(db, oldVersion, newVersion)
    }

    private fun migrateTableHighScores(db: SQLiteDatabase,
                                       oldVersion: Int,
                                       newVersion: Int) {
        // TODO: migrate data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGH_SCORES)
        createTableHighScores(db)
    }

    private fun migrateTableCurrentGame(db: SQLiteDatabase,
                                        oldVersion: Int,
                                        newVersion: Int) {
        // TODO: migrate data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENT_GAME)
        createTableCurrentGame(db)
    }

    fun getHighScore(hash: Int): HighScore? {
        val db = readableDatabase
        val selectHighScore = "SELECT * FROM " + TABLE_HIGH_SCORES + " WHERE " +
                COLUMN_HASH + " = " + hash + ";"
        val cursor = db.rawQuery(selectHighScore, null)
        var result: HighScore? = null
        if (cursor.moveToFirst()) {
            val moves = cursor.getInt(cursor.getColumnIndex(COLUMN_MOVES))
            val pushes = cursor.getInt(cursor.getColumnIndex(COLUMN_PUSHES))
            val time = cursor.getInt(cursor.getColumnIndex(COLUMN_TIME))
            val level = cursor.getInt(cursor.getColumnIndex(COLUMN_LEVEL_NUMBER))
            result = HighScore(hash, level, time, moves, pushes)
        }
        db.close()
        cursor.close()
        return result
    }

    private fun updateHighScore(highScore: HighScore): Boolean {
        val oldScore = getHighScore(highScore.hash)
        if (oldScore != null) {
            try {
                oldScore.improve(highScore)
            } catch (e: HighScore.DifferentLevelsException) {
                Log.e(Sokoban.TAG, "Got result from another Level")
                return false
            }

            val updateHighScore = "UPDATE " + TABLE_HIGH_SCORES + " SET " +
                    COLUMN_MOVES + "=" + highScore.moves + "," +
                    COLUMN_PUSHES + "=" + highScore.pushes + "," +
                    COLUMN_TIME + "=" + highScore.time +
                    " WHERE " + COLUMN_HASH + " = " + highScore.hash
            val db = writableDatabase
            db.execSQL(updateHighScore)
            return true
        }
        return false
    }

    fun addHighScore(highScore: HighScore) {
        if (updateHighScore(highScore)) {
            return
        }
        val entry = ContentValues()
        entry.put(COLUMN_HASH, highScore.hash)
        entry.put(COLUMN_MOVES, highScore.moves)
        entry.put(COLUMN_TIME, highScore.time)
        entry.put(COLUMN_PUSHES, highScore.pushes)
        entry.put(COLUMN_LEVEL_NUMBER, highScore.levelNumber)
        val db = writableDatabase
        db.insert(TABLE_HIGH_SCORES, null, entry)
        db.close()
    }

    fun setCurrentGame(gameplay: Gameplay) {
        val db = writableDatabase
        // clear table
        db.delete(TABLE_CURRENT_GAME, TRUE_CONDITION, null)

        //add new entry
        val entry = ContentValues()
        entry.put(COLUMN_HASH, gameplay.stats().hash)
        entry.put(COLUMN_LEVEL_NUMBER, gameplay.level().number())
        entry.put(COLUMN_TIME, gameplay.stats().time)
        entry.put(COLUMN_MOVES_LIST, gameplay.movesString())
        db.insert(TABLE_CURRENT_GAME, null, entry)
        db.close()
    }

    fun getCurrentGame(ctx: Context): Gameplay? {
        val db = readableDatabase
        val selectCurrentGame = "SELECT * FROM " + TABLE_CURRENT_GAME +
                " WHERE " + TRUE_CONDITION + ";"
        val cursor = db.rawQuery(selectCurrentGame, null)
        var result: Gameplay? = null
        if (cursor.moveToFirst()) {
            val time = cursor.getInt(cursor.getColumnIndex(COLUMN_TIME))
            val currentLevel = cursor.getInt(cursor.getColumnIndex(COLUMN_LEVEL_NUMBER))
            result = Gameplay(currentLevel, FakeGameController(ctx))
            result.stats().time = time
            val movesList = cursor.getString(cursor.getColumnIndex(COLUMN_MOVES_LIST))
            try {
                result.makeMoves(movesList)
            } catch (e: Move.UnknownMoveException) {
                Log.e(Sokoban.TAG, "UnknownMove in " + movesList)
            }

        }
        db.close()
        cursor.close()
        return result
    }

    companion object {
        private val DATABASE_VERSION = 5
        private val DATABASE_NAME = "Sokoban.db"

        // tables
        private val TABLE_HIGH_SCORES = "high_scores"
        private val TABLE_CURRENT_GAME = "current_game"

        // types
        private val TYPE_ID = "INTEGER PRIMARY KEY AUTOINCREMENT"
        private val TYPE_INTEGER = "INTEGER"
        private val TYPE_TEXT = "TEXT"

        // columns
        private val COLUMN_ID = "_id"
        private val COLUMN_HASH = "hash"
        private val COLUMN_TIME = "time"
        private val COLUMN_MOVES = "moves"
        private val COLUMN_PUSHES = "pushes"
        private val COLUMN_LEVEL_NUMBER = "level_number"
        private val COLUMN_MOVES_LIST = "moves_list"

        private val TRUE_CONDITION = "1 = 1"

        private var instance: DatabaseManager? = null

        private fun initialize(context: Context) {
            instance = DatabaseManager(context)
        }

        fun instance(context: Context): DatabaseManager {
            if (instance == null) {
                initialize(context)
            }
            return instance!!
        }
    }
}
