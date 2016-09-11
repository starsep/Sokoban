package com.starsep.sokoban.gamelogic;

public class HighScore {

	public class DifferentLevelsException extends Exception {}

	public int hash;
	public int time;
	public int moves;
	public int pushes;
	public int levelNumber;

	public HighScore(int hash, int level, int time, int moves, int pushes) {
		this.hash = hash;
		levelNumber = level;
		set(time, moves, pushes);
	}

	public void improve(HighScore another) throws DifferentLevelsException {
		if (hash != another.hash) {
			throw new DifferentLevelsException();
		}
		time = Math.min(time, another.time);
		moves = Math.min(moves, another.moves);
		pushes = Math.min(pushes, another.pushes);
	}

	@Override
	public String toString() {
		return "HighScore(" +
				"level=" + levelNumber + ", " +
				"hash=" + hash + ", " +
				"time=" + time + ", " +
				"moves=" + moves + ", " +
				"pushes=" + pushes + ")";
	}

	public void set(int time, int moves, int pushes) {
		this.time = time;
		this.moves = moves;
		this.pushes = pushes;
	}
}
