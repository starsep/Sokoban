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
	private char[][] tiles;
	private Position player;
	private Gameplay gameplay;

	private Level(int width, int height) {
		tiles = new char[height][];
		for (int i = 0; i < height; i++) {
			tiles[i] = new char[width];
		}
	}

	public void setPlayer(Position position) {
		player = new Position(position.y, position.x);
	}

	public Level(char[][] data, Position player) {
		this(data[0].length, data.length);
		for (int i = 0; i < height(); i++) {
			System.arraycopy(data[i], 0, tiles[i], 0, width());
		}
		setPlayer(player);
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
		Level result = new Level(width, height);
		Position player = new Position(scanner.nextInt(), scanner.nextInt());
		result.setPlayer(player);
		result.setGameplay(gameplay);
		for (int i = 0; i < result.tiles().length; i++) {
			String line = scanner.next();
			for (int j = 0; j < result.tiles()[i].length; j++) {
				result.tiles()[i][j] = line.charAt(j);
			}
		}
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

	private void moveCrate(int x, int y, int newX, int newY) {
		char oldTile = tiles()[y][x];
		char newTile = tiles()[newY][newX];
		tiles()[newY][newX] = Tile.maskToChar(Tile.mask(newTile) | Tile.CRATE_MASK);
		tiles()[y][x] = Tile.maskToChar(Tile.mask(oldTile) ^ Tile.CRATE_MASK);
	}

	private void checkMove(int dx, int dy) {
		int x = player.x + dx;
		int y = player.y + dy;
		if (isCrate(x, y) && canMove(x + dx, y + dy)) {
			moveCrate(x, y, x + dx, y + dy);
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
}
