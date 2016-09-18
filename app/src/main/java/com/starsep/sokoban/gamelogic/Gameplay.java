package com.starsep.sokoban.gamelogic;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.starsep.sokoban.Sokoban;
import com.starsep.sokoban.database.DatabaseManager;
import com.starsep.sokoban.mvc.GameController;
import com.starsep.sokoban.mvc.GameModel;
import com.starsep.sokoban.mvc.ViewEventsListener;

import java.io.IOException;

public class Gameplay implements GameModel {
	private ViewEventsListener viewListener;

	private HighScore stats;
	private Level currentLevel;
	private GameController gameController;

	public Gameplay(@NonNull GameController gameController, int levelNumber) {
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
		if (gameController.editMode()) {
			currentLevel = Level.getDefaultLevel(number);
			return;
		}
		try {
			currentLevel = LevelLoader.load(gameController.getContext(),
					"levels/" + number + ".level", this, number);
		} catch (IOException e) {
			Log.e(Sokoban.TAG, "Load error (" + number + ".level) :<");
			currentLevel = Level.getDefaultLevel(number);
		}
		stats = new HighScore(currentLevel.hash(), currentLevel.number(), 0, 0, 0);
		saveGame();
		onNewGame();
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

	@Override
	public void onMove() {
		stats.moves++;
		saveGame();
		updateView();
	}

	@Override
	public void onWin() {
		// Log.d(Sokoban.TAG, getHighScore(currentLevel.hash()).toString());
		if (viewListener != null) {
			pauseGame();
			viewListener.showWinDialog(level().number(), stats, getHighScore(level().hash()));
			sendHighScore();
		} else {
			nextLevel();
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
		loadLevel(level().number());
		updateView();
	}

	public void undoMove() {
		level().undoMove();
		updateView();
	}

	@NonNull
	public HighScore stats() {
		return stats;
	}

	@Override
	@NonNull
	public Position player() {
		return level().player();
	}

	@NonNull
	@Override
	public Level level() {
		return currentLevel;
	}

	public void nextLevel() {
		if (level().won()) {
			loadLevel(level().number() + 1);
			updateView();
		}
	}

	@Override
	@NonNull
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

	private void sendHighScore() {
		DatabaseManager databaseManager = DatabaseManager.instance(viewListener.getContext());
		databaseManager.addHighScore(stats);
	}

	@Nullable
	private HighScore getHighScore(int hash) {
		DatabaseManager databaseManager = DatabaseManager.instance(viewListener.getContext());
		return databaseManager.getHighScore(hash);
	}

	private void pauseGame() {
		if (gameController != null) {
			gameController.onGamePause();
		}
	}

	@NonNull
	public String movesString() {
		return level().movesString();
	}

	public void setViewListener(ViewEventsListener listener) {
		viewListener = listener;
		updateView();
	}

	public void setGameController(@NonNull GameController gameController) {
		this.gameController = gameController;
	}

	public void makeMoves(@NonNull String moves) throws Move.UnknownMoveException {
		level().makeMoves(moves);
	}
}
