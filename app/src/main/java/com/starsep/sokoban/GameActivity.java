package com.starsep.sokoban;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.starsep.sokoban.database.DatabaseManager;
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
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			 newGame = extras.getBoolean("New");
			getIntent().putExtra("New", false);
		}

		setContentView(R.layout.activity_game);

		gameView = (GameView) findViewById(R.id.gameView);
		statusTextView = (TextView) findViewById(R.id.statusTextView);
		Gameplay gameplay;
		if (newGame) {
			gameplay = new Gameplay(this);
		} else {
			gameplay = DatabaseManager.instance(this).getCurrentGame(this);
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

	@Override
	public void onGamePause() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	@Override
	public void onGameStart() {
		onGamePause();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(() -> gameModel.onSecondElapsed());
			}
		}, 0, 1000);
	}

	@Override
	public void onSaveGame(Gameplay game) {
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
