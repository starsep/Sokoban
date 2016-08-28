package com.starsep.sokoban;

import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class AchievementClient {
	public static GoogleApiClient build(GameActivity gameActivity, View view) {
		return new GoogleApiClient.Builder(gameActivity)
				.addConnectionCallbacks(gameActivity)
				.addOnConnectionFailedListener(gameActivity)
				.addApi(Games.API).addScope(Games.SCOPE_GAMES)
				.setViewForPopups(view)
				.build();
	}
}
