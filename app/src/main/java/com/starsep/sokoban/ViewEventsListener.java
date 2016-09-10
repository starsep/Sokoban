package com.starsep.sokoban;

import com.starsep.sokoban.gamelogic.HighScore;

public interface ViewEventsListener extends ContextGetter {
	void onUpdate();

	void showWinDialog(int levelNumber, HighScore stats);
}
