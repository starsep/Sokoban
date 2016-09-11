package com.starsep.sokoban.mvc;

import android.content.Context;

import com.starsep.sokoban.gamelogic.Gameplay;

public class FakeGameController implements GameController {
	private final Context context;

	public FakeGameController(Context context) {
		this.context = context;
	}

	@Override
	public void onStatsChanged() {

	}

	@Override
	public void onGamePause() {

	}

	@Override
	public void onGameStart() {

	}

	@Override
	public void onSaveGame(Gameplay game) {

	}

	@Override
	public void onNewGame() {

	}

	@Override
	public boolean editMode() {
		return false;
	}

	@Override
	public Context getContext() {
		return context;
	}
}