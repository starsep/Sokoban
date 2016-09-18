package com.starsep.sokoban.gamelogic;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LevelConverterTest {

	@Test
	public void convertTest() throws LevelConverter.LevelConverterException {
		char[] exampleLevel = ("#####" + "#+.*#" + "#####").toCharArray();
		LevelConverter.convert(exampleLevel, 5, 0);
	}

	@Test
	public void convertTwoPlayersTest() throws LevelConverter.LevelConverterException {
		try {
			char[] exampleLevel = ("#####" + "#@$@#" + "#####").toCharArray();
			LevelConverter.convert(exampleLevel, 5, 0);
		} catch (LevelConverter.ManyPlayerTilesException e) {
			return;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		assertTrue(false);
	}

	@Test
	public void convertUnknownTileTest() throws LevelConverter.LevelConverterException {
		try {
			char[] exampleLevel = ("#####" + "#@$~#" + "#####").toCharArray();
			LevelConverter.convert(exampleLevel, 5, 0);
		} catch (LevelConverter.UnknownTileException e) {
			return;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		assertTrue(false);
	}

	@Test
	public void convertNoPlayerTileTest() throws LevelConverter.LevelConverterException {
		try {
			char[] exampleLevel = ("#####" + "# $.#" + "#####").toCharArray();
			LevelConverter.convert(exampleLevel, 5, 0);
		} catch (LevelConverter.NoPlayerTileException e) {
			return;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		assertTrue(false);
	}
}
