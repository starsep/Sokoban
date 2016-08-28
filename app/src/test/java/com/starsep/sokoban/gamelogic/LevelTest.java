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
		level.setLevelEventsListener(new LevelEventsListener() {
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
		});
		level.moveRight();
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
		level.setLevelEventsListener(new LevelEventsListener() {
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
		});
		level.moveRight();
		level.moveRight();
		assertEquals(moves[0], 2);
		assertEquals(pushes[0], 2);
		assertEquals(wins[0], 1);
		char[] dataAfterMove = ("WWWWWW" + "W...&W" + "WWWWWW").toCharArray();
		assertArrayEquals(level.tiles(), dataAfterMove);
	}
}