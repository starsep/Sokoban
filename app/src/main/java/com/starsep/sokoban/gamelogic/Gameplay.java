package com.starsep.sokoban.gamelogic;

import android.util.Log;

import com.starsep.sokoban.GameView;
import com.starsep.sokoban.Sokoban;
import com.starsep.sokoban.ViewUpdateListener;
import com.starsep.sokoban.database.DatabaseManager;

import java.io.IOException;

public class Gameplay implements LevelEventsListener {
	private final ViewUpdateListener viewUpdateListener;

	private HighScore stats;
	private Level currentLevel;
	private int levelNumber;

	public Gameplay(ViewUpdateListener listener) {
		this(listener, 1);
	}

	public Gameplay(ViewUpdateListener listener, int levelNumber) {
		viewUpdateListener = listener;
		loadLevel(levelNumber);
	}

	private void loadLevel(int number) {
		levelNumber = number;
		if (viewUpdateListener.editMode()) {
			currentLevel = Level.getDefaultLevel();
			viewUpdateListener.onUpdate();
			return;
		}
		try {
			currentLevel = Level.load(viewUpdateListener.getContext(), "levels/" + number + ".level", this);
		} catch (IOException e) {
			Log.e(Sokoban.TAG, "Load error (" + number + ".level) :<");
		}
		stats = new HighScore(currentLevel.hash(), 0, 0, 0);
		viewUpdateListener.onUpdate();
	}

	public Level currentLevel() {
		return currentLevel;
	}

	@Override
	public void onMove() {
		stats.moves++;
		viewUpdateListener.onUpdate();
	}

	@Override
	public void onPush() {
		stats.pushes++;
	}

	private void sendHighScore() {
		DatabaseManager databaseManager = DatabaseManager.instance(viewUpdateListener.getContext());
		databaseManager.addHighScore(stats);
	}

	private HighScore getHighScore(int hash) {
		DatabaseManager databaseManager = DatabaseManager.instance(viewUpdateListener.getContext());
		return databaseManager.getHighScore(hash);
	}

	@Override
	public void onWin() {
		sendHighScore();
		Log.d(Sokoban.TAG, getHighScore(currentLevel.hash()).toString());
		viewUpdateListener.showWinDialog(levelNumber, stats);
	}

	public void repeatLevel() {
		loadLevel(levelNumber);
	}

	public void nextLevel() {
		loadLevel(++levelNumber);
	}

	public HighScore stats() {
		return stats;
	}
}
