package com.starsep.sokoban;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.starsep.sokoban.gamelogic.GameModel;
import com.starsep.sokoban.gamelogic.Gameplay;
import com.starsep.sokoban.gamelogic.HighScore;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends Activity implements
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener,
		GameController {
	// private GoogleApiClient googleApiClient;
	private GameView gameView;
	private GameModel gameModel;
	private TextView statusTextView;
	private Timer timer;

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

		gameView = (GameView) findViewById(R.id.gameView);
		statusTextView = (TextView) findViewById(R.id.statusTextView);

		Gameplay gameplay = new Gameplay(gameView);
		gameplay.setViewListener(gameView);
		gameModel = gameplay;

		gameView.setGameController(this);
		gameView.setGameModel(gameModel);
		gameView.setOnTouchListener(new OnSwipeTouchListener(this) {
			public void onSwipeTop() {
				gameModel.moveUp();
			}

			public void onSwipeRight() {
				gameModel.moveRight();
			}

			public void onSwipeLeft() {
				gameModel.moveLeft();
			}

			public void onSwipeBottom() {
				gameModel.moveDown();
			}
		});

		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						gameModel.onSecondElapsed();
					}
				});
			}
		}, 0, 1000);
		// googleApiClient = AchievementClient.build(this, gameView);
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

	/*@Override
	public void onAchievementUnlock(int id) {
		 Log.d(TAG, "Trying to unlock achievement");
		 googleApiClient.connect();
		 if (googleApiClient != null && googleApiClient.isConnected()) {
				Log.d(TAG, getResources().getString(id));
				Games.Achievements.increment(googleApiClient, getResources().getString(id), 300);
		      startActivityForResult(Games.Achievements.getAchievementsIntent(googleApiClient), 9111);
		 }
	}*/

	public void resetButtonClicked(View view) {
		gameModel.repeatLevel();
	}

	public void undoButtonClicked(View view) { gameModel.undoMove(); }

	public void settingsButtonClicked(View view) {

	}

	public void onStatsChanged() {
		int levelNumber = gameModel.levelNumber();
		HighScore highScore = gameModel.stats();
		int minutes = highScore.time / 60;
		int seconds = highScore.time % 60;
		int moves = highScore.moves;
		int pushes = highScore.pushes;
		statusTextView.setText(String.format(getResources().getString(R.string.level_status),
				levelNumber, minutes, seconds, moves, pushes));
	}
}
