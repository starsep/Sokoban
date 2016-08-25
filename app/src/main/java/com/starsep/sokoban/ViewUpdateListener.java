package com.starsep.sokoban;

import android.content.Context;

import com.starsep.sokoban.gamelogic.HighScore;

public interface ViewUpdateListener {
	void onUpdate();

	boolean editMode();

	Context getContext();

	void showWinDialog(int levelNumber, HighScore stats);
}
