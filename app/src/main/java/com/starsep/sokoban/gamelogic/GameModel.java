package com.starsep.sokoban.gamelogic;

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
}
