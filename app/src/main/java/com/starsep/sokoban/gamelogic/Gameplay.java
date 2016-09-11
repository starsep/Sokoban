package com.starsep.sokoban.gamelogic;

import android.util.Log;

import com.starsep.sokoban.mvc.GameController;
import com.starsep.sokoban.Sokoban;
import com.starsep.sokoban.mvc.GameModel;
import com.starsep.sokoban.mvc.ViewEventsListener;
import com.starsep.sokoban.database.DatabaseManager;

import java.io.IOException;

public class Gameplay implements GameModel {
	private ViewEventsListener viewListener;

	private HighScore stats;
	private Level currentLevel;
	private int levelNumber;
	private GameController gameController;

	public Gameplay(GameController gameController) {
		this(gameController, 1);
	}

	public Gameplay(GameController gameController, int levelNumber) {
		setGameController(gameController);
		loadLevel(levelNumber);
	}

	private void saveGame() {
		if (gameController != null) {
			gameController.onSaveGame(this);
		}
	}

	private void onNewGame() {
		if (gameController != null) {
			gameController.onNewGame();
		}
	}

	private void loadLevel(int number) {
		levelNumber = number;
		if (gameController.editMode()) {
			currentLevel = Level.getDefaultLevel();
			return;
		}
		try {
			currentLevel = LevelLoader.load(gameController.getContext(), "levels/" + number + ".level", this);
		} catch (IOException e) {
			Log.e(Sokoban.TAG, "Load error (" + number + ".level) :<");
			currentLevel = Level.getDefaultLevel();
		}
		stats = new HighScore(currentLevel.hash(), 0, 0, 0);
		saveGame();
		onNewGame();
	}

	@Override
	public Level level() {
		return currentLevel;
	}

	@Override
	public void onMove() {
		stats.moves++;
		saveGame();
		updateView();
	}

	private void updateView() {
		if (viewListener != null) {
			viewListener.onUpdate();
		}
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
		if (viewListener != null) {
			pauseGame();
			viewListener.showWinDialog(levelNumber, stats, getHighScore(level().hash()));
			sendHighScore();
		} else {
			nextLevel();
		}
	}

	private void pauseGame() {
		if (gameController != null) {
			gameController.onGamePause();
		}
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
		updateView();
	}

	public void nextLevel() {
		if (level().won()) {
			loadLevel(++levelNumber);
			updateView();
		}
	}

	@Override
	public Move lastMove() {
		return level().lastMove();
	}

	@Override
	public void onUndoMove() {
		stats().moves--;
		updateView();
	}

	@Override
	public void onUndoPush() {
		stats().pushes--;
		updateView();
	}

	@Override
	public void onSecondElapsed() {
		stats.time++;
		updateView();
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
		updateView();
	}

	public int levelNumber() {
		return levelNumber;
	}

	public String movesString() {
		return level().movesString();
	}

	public void setViewListener(ViewEventsListener listener) {
		viewListener = listener;
		updateView();
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	public void makeMoves(String moves) throws Move.UnknownMoveException {
		level().makeMoves(moves);
	}
}
