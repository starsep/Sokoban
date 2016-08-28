package com.starsep.sokoban.gamelogic;

import android.graphics.Bitmap;

import com.starsep.sokoban.Textures;

import java.util.ArrayList;
import java.util.List;

public class Level {
	private final char[] tiles;
	private final int width;
	private Position player;
	private GameModel gameModel;
	private final int hash;
	private final List<Move> moves;

	private class Move {
		public int dx;
		public int dy;
		public boolean pushed;

		public Move(int dx, int dy, boolean pushed) {
			this.dx = dx;
			this.dy = dy;
			this.pushed = pushed;
		}
	}

	private void setPlayer(Position position) {
		player = new Position(position.y, position.x);
	}

	public Level(char[] data, int width, Position player) {
		tiles = new char[data.length];
		this.width = width;
		System.arraycopy(data, 0, tiles, 0, data.length);
		setPlayer(player);
		hash = toString().hashCode();
		moves = new ArrayList<>();
	}

	public char[] tiles() {
		return tiles;
	}

	public Bitmap texture(int y, int x) {
		return Textures.tile(tile(y, x));
	}

	public int height() {
		return tiles.length / width();
	}

	public int width() {
		return width;
	}

	public void setGameModel(GameModel model) {
		gameModel = model;
	}

	public Position player() { return player; }

	private void move(int dx, int dy, boolean pushed, boolean real) {
		player.x += dx;
		player.y += dy;
		if (real) {
			addMove(dx, dy, pushed);
			gameModel.onMove();
			if (won()) {
				gameModel.onWin();
			}
		}
	}

	private void move(int dx, int dy, boolean pushed) {
		move(dx, dy, pushed, true);
	}

	private void addMove(int dx, int dy, boolean pushed) {
		moves.add(new Move(dx, dy, pushed));
	}

	private void push(int x, int y, int newX, int newY, boolean real) {
		char oldTile = tile(y, x);
		char newTile = tile(newY, newX);
		setTile(newY, newX, Tile.withCrate(newTile));
		setTile(y, x, Tile.withoutCrate(oldTile));
		if (real) {
			gameModel.onPush();
		}
	}

	private void push(int x, int y, int newX, int newY) {
		push(x, y, newX, newY, true);
	}

	private void setTile(int y, int x, char c) {
		tiles[tileIndex(y, x)] = c;
	}

	private void checkMove(int dx, int dy) {
		int x = player.x + dx;
		int y = player.y + dy;
		if (isCrate(x, y) && canMove(x + dx, y + dy)) {
			push(x, y, x + dx, y + dy);
			move(dx, dy, true);
		} else if (canMove(x, y)) {
			move(dx, dy, false);
		}
	}

	private boolean validTile(int x, int y) {
		return x >= 0 && x < width() && y >= 0 && y < height();
	}

	private boolean canMove(int x, int y) {
		return validTile(x, y) && Tile.isWalkable(tile(y, x));
	}

	private boolean isCrate(int x, int y) {
		return validTile(x, y) && Tile.isCrate(tile(y, x));
	}

	public boolean won() {
		return countCrates() == 0 && countEndpoints() == 0;
	}

	private int count(char tileType) {
		int result = 0;
		for (char tile : tiles) {
			if (tile == tileType) {
				result++;
			}
		}
		return result;
	}

	private int countCrates() {
		return count(Tile.crate);
	}

	private int countEndpoints() {
		return count(Tile.endpoint);
	}

	public boolean valid() {
		return countEndpoints() == countCrates();
	}

	public void moveLeft() {
		checkMove(-1, 0);
	}

	public void moveRight() {
		checkMove(1, 0);
	}

	public void moveUp() {
		checkMove(0, -1);
	}

	public void moveDown() {
		checkMove(0, 1);
	}

	@Override
	public String toString() {
		String result = "" + height() + ' ' + width() + '\n' +
				player.y + ' ' + player.x + '\n';
		for (int i = 0; i < height(); i++) {
			for (int j = 0; j < width(); j++) {
				result += tile(i, j);
			}
			result += "\n";
		}
		return result;
	}

	public static Level getDefaultLevel() {
		char[] data = ("###" + "#.#" + "###").toCharArray();
		return new Level(data, 3, new Position(1, 1));
	}

	public int hash() {
		return hash;
	}

	private int tileIndex(int y, int x) {
		return y * width() + x;
	}

	public char tile(int y, int x) {
		return tiles[tileIndex(y, x)];
	}

	public void undoMove() {
		if (moves.isEmpty()) {
			return;
		}
		Move toUndo = moves.get(moves.size() - 1);
		moves.remove(moves.size() - 1);
		if (toUndo.pushed) {
			push(player.x + toUndo.dx, player.y + toUndo.dy, player.x, player.y, false);
		}
		move(-toUndo.dx, -toUndo.dy, toUndo.pushed, false);
	}
}
