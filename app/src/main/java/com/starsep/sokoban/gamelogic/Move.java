package com.starsep.sokoban.gamelogic;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.starsep.sokoban.res.Textures;

public class Move {
	public static class UnknownMoveException extends Exception { }

	@NonNull public static final Move DOWN = new Move(Direction.DOWN, false);
	@NonNull public static final Move UP = new Move(Direction.UP, false);
	@NonNull public static final Move LEFT = new Move(Direction.LEFT, false);
	@NonNull public static final Move RIGHT = new Move(Direction.RIGHT, false);
	@NonNull public static final Move PUSH_DOWN = new Move(Direction.DOWN, true);
	@NonNull public static final Move PUSH_UP = new Move(Direction.UP, true);
	@NonNull public static final Move PUSH_LEFT = new Move(Direction.LEFT, true);
	@NonNull public static final Move PUSH_RIGHT = new Move(Direction.RIGHT, true);

	@NonNull private final Direction direction;
	private final boolean pushed;

	@NonNull
	public static Move make_push(@NonNull Move move) {
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

	@NonNull
	public Move reverse() {
		switch (direction) {
			case LEFT:
				return push() ? PUSH_RIGHT : RIGHT;
			case RIGHT:
				return push() ? PUSH_LEFT : LEFT;
			case DOWN:
				return push() ? PUSH_UP : UP;
			default:
			case UP:
				return push() ? PUSH_DOWN : DOWN;
		}
	}

	@NonNull
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

	private Move(@NonNull Direction direction, boolean pushed) {
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

	@Override
	@NonNull
	public String toString() {
		return "" + toChar();
	}


	public char toChar() {
		switch (direction) {
			case LEFT:
				return push() ? 'L' : 'l';
			case RIGHT:
				return push() ? 'R' : 'r';
			case DOWN:
				return push() ? 'D' : 'd';
			default:
			case UP:
				return push() ? 'U' : 'u';
		}
	}

	@NonNull
	public static Move fromChar(char c) throws UnknownMoveException {
		switch (c) {
			case 'l':
				return LEFT;
			case 'L':
				return PUSH_LEFT;
			case 'r':
				return RIGHT;
			case 'R':
				return PUSH_RIGHT;
			case 'u':
				return UP;
			case 'U':
				return PUSH_UP;
			case 'd':
				return DOWN;
			case 'D':
				return PUSH_DOWN;
			default:
				throw new UnknownMoveException();
		}
	}
}