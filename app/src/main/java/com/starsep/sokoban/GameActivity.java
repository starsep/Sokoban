package com.starsep.sokoban;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.*;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class GameActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, AchievementListener {
	private GoogleApiClient googleApiClient;

	@Override
	protected void onStop() {
		super.onStop();
		// googleApiClient.disconnect();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// googleApiClient.connect();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		GameView gameView = (GameView) findViewById(R.id.gameView);

		googleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(Games.API).addScope(Games.SCOPE_GAMES)
				.setViewForPopups(gameView)
				.build();
		gameView.setAchievementListener(this);
	}

	@Override
	public void onConnected(@Nullable Bundle bundle) {
		// Log.d(TAG, "onConnected(): connected to Google APIs");
		// Games.setViewForPopups(googleApiClient, getWindow().getDecorView().findViewById(android.R.id.content));
		// onAchievementUnlock(R.string.achievement_leet);
	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
		// Log.d(TAG, "onConnectionFailed(): attempting to resolve" + connectionResult);
		// try {
		//      connectionResult.startResolutionForResult(this, 9143);
		// } catch (IntentSender.SendIntentException e) {
		//      e.printStackTrace();
		// }
	}

	@Override
	public void onAchievementUnlock(int id) {
		// Log.d(TAG, "Trying to unlock achievement");
		// googleApiClient.connect();
		// if (googleApiClient != null && googleApiClient.isConnected()) {
		//		Log.d(TAG, getResources().getString(id));
		//		Games.Achievements.increment(googleApiClient, getResources().getString(id), 300);
		//      startActivityForResult(Games.Achievements.getAchievementsIntent(googleApiClient), 9111);
		// }
	}
}
