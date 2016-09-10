package com.starsep.sokoban.gamelogic;

import android.util.Log;

import com.starsep.sokoban.ContextGetter;
import com.starsep.sokoban.Sokoban;
import com.starsep.sokoban.ViewEventsListener;
import com.starsep.sokoban.database.DatabaseManager;

import java.io.IOException;

public class Gameplay implements GameModel {
	private ViewEventsListener viewListener;

	private HighScore stats;
	private Level currentLevel;
	private int levelNumber;

	public Gameplay(ContextGetter contextGetter) {
		this(contextGetter, 1);
	}

	public Gameplay(ContextGetter contextGetter, int levelNumber) {
		loadLevel(levelNumber, contextGetter);
	}

	private void loadLevel(int number, ContextGetter contextGetter) {
		levelNumber = number;
		if (contextGetter.editMode()) {
			currentLevel = Level.getDefaultLevel();
			return;
		}
		try {
			currentLevel = LevelLoader.load(contextGetter.getContext(), "levels/" + number + ".level", this);
		} catch (IOException e) {
			Log.e(Sokoban.TAG, "Load error (" + number + ".level) :<");
		}
		stats = new HighScore(currentLevel.hash(), 0, 0, 0);
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
		// Log.d(Sokoban.TAG, getHighScore(currentLevel.hash()).toString());
		viewListener.showWinDialog(levelNumber, stats);
		sendHighScore();
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
		loadLevel(levelNumber, viewListener);
		viewListener.onUpdate();
	}

	public void nextLevel() {
		loadLevel(++levelNumber, viewListener);
		viewListener.onUpdate();
	}

	@Override
	public Move lastMove() {
		return level().lastMove();
	}

	@Override
	public void onUndoMove() {
		stats().moves--;
		viewListener.onUpdate();
	}

	@Override
	public void onUndoPush() {
		stats().pushes--;
		viewListener.onUpdate();
	}

	@Override
	public void onSecondElapsed() {
		stats.time++;
		viewListener.onUpdate();
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

	public String movesString() {
		return level().movesString();
	}

	public void setViewListener(ViewEventsListener listener) {
		viewListener = listener;
		viewListener.onUpdate();
	}
}
