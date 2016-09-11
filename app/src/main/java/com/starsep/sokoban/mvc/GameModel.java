package com.starsep.sokoban.mvc;

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

	HighScore stats();

	Position player();

	Level level();

	void nextLevel();

	Move lastMove();

	void onUndoMove();

	void onUndoPush();

	void onSecondElapsed();
}
