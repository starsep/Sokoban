package com.starsep.sokoban.activity;

import android.content.Context;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.starsep.sokoban.R;
import com.starsep.sokoban.Sokoban;
import com.starsep.sokoban.controls.OnSwipeTouchListener;
import com.starsep.sokoban.database.DatabaseManager;
import com.starsep.sokoban.gamelogic.Gameplay;
import com.starsep.sokoban.gamelogic.HighScore;
import com.starsep.sokoban.mvc.GameController;
import com.starsep.sokoban.mvc.GameModel;
import com.starsep.sokoban.play_services.GoogleApiClientBuilder;
import com.starsep.sokoban.view.GameView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends SokobanActivity implements
		GameController,
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener {
	private GoogleApiClient googleApiClient;
	private GameView gameView;
	@Nullable private GameModel gameModel;
	private TextView statusTextView;
	@Nullable private Timer timer;

	@Override
	protected void onStart() {
		super.onStart();
		googleApiClient.connect();
		onGameStart();
	}

	protected void onPause() {
		super.onPause();
		googleApiClient.disconnect();
		onGamePause();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boolean newGame = false;
		int levelNumber = 1;

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			newGame = extras.getBoolean(Sokoban.NEW, false);
			getIntent().putExtra("New", false);
			levelNumber = extras.getInt(Sokoban.LEVEL_NUMBER, 1);
		}

		setContentView(R.layout.activity_game);

		gameView = (GameView) findViewById(R.id.gameView);
		statusTextView = (TextView) findViewById(R.id.statusTextView);
		Gameplay gameplay;
		if (newGame) {
			gameplay = new Gameplay(this, levelNumber);
		} else {
			gameplay = DatabaseManager.instance(this).getCurrentGame(this);
			assert gameplay != null;
			gameplay.setGameController(this);
		}
		gameplay.setViewListener(gameView);
		gameModel = gameplay;

		gameView.setGameController(this);
		gameView.setGameModel(gameModel);
		gameView.setOnTouchListener(new OnSwipeTouchListener(this) {
			public void onSwipeRight() {
				gameModel.moveRight();
			}

			public void onSwipeLeft() {
				gameModel.moveLeft();
			}

			public void onSwipeTop() {
				gameModel.moveUp();
			}

			public void onSwipeBottom() {
				gameModel.moveDown();
			}
		});

		googleApiClient = GoogleApiClientBuilder.build(this, gameView);
	}


	public void resetButtonClicked(@SuppressWarnings("UnusedParameters") View view) {
		assert gameModel != null;
		gameModel.repeatLevel();
	}

	public void undoButtonClicked(@SuppressWarnings("UnusedParameters") View view) {
		assert gameModel != null;
		gameModel.undoMove();
	}

	@SuppressWarnings("EmptyMethod")
	public void settingsButtonClicked(@SuppressWarnings("UnusedParameters") View view) {
		//TODO: implement
	}

	public void onStatsChanged() {
		assert gameModel != null;
		int levelNumber = gameModel.level().number();
		HighScore highScore = gameModel.stats();
		int minutes = highScore.time / 60;
		int seconds = highScore.time % 60;
		int moves = highScore.moves;
		int pushes = highScore.pushes;
		statusTextView.setText(String.format(getString(R.string.level_status),
				levelNumber, minutes, seconds, moves, pushes));
	}

	@Override
	public void onGamePause() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	@Override
	public void onSaveGame(@NonNull Gameplay game) {
		DatabaseManager.instance(this).setCurrentGame(game);
	}

	@Override
	public void onNewGame() {
		onGameStart();
	}

	@Override
	public boolean editMode() {
		return gameView.isInEditMode();
	}

	@Override
	public Context getContext() {
		return super.getBaseContext();
	}

	private void onGameStart() {
		onGamePause();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						assert gameModel != null;
						gameModel.onSecondElapsed();
					}
				});
			}
		}, 0, 1000);
	}

	@Override
	public void onConnected(@Nullable Bundle bundle) {
        Log.d(Sokoban.TAG, "onConnected(): connected to Google APIs");
		Games.setViewForPopups(googleApiClient, getWindow().getDecorView().findViewById(android.R.id.content));
	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(Sokoban.TAG, "onConnectionFailed(): attempting to resolve" + connectionResult);
		 try {
		      connectionResult.startResolutionForResult(this, 9143);
		 } catch (IntentSender.SendIntentException e) {
		      e.printStackTrace();
		 }
	}
}
