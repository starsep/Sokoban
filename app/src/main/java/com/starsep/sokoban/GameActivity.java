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

public class GameActivity extends Activity implements GameController {
	private GameView gameView;
	private GameModel gameModel;
	private TextView statusTextView;
	private Timer timer;

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onStart() {
		super.onStart();
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
				runOnUiThread(() -> gameModel.onSecondElapsed());
			}
		}, 0, 1000);
	}


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
