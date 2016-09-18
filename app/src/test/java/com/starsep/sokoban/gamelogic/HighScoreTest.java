package com.starsep.sokoban.gamelogic;

import com.google.gson.Gson;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HighScoreTest {
	@Test
	public void gsonHighscore() {
		HighScore highScore = new HighScore(0, 0, 0, 0, 0);
		Gson gson = new Gson();
		String jsonString = gson.toJson(highScore);
		HighScore fromJson = gson.fromJson(jsonString, HighScore.class);
		assertTrue(highScore.equals(fromJson));
	}
}
