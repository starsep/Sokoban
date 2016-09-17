package com.starsep.sokoban.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.starsep.sokoban.Sokoban;
import com.starsep.sokoban.gamelogic.Gameplay;
import com.starsep.sokoban.gamelogic.HighScore;
import com.starsep.sokoban.gamelogic.Move;
import com.starsep.sokoban.mvc.GameController;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 5;
	@NonNull private static final String DATABASE_NAME = "Sokoban.db";

	// tables
	@NonNull private static final String TABLE_HIGH_SCORES = "high_scores";
	@NonNull private static final String TABLE_CURRENT_GAME = "current_game";

	// types
	@NonNull private static final String TYPE_ID = "INTEGER PRIMARY KEY AUTOINCREMENT";
	@NonNull private static final String TYPE_INTEGER = "INTEGER";
	@NonNull private static final String TYPE_TEXT = "TEXT";

	// columns
	@NonNull private static final String COLUMN_ID = "_id";
	@NonNull private static final String COLUMN_HASH = "hash";
	@NonNull private static final String COLUMN_TIME = "time";
	@NonNull private static final String COLUMN_MOVES = "moves";
	@NonNull private static final String COLUMN_PUSHES = "pushes";
	@NonNull private static final String COLUMN_LEVEL_NUMBER = "level_number";
	@NonNull private static final String COLUMN_MOVES_LIST = "moves_list";

	@NonNull private static final String TRUE_CONDITION = "1 = 1";

	@Nullable private static DatabaseManager instance = null;

	private DatabaseManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	private static void initialize(Context context) {
		instance = new DatabaseManager(context);
	}

	@NonNull
	public static DatabaseManager instance(@NonNull Context context) {
		if (instance == null) {
			initialize(context);
		}
		return instance;
	}


	private void createTableCurrentGame(@NonNull SQLiteDatabase db) {
		String query = "CREATE TABLE " + TABLE_CURRENT_GAME + "(" +
				COLUMN_ID + " " + TYPE_ID + ", " +
				COLUMN_HASH + " " + TYPE_INTEGER + ", " +
				COLUMN_LEVEL_NUMBER + " " + TYPE_INTEGER + ", " +
				COLUMN_TIME + " " + TYPE_INTEGER + ", " +
				COLUMN_MOVES_LIST + " " + TYPE_TEXT +
				");";
		db.execSQL(query);
	}

	private void createTableHighScores(@NonNull SQLiteDatabase db) {
		String query = "CREATE TABLE " + TABLE_HIGH_SCORES + "(" +
				COLUMN_ID + " " + TYPE_ID + ", " +
				COLUMN_HASH + " " + TYPE_INTEGER + ", " +
				COLUMN_LEVEL_NUMBER + " " + TYPE_INTEGER + ", " +
				COLUMN_TIME + " " + TYPE_INTEGER + ", " +
				COLUMN_MOVES + " " + TYPE_INTEGER + ", " +
				COLUMN_PUSHES + " " + TYPE_INTEGER +
				");";
		db.execSQL(query);
	}

	@Override
	public void onCreate(@NonNull SQLiteDatabase db) {
		createTableHighScores(db);
		createTableCurrentGame(db);
	}

	private void migrateTableHighScores(@NonNull SQLiteDatabase db,
	                                    @SuppressWarnings("UnusedParameters") int oldVersion,
	                                    @SuppressWarnings("UnusedParameters") int newVersion) {
		// TODO: migrate data
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGH_SCORES);
		createTableHighScores(db);
	}

	private void migrateTableCurrentGame(@NonNull SQLiteDatabase db,
	                                     @SuppressWarnings("UnusedParameters") int oldVersion,
	                                     @SuppressWarnings("UnusedParameters") int newVersion) {
		// TODO: migrate data
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENT_GAME);
		createTableCurrentGame(db);
	}

	@Override
	public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
		migrateTableHighScores(db, oldVersion, newVersion);
		migrateTableCurrentGame(db, oldVersion, newVersion);
	}

	@Nullable
	public HighScore getHighScore(int hash) {
		SQLiteDatabase db = getReadableDatabase();
		String selectHighScore = "SELECT * FROM " + TABLE_HIGH_SCORES + " WHERE " +
				COLUMN_HASH + " = " + hash + ";";
		Cursor cursor = db.rawQuery(selectHighScore, null);
		HighScore result = null;
		if (cursor.moveToFirst()) {
			int moves = cursor.getInt(cursor.getColumnIndex(COLUMN_MOVES));
			int pushes = cursor.getInt(cursor.getColumnIndex(COLUMN_PUSHES));
			int time = cursor.getInt(cursor.getColumnIndex(COLUMN_TIME));
			int level = cursor.getInt(cursor.getColumnIndex(COLUMN_LEVEL_NUMBER));
			result = new HighScore(hash, level, time, moves, pushes);
		}
		db.close();
		cursor.close();
		return result;
	}

	private boolean updateHighScore(@NonNull final HighScore highScore) {
		HighScore oldScore = getHighScore(highScore.hash);
		if (oldScore != null) {
			try {
				oldScore.improve(highScore);
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

	public void addHighScore(@NonNull HighScore highScore) {
		if (updateHighScore(highScore)) {
			return;
		}
		ContentValues entry = new ContentValues();
		entry.put(COLUMN_HASH, highScore.hash);
		entry.put(COLUMN_MOVES, highScore.moves);
		entry.put(COLUMN_TIME, highScore.time);
		entry.put(COLUMN_PUSHES, highScore.pushes);
		entry.put(COLUMN_LEVEL_NUMBER, highScore.levelNumber);
		SQLiteDatabase db = getWritableDatabase();
		db.insert(TABLE_HIGH_SCORES, null, entry);
		db.close();
	}

	public void setCurrentGame(@NonNull Gameplay gameplay) {
		SQLiteDatabase db = getWritableDatabase();
		// clear table
		db.delete(TABLE_CURRENT_GAME, TRUE_CONDITION, null);

		//add new entry
		ContentValues entry = new ContentValues();
		entry.put(COLUMN_HASH, gameplay.stats().hash);
		entry.put(COLUMN_LEVEL_NUMBER, gameplay.levelNumber());
		entry.put(COLUMN_TIME, gameplay.stats().time);
		entry.put(COLUMN_MOVES_LIST, gameplay.movesString());
		db.insert(TABLE_CURRENT_GAME, null, entry);
		db.close();
	}

	@Nullable
	public Gameplay getCurrentGame(@NonNull final GameController gameController) {
		SQLiteDatabase db = getReadableDatabase();
		String selectCurrentGame = "SELECT * FROM " + TABLE_CURRENT_GAME +
				" WHERE " + TRUE_CONDITION + ";";
		Cursor cursor = db.rawQuery(selectCurrentGame, null);
		Gameplay result = null;
		if (cursor.moveToFirst()) {
			int time = cursor.getInt(cursor.getColumnIndex(COLUMN_TIME));
			int currentLevel = cursor.getInt(cursor.getColumnIndex(COLUMN_LEVEL_NUMBER));
			result = new Gameplay(gameController, currentLevel);
			result.stats().time = time;
			String movesList = cursor.getString(cursor.getColumnIndex(COLUMN_MOVES_LIST));
			try {
				result.makeMoves(movesList);
			} catch (Move.UnknownMoveException e) {
				Log.e(Sokoban.TAG, "UnknownMove in " + movesList);
			}
		}
		db.close();
		cursor.close();
		return result;
	}

	@NonNull
	public List<Integer> getSolvedLevels() {
		SQLiteDatabase db = getReadableDatabase();
		String selectLevelsSolved = "SELECT " + COLUMN_LEVEL_NUMBER + " FROM " + TABLE_HIGH_SCORES +
				" WHERE " + TRUE_CONDITION + ";";
		Cursor cursor = db.rawQuery(selectLevelsSolved, null);
		List<Integer> result = new ArrayList<>();
		while (cursor.moveToNext()) {
			result.add(cursor.getInt(cursor.getColumnIndex(COLUMN_LEVEL_NUMBER)));
		}
		db.close();
		cursor.close();
		return result;
	}
}
