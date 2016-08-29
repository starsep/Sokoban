package com.starsep.sokoban;

import android.content.Context;

import com.starsep.sokoban.gamelogic.HighScore;

public interface ViewEventsListener extends ContextGetter {
	void onUpdate();

	void showWinDialog(int levelNumber, HighScore stats);
}
