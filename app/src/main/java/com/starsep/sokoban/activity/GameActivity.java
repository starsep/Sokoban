package com.starsep.sokoban.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.starsep.sokoban.R;
import com.starsep.sokoban.Sokoban;
import com.starsep.sokoban.controls.OnSwipeTouchListener;
import com.starsep.sokoban.database.DatabaseManager;
import com.starsep.sokoban.gamelogic.Gameplay;
import com.starsep.sokoban.gamelogic.HighScore;
import com.starsep.sokoban.mvc.GameController;
import com.starsep.sokoban.mvc.GameModel;
import com.starsep.sokoban.view.GameView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends SokobanActivity implements GameController {
	private GameView gameView;
	@Nullable private GameModel gameModel;
	private TextView statusTextView;
	@Nullable private Timer timer;

	protected void onPause() {
		super.onPause();
		onGamePause();
	}

	@Override
	protected void onStart() {
		super.onStart();
		onGameStart();
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
		int levelNumber = gameModel.levelNumber();
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
}
