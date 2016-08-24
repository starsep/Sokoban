package com.starsep.sokoban.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.starsep.sokoban.Sokoban;

public class DatabaseManager extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Sokoban.db";
	public static final String TABLE_HIGH_SCORES = "high_scores";
	public static final String TYPE_ID = "INTEGER PRIMARY KEY AUTOINCREMENT";
	public static final String TYPE_INTEGER = "INTEGER";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_HASH = "hash";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_MOVES = "moves";
	public static final String COLUMN_PUSHES = "pushes";
	private static DatabaseManager instance = null;

	private DatabaseManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	private static void initialize(Context context) {
		instance = new DatabaseManager(context);
	}

	public static DatabaseManager instance(Context context) {
		if (instance == null) {
			initialize(context);
		}
		return instance;
	}

	private void createTableHighScores(SQLiteDatabase db) {
		String query = "CREATE TABLE " + TABLE_HIGH_SCORES + "(" +
				COLUMN_ID + " " + TYPE_ID + ", " +
				COLUMN_HASH + " " + TYPE_INTEGER + ", " +
				COLUMN_TIME + " " + TYPE_INTEGER + ", " +
				COLUMN_MOVES + " " + TYPE_INTEGER + ", " +
				COLUMN_PUSHES + " " + TYPE_INTEGER +
				");";
		db.execSQL(query);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTableHighScores(db);
	}

	private void migrateTableHighScores(SQLiteDatabase db, int oldVersion, int newVersion) {
		// migrate data
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGH_SCORES);
		createTableHighScores(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		migrateTableHighScores(db, oldVersion, newVersion);
	}

	public HighScore getHighScore(int hash) {
		SQLiteDatabase db = getReadableDatabase();
		String selectHighScore = "SELECT " + COLUMN_TIME + ", " + COLUMN_MOVES + ", " + COLUMN_PUSHES +
				" FROM " + TABLE_HIGH_SCORES + " WHERE " +
				COLUMN_HASH + " = " + hash + ";";
		Cursor cursor = db.rawQuery(selectHighScore, null);
		HighScore result = null;
		if (cursor.moveToFirst()) {
			int moves = cursor.getInt(cursor.getColumnIndex(COLUMN_MOVES));
			int pushes = cursor.getInt(cursor.getColumnIndex(COLUMN_PUSHES));
			int time = cursor.getInt(cursor.getColumnIndex(COLUMN_TIME));
			result = new HighScore(hash, time, moves, pushes);
		}
		db.close();
		cursor.close();
		return result;
	}

	private boolean updateHighScore(HighScore highScore) {
		HighScore oldScore = getHighScore(highScore.hash);
		if (oldScore != null) {
			try {
				highScore.improve(oldScore);
			} catch (HighScore.DifferentLevelsException e) {
				Log.e(Sokoban.TAG, "Got result from another Level");
				return false;
			}
			String updateHighScore = "UPDATE " + TABLE_HIGH_SCORES + " SET " +
					COLUMN_MOVES + "=" + highScore.moves + "," +
					COLUMN_PUSHES + "=" + highScore.pushes + "," +
					COLUMN_TIME + "=" + highScore.time +
					" WHERE " + COLUMN_HASH + " = " + highScore.hash;
			SQLiteDatabase db = getWritableDatabase();
			db.execSQL(updateHighScore);
			return true;
		}
		return false;
	}

	public void addHighScore(HighScore highScore) {
		if (updateHighScore(highScore)) {
			return;
		}
		ContentValues entry = new ContentValues();
		entry.put(COLUMN_HASH, highScore.hash);
		entry.put(COLUMN_MOVES, highScore.moves);
		entry.put(COLUMN_TIME, highScore.time);
		entry.put(COLUMN_PUSHES, highScore.pushes);
		SQLiteDatabase db = getWritableDatabase();
		db.insert(TABLE_HIGH_SCORES, null, entry);
		db.close();
	}
}
