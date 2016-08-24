package com.starsep.sokoban.gamelogic;

import android.util.Log;

import com.starsep.sokoban.GameView;
import com.starsep.sokoban.Sokoban;
import com.starsep.sokoban.database.DatabaseManager;
import com.starsep.sokoban.database.HighScore;

import java.io.IOException;

public class Gameplay {
	private final GameView gameView;

	private int moves;
	private int pushes;
	private int time;

	public Gameplay(GameView gameView) {
		this.gameView = gameView;
		loadLevel(1);
	}

	private void loadLevel(int number) {
		moves = 0;
		pushes = 0;
		time = 0;
		levelNumber = number;
		if (gameView.isInEditMode()) {
			currentLevel = Level.getDefaultLevel();
			gameView.update();
			return;
		}
		try {
			currentLevel = Level.load(gameView.getContext(), "levels/" + number + ".level", this);
		} catch (IOException e) {
			Log.e(Sokoban.TAG, "Load error (" + number + ".level) :<");
		}
		gameView.update();
	}

	private Level currentLevel;
	private int levelNumber;

	public Level currentLevel() {
		return currentLevel;
	}

	public int moves() {
		return moves;
	}

	public int pushes() {
		return pushes;
	}

	public int time() { return time; }

	public void onMove() {
		moves++;
		gameView.update();
	}

	public void onPush() {
		pushes++;
	}

	private void sendHighScore() {
		DatabaseManager databaseManager = DatabaseManager.instance(gameView.getContext());
		databaseManager.addHighScore(new HighScore(currentLevel.hash(), time, moves, pushes));
	}

	private HighScore getHighScore(int hash) {
		DatabaseManager databaseManager = DatabaseManager.instance(gameView.getContext());
		return databaseManager.getHighScore(hash);
	}

	public void onWin() {
		sendHighScore();
		Log.d(Sokoban.TAG, getHighScore(currentLevel.hash()).toString());
		gameView.showWinDialog(levelNumber, moves, pushes, time);
	}

	public void repeatLevel() {
		loadLevel(levelNumber);
	}

	public void nextLevel() {
		loadLevel(++levelNumber);
	}
}
