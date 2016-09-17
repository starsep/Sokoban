package com.starsep.sokoban.mvc;

import android.support.annotation.NonNull;

import com.starsep.sokoban.gamelogic.HighScore;
import com.starsep.sokoban.gamelogic.Level;
import com.starsep.sokoban.gamelogic.Move;
import com.starsep.sokoban.gamelogic.Position;

public interface GameModel {
	void onPush();

	void onMove();

	void onWin();

	void moveUp();

	void moveDown();

	void moveLeft();

	void moveRight();

	void repeatLevel();

	void undoMove();

	int levelNumber();

	@NonNull
	HighScore stats();

	@NonNull
	Position player();

	@NonNull
	Level level();

	void nextLevel();

	@NonNull
	Move lastMove();

	void onUndoMove();

	void onUndoPush();

	void onSecondElapsed();
}
