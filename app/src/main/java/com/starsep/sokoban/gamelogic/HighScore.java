package com.starsep.sokoban.gamelogic;

import android.support.annotation.NonNull;

public class HighScore {
	public final int hash;
	public final int levelNumber;
	public int time;
	public int moves;
	public int pushes;
	public HighScore(int hash, int level, int time, int moves, int pushes) {
		this.hash = hash;
		levelNumber = level;
		this.time = time;
		this.moves = moves;
		this.pushes = pushes;
	}

	public void improve(@NonNull HighScore another) throws DifferentLevelsException {
		if (hash != another.hash) {
			throw new DifferentLevelsException();
		}
		time = Math.min(time, another.time);
		moves = Math.min(moves, another.moves);
		pushes = Math.min(pushes, another.pushes);
	}

	@Override
	@NonNull
	public String toString() {
		return "HighScore(" +
				"level=" + levelNumber + ", " +
				"hash=" + hash + ", " +
				"time=" + time + ", " +
				"moves=" + moves + ", " +
				"pushes=" + pushes + ")";
	}

	public class DifferentLevelsException extends Exception {}
}
