package com.starsep.sokoban.gamelogic;

import android.util.Log;

import com.starsep.sokoban.GameView;
import com.starsep.sokoban.Sokoban;

import java.io.IOException;

public class Gameplay {
	private final GameView gameView;

	private int points;
	private int moves;
	private int totalPoints;

	public Gameplay(GameView gameView) {
		this.gameView = gameView;
		totalPoints = 0;
		loadLevel(1);
	}

	private void loadLevel(int number) {
		moves = 0;
		points = 0;
		levelNumber = number;
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

	public int points() {
		return points;
	}

	public int totalPoints() { return points + totalPoints; }

	public void onMove() {
		moves++;
		points += 50;
		gameView.update();
	}

	public void onWin() {
		gameView.showWinDialog(levelNumber, points, points + totalPoints);
	}

	public void repeatLevel() {
		loadLevel(levelNumber);
	}

	public void nextLevel() {
		totalPoints += points;
		loadLevel(++levelNumber);
	}
}
