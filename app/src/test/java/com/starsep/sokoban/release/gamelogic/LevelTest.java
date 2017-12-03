package com.starsep.sokoban.release.gamelogic;

import com.google.gson.Gson;
import com.starsep.sokoban.release.gamelogic.level.ImmutableLevel;
import com.starsep.sokoban.release.gamelogic.level.Level;

import org.junit.Test;

import static com.starsep.sokoban.release.gamelogic.level.LevelKt.getDefaultLevel;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LevelTest {
	@Test
	public void moveTest() {
		char[] data = ("WWWWW" + "W...W" + "WWWWW").toCharArray();
		Level level = new Level(new ImmutableLevel(data, 5, new Position(1, 1)));
		level.move(Move.Companion.getRIGHT());
		char[] dataAfterMove = ("WWWWW" + "W...W" + "WWWWW").toCharArray();
		assertArrayEquals(level.getTiles(), dataAfterMove);
		assertEquals(new Position(1, 2), level.getPlayer());
	}

	@Test
	public void pushTest() {
		char[] data = ("WWWWW" + "W.#.W" + "WWWWW").toCharArray();
		Level level = new Level(new ImmutableLevel(data, 5, new Position(1, 1)));
		level.move(Move.Companion.getRIGHT());
		char[] dataAfterMove = ("WWWWW" + "W..#W" + "WWWWW").toCharArray();
		assertArrayEquals(level.getTiles(), dataAfterMove);
		assertEquals(new Position(1, 2), level.getPlayer());
	}

	@Test
	public void pushTestWithWin() {
		char[] data = ("WWWWWW" + "W.#.XW" + "WWWWWW").toCharArray();
		Level level = new Level(new ImmutableLevel(data, 6, new Position(1, 1)));
		level.move(Move.Companion.getRIGHT());
		level.move(Move.Companion.getRIGHT());
		char[] dataAfterMove = ("WWWWWW" + "W...&W" + "WWWWWW").toCharArray();
		assertArrayEquals(level.getTiles(), dataAfterMove);
		assertEquals(new Position(1, 3), level.getPlayer());
	}

	@Test
	public void gsonLevel() {
		Level level = getDefaultLevel();
		level.move(Move.Companion.getLEFT());
		Gson gson = new Gson();
		String jsonString = gson.toJson(level);
		Level fromJson = gson.fromJson(jsonString, Level.class);
		assertTrue(level.equals(fromJson));
	}
}
