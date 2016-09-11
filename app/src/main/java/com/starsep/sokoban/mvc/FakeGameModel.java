package com.starsep.sokoban.mvc;

import com.starsep.sokoban.gamelogic.HighScore;
import com.starsep.sokoban.gamelogic.Level;
import com.starsep.sokoban.gamelogic.Move;
import com.starsep.sokoban.gamelogic.Position;

public class FakeGameModel implements GameModel {
	@Override
	public void onPush() {

	}

	@Override
	public void onMove() {

	}

	@Override
	public void onWin() {

	}

	@Override
	public void moveUp() {

	}

	@Override
	public void moveDown() {

	}

	@Override
	public void moveLeft() {

	}

	@Override
	public void moveRight() {

	}

	@Override
	public void repeatLevel() {

	}

	@Override
	public void undoMove() {

	}

	@Override
	public int levelNumber() {
		return 0;
	}

	@Override
	public HighScore stats() {
		return null;
	}

	@Override
	public Position player() {
		return null;
	}

	@Override
	public Level level() {
		return null;
	}

	@Override
	public void nextLevel() {

	}

	@Override
	public Move lastMove() {
		return null;
	}

	@Override
	public void onUndoMove() {

	}

	@Override
	public void onUndoPush() {

	}

	@Override
	public void onSecondElapsed() {

	}
}
