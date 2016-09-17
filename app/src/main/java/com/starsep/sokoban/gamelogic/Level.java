package com.starsep.sokoban.gamelogic;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.starsep.sokoban.mvc.GameModel;
import com.starsep.sokoban.res.Textures;

import java.util.ArrayList;
import java.util.List;

public class Level {
	@NonNull private final char[] tiles;
	private final int width;
	@NonNull private final Position player;
	private GameModel gameModel;
	private final int hash;
	@NonNull private final List<Move> moves;

	public Level(@NonNull char[] data, int width, @NonNull Position player) {
		tiles = new char[data.length];
		this.width = width;
		System.arraycopy(data, 0, tiles, 0, data.length);
		this.player = new Position(player.y, player.x);
		hash = toString().hashCode();
		moves = new ArrayList<>();
	}

	@NonNull
	public char[] tiles() {
		return tiles;
	}

	@NonNull
	public Bitmap texture(int y, int x) {
		return Textures.tile(tile(y, x));
	}

	public int height() {
		return tiles.length / width();
	}

	public int width() {
		return width;
	}

	public void setGameModel(@NonNull GameModel model) {
		gameModel = model;
	}

	@NonNull
	public Position player() { return player; }

	private void makeMove(@NonNull Move move, boolean undo) {
		player.x += move.dx();
		player.y += move.dy();
		if (!undo) {
			addMove(move);
			gameModel.onMove();
			if (won()) {
				gameModel.onWin();
			}
		} else {
			gameModel.onUndoMove();
		}
	}

	private void makeMove(@NonNull Move move) {
		makeMove(move, false);
	}

	private void addMove(@NonNull Move move) {
		moves.add(move);
	}

	private void push(int x, int y, int newX, int newY, boolean undo) {
		char oldTile = tile(y, x);
		char newTile = tile(newY, newX);
		setTile(newY, newX, Tile.withCrate(newTile));
		setTile(y, x, Tile.withoutCrate(oldTile));
		if (undo) {
			gameModel.onUndoPush();
		} else {
			gameModel.onPush();
		}
	}

	private void push(int x, int y, int newX, int newY) {
		push(x, y, newX, newY, false);
	}

	private void setTile(int y, int x, char c) {
		tiles[tileIndex(y, x)] = c;
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

	@NonNull
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
		if (toUndo.push()) {
			push(player.x + toUndo.dx(), player.y + toUndo.dy(), player.x, player.y, true);
		}
		makeMove(toUndo.reverse(), true);
	}

	@NonNull
	public Move lastMove() {
		if (moves.isEmpty()) {
			return Move.DOWN;
		}
		return moves.get(moves.size() - 1);
	}

	public void move(@NonNull Move move) {
		int x = player.x + move.dx();
		int y = player.y + move.dy();
		if (isCrate(x, y) && canMove(x + move.dx(), y + move.dy())) {
			push(x, y, x + move.dx(), y + move.dy());
			makeMove(Move.make_push(move));
		} else if (canMove(x, y)) {
			makeMove(move);
		}
	}

	@NonNull
	public String movesString() {
		String result = "";
		for (Move move : moves) {
			result += move.toString();
		}
		return result;
	}

	public void makeMoves(@NonNull String movesList) throws Move.UnknownMoveException {
		for (char c : movesList.toCharArray()) {
			move(Move.fromChar(c));
		}
	}
}
