package com.starsep.sokoban.play_services;

import android.view.View;

import com.google.android.gms.games.Games;
import com.starsep.sokoban.activity.GameActivity;

public class GoogleApiClientBuilder {
	public static com.google.android.gms.common.api.GoogleApiClient build(GameActivity gameActivity, View view) {
		return new com.google.android.gms.common.api.GoogleApiClient.Builder(gameActivity)
				.addConnectionCallbacks(gameActivity)
				.addOnConnectionFailedListener(gameActivity)
				.addApi(Games.API).addScope(Games.SCOPE_GAMES)
				.setViewForPopups(view)
				.build();
	}
}
