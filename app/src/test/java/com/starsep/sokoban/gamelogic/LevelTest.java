package com.starsep.sokoban.gamelogic;

import org.junit.Test;

import static org.junit.Assert.*;

public class LevelTest {
	@Test
	public void pushTest() {
		char[] data = ("WWWWW" + "W.#.W" + "WWWWW").toCharArray();
		Level level = new Level(data, 5, new Position(1, 1));
		final int[] moves = {0};
		final int[] pushes = {0};
		level.setGameModel(new GameModel() {
			@Override
			public void onPush() {
				pushes[0]++;
			}

			@Override
			public void onMove() {
				moves[0]++;
			}

			@Override
			public void onWin() {
				assertTrue(false);
			}

			@Override
			public void moveUp() {

			}

			@Override
			public void moveDown() {

			}

			@Override
			public void moveLeft() {

			}

			@Override
			public void moveRight() {

			}

			@Override
			public void repeatLevel() {

			}

			@Override
			public void undoMove() {

			}

			@Override
			public int levelNumber() {
				return 0;
			}

			@Override
			public HighScore stats() {
				return null;
			}

			@Override
			public Position player() {
				return null;
			}

			@Override
			public Level level() {
				return null;
			}

			@Override
			public void nextLevel() {

			}

			@Override
			public Move lastMove() {
				return null;
			}

			@Override
			public void onUndoMove() {

			}

			@Override
			public void onUndoPush() {

			}
		});
		level.move(Move.RIGHT);
		assertEquals(moves[0], 1);
		assertEquals(pushes[0], 1);
		char[] dataAfterMove = ("WWWWW" + "W..#W" + "WWWWW").toCharArray();
		assertArrayEquals(level.tiles(), dataAfterMove);
	}

	@Test
	public void pushTestWithWin() {
		char[] data = ("WWWWWW" + "W.#.XW" + "WWWWWW").toCharArray();
		Level level = new Level(data, 6, new Position(1, 1));
		final int[] moves = {0};
		final int[] pushes = {0};
		final int[] wins = {0};
		level.setGameModel(new GameModel() {
			@Override
			public void onPush() {
				pushes[0]++;
			}

			@Override
			public void onMove() {
				moves[0]++;
			}

			@Override
			public void onWin() {
				wins[0]++;
			}

			@Override
			public void moveUp() {

			}

			@Override
			public void moveDown() {

			}

			@Override
			public void moveLeft() {

			}

			@Override
			public void moveRight() {

			}

			@Override
			public void repeatLevel() {

			}

			@Override
			public void undoMove() {

			}

			@Override
			public int levelNumber() {
				return 0;
			}

			@Override
			public HighScore stats() {
				return null;
			}

			@Override
			public Position player() {
				return null;
			}

			@Override
			public Level level() {
				return null;
			}

			@Override
			public void nextLevel() {

			}

			@Override
			public Move lastMove() {
				return null;
			}

			@Override
			public void onUndoMove() {

			}

			@Override
			public void onUndoPush() {

			}
		});
		level.move(Move.RIGHT);
		level.move(Move.RIGHT);
		assertEquals(moves[0], 2);
		assertEquals(pushes[0], 2);
		assertEquals(wins[0], 1);
		char[] dataAfterMove = ("WWWWWW" + "W...&W" + "WWWWWW").toCharArray();
		assertArrayEquals(level.tiles(), dataAfterMove);
	}
}
