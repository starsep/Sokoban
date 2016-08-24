package com.starsep.sokoban.gamelogic;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.starsep.sokoban.Sokoban;
import com.starsep.sokoban.Textures;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Level {
	private final char[][] tiles;
	private Position player;
	private Gameplay gameplay;
	private final int hash;

	private void setPlayer(Position position) {
		player = new Position(position.y, position.x);
	}

	public Level(char[][] data, Position player) {
		tiles = new char[data.length][];
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = new char[data[0].length];
		}
		for (int i = 0; i < height(); i++) {
			System.arraycopy(data[i], 0, tiles[i], 0, width());
		}
		setPlayer(player);
		hash = toString().hashCode();
	}

	public char[][] tiles() {
		return tiles;
	}

	public Bitmap texture(int y, int x) {
		return Textures.tile(tiles[y][x]);
	}

	public int height() {
		return tiles.length;
	}

	public int width() {
		return tiles[0].length;
	}

	public void setGameplay(Gameplay gameplay) {
		this.gameplay = gameplay;
	}

	public static Level load(Context context, String filename, Gameplay gameplay) throws IOException {
		InputStream inputStream;
		inputStream = context.getAssets().open(filename);
		Scanner scanner = new Scanner(inputStream);
		int height = scanner.nextInt(), width = scanner.nextInt();
		Position player = new Position(scanner.nextInt(), scanner.nextInt());
		char[][] data = new char[height][];
		for (int i = 0; i < height; i++) {
			String line = scanner.next();
			if (line.length() != width) {
				Log.e(Sokoban.TAG, "Level line: " + line + " has bad length. Should have " + width);
			}
			data[i] = line.toCharArray();
		}
		Level result = new Level(data, player);
		result.setGameplay(gameplay);
		if (!result.valid()) {
			Log.e(Sokoban.TAG, "Level.load: " + "Loaded level is invalid");
		}
		return result;
	}

	public Position player() { return player; }

	private void move(int dx, int dy) {
		player.x += dx;
		player.y += dy;
		gameplay.onMove();
		if (won()) {
			gameplay.onWin();
		}
	}

	private void push(int x, int y, int newX, int newY) {
		char oldTile = tiles()[y][x];
		char newTile = tiles()[newY][newX];
		tiles()[newY][newX] = Tile.withCrate(newTile);
		tiles()[y][x] = Tile.withoutCrate(oldTile);
		gameplay.onPush();
	}

	private void checkMove(int dx, int dy) {
		int x = player.x + dx;
		int y = player.y + dy;
		if (isCrate(x, y) && canMove(x + dx, y + dy)) {
			push(x, y, x + dx, y + dy);
			move(dx, dy);
		} else if (canMove(x, y)) {
			move(dx, dy);
		}
	}

	private boolean validTile(int x, int y) {
		return x >= 0 && x < width() && y >= 0 && y < height();
	}

	private boolean canMove(int x, int y) {
		return validTile(x, y) && Tile.isWalkable(tiles()[y][x]);
	}

	private boolean isCrate(int x, int y) {
		return validTile(x, y) && Tile.isCrate(tiles()[y][x]);
	}

	public boolean won() {
		return countCrates() == 0 && countEndpoints() == 0;
	}

	private int count(char tileType) {
		int result = 0;
		for (int i = 0; i < height(); i++) {
			for (int j = 0; j < width(); j++) {
				if (tiles[i][j] == tileType) {
					result++;
				}
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
		for (char[] l : tiles) {
			result += new String(l) + '\n';
		}
		return result;
	}

	public static Level getDefaultLevel() {
		char[][] data = {
			"###".toCharArray(),
			"#.#".toCharArray(),
			"###".toCharArray()
		};
		return new Level(data, new Position(1, 1));
	}

	public int hash() {
		return hash;
	}
}
