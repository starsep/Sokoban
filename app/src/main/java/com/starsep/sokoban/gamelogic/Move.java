package com.starsep.sokoban.gamelogic;

import android.graphics.Bitmap;

import com.starsep.sokoban.Textures;

public class Move {
	public static final Move DOWN = new Move(Direction.DOWN, false);
	public static final Move UP = new Move(Direction.UP, false);
	public static final Move LEFT = new Move(Direction.LEFT, false);
	public static final Move RIGHT = new Move(Direction.RIGHT, false);
	public static final Move PUSH_DOWN = new Move(Direction.DOWN, true);
	public static final Move PUSH_UP = new Move(Direction.UP, true);
	public static final Move PUSH_LEFT = new Move(Direction.LEFT, true);
	public static final Move PUSH_RIGHT = new Move(Direction.RIGHT, true);

	private final Direction direction;
	private final boolean pushed;

	public static Move make_push(Move move) {
		switch (move.direction) {
			case LEFT:
				return PUSH_LEFT;
			case RIGHT:
				return PUSH_RIGHT;
			case UP:
				return PUSH_UP;
			case DOWN:
				return PUSH_DOWN;
			default:
				return PUSH_DOWN;
		}
	}

	public static Move reverse(Move move) {
		switch (move.direction) {
			case LEFT:
				return move.push() ? PUSH_RIGHT : RIGHT;
			case RIGHT:
				return move.push() ? PUSH_LEFT : LEFT;
			case DOWN:
				return move.push() ? PUSH_UP : UP;
			default:
			case UP:
				return move.push() ? PUSH_DOWN : DOWN;
		}
	}

	public Bitmap heroTexture() {
		switch (direction) {
			case LEFT:
				return Textures.heroLeft();
			case RIGHT:
				return Textures.heroRight();
			case UP:
				return Textures.heroUp();
			default:
			case DOWN:
				return Textures.heroDown();
		}
	}

	public enum Direction {
		LEFT,
		RIGHT,
		DOWN,
		UP
	}

	private Move(Direction direction, boolean pushed) {
		this.direction = direction;
		this.pushed = pushed;
	}

	public int dx() {
		switch (direction) {
			case LEFT:
				return -1;
			case RIGHT:
				return 1;
			case UP:
			case DOWN:
			default:
				return 0;
		}
	}

	public int dy() {
		switch (direction) {
			case LEFT:
			case RIGHT:
				return 0;
			case UP:
				return -1;
			case DOWN:
			default:
				return 1;
		}
	}

	public boolean push() {
		return pushed;
	}
}