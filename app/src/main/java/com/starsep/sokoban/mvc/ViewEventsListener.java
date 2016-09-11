package com.starsep.sokoban.mvc;

import com.starsep.sokoban.gamelogic.HighScore;

public interface ViewEventsListener extends ContextGetter {
	void onUpdate();

	void showWinDialog(int levelNumber, HighScore stats, HighScore highScore);
}
