package com.starsep.sokoban.mvc;

import android.content.Context;

import com.starsep.sokoban.gamelogic.Gameplay;

public interface GameController {
	void onStatsChanged();

	void onGamePause();

	void onSaveGame(Gameplay game);

	void onNewGame();

	boolean editMode();

	Context getContext();
}
