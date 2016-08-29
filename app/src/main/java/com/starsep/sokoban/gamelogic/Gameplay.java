package com.starsep.sokoban.gamelogic;

import android.util.Log;

import com.starsep.sokoban.Sokoban;
import com.starsep.sokoban.ViewEventsListener;
import com.starsep.sokoban.database.DatabaseManager;

import java.io.IOException;

public class Gameplay implements GameModel {
	private final ViewEventsListener viewListener;

	private HighScore stats;
	private Level currentLevel;
	private int levelNumber;

	public Gameplay(ViewEventsListener listener) {
		this(listener, 1);
	}

	public Gameplay(ViewEventsListener listener, int levelNumber) {
		viewListener = listener;
		loadLevel(levelNumber);
	}

	private void loadLevel(int number) {
		levelNumber = number;
		if (viewListener.editMode()) {
			currentLevel = Level.getDefaultLevel();
			viewListener.onUpdate();
			return;
		}
		try {
			currentLevel = LevelLoader.load(viewListener.getContext(), "levels/" + number + ".level", this);
		} catch (IOException e) {
			Log.e(Sokoban.TAG, "Load error (" + number + ".level) :<");
		}
		stats = new HighScore(currentLevel.hash(), 0, 0, 0);
		viewListener.onUpdate();
	}

	@Override
	public Level level() {
		return currentLevel;
	}

	@Override
	public void onMove() {
		stats.moves++;
		viewListener.onUpdate();
	}

	@Override
	public void onPush() {
		stats.pushes++;
	}

	private void sendHighScore() {
		DatabaseManager databaseManager = DatabaseManager.instance(viewListener.getContext());
		databaseManager.addHighScore(stats);
	}

	private HighScore getHighScore(int hash) {
		DatabaseManager databaseManager = DatabaseManager.instance(viewListener.getContext());
		return databaseManager.getHighScore(hash);
	}

	@Override
	public void onWin() {
		sendHighScore();
		Log.d(Sokoban.TAG, getHighScore(currentLevel.hash()).toString());
		viewListener.showWinDialog(levelNumber, stats);
	}

	@Override
	public void moveUp() {
		level().move(Move.UP);

	}

	@Override
	public void moveDown() {
		level().move(Move.DOWN);
	}

	@Override
	public void moveLeft() {
		level().move(Move.LEFT);
	}

	@Override
	public void moveRight() {
		level().move(Move.RIGHT);
	}

	public void repeatLevel() {
		loadLevel(levelNumber);
	}

	public void nextLevel() {
		loadLevel(++levelNumber);
	}

	@Override
	public Move lastMove() {
		return level().lastMove();
	}

	public HighScore stats() {
		return stats;
	}

	@Override
	public Position player() {
		return level().player();
	}

	public void undoMove() {
		level().undoMove();
		viewListener.onUpdate();
	}

	public int levelNumber() {
		return levelNumber;
	}
}
