package com.starsep.sokoban.gamelogic;

public interface LevelEventsListener {
	void onPush();

	void onMove();

	void onWin();
}
