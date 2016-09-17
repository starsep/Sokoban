package com.starsep.sokoban.gamelogic;

public class Position {
	public int y;
	public int x;

	public Position(int y, int x) {
		this.y = y;
		this.x = x;
	}

	public Position() {
		this(0, 0);
	}
}
