package com.starsep.sokoban.gamelogic;

import com.google.gson.Gson;
import com.starsep.sokoban.fake.FakeContext;
import com.starsep.sokoban.fake.FakeGameModel;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LevelTest {
	@Test
	public void pushTest() {
		char[] data = ("WWWWW" + "W.#.W" + "WWWWW").toCharArray();
		Level level = new Level(data, 5, new Position(1, 1), 0);
		final int[] moves = {0};
		final int[] pushes = {0};
		level.setGameModel(new FakeGameModel(new FakeContext()) {
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
		level.move(Move.Companion.getRIGHT());
		assertEquals(moves[0], 1);
		assertEquals(pushes[0], 1);
		char[] dataAfterMove = ("WWWWW" + "W..#W" + "WWWWW").toCharArray();
		assertArrayEquals(level.tiles(), dataAfterMove);
	}

	@Test
	public void pushTestWithWin() {
		char[] data = ("WWWWWW" + "W.#.XW" + "WWWWWW").toCharArray();
		Level level = new Level(data, 6, new Position(1, 1), 0);
		final int[] moves = {0};
		final int[] pushes = {0};
		final int[] wins = {0};
		level.setGameModel(new FakeGameModel(new FakeContext()) {
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
		level.move(Move.Companion.getRIGHT());
		level.move(Move.Companion.getRIGHT());
		assertEquals(moves[0], 2);
		assertEquals(pushes[0], 2);
		assertEquals(wins[0], 1);
		char[] dataAfterMove = ("WWWWWW" + "W...&W" + "WWWWWW").toCharArray();
		assertArrayEquals(level.tiles(), dataAfterMove);
	}

	@Test
	public void gsonLevel() {
		Level level = Level.Companion.getDefaultLevel(0);
		level.move(Move.Companion.getLEFT());
		Gson gson = new Gson();
		String jsonString = gson.toJson(level);
		Level fromJson = gson.fromJson(jsonString, Level.class);
		assertTrue(level.equals(fromJson));
	}
}
