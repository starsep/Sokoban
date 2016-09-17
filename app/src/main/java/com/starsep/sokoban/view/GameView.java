package com.starsep.sokoban.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.starsep.sokoban.mvc.GameController;
import com.starsep.sokoban.R;
import com.starsep.sokoban.res.Textures;
import com.starsep.sokoban.mvc.ViewEventsListener;
import com.starsep.sokoban.mvc.GameModel;
import com.starsep.sokoban.gamelogic.HighScore;
import com.starsep.sokoban.gamelogic.Level;
import com.starsep.sokoban.gamelogic.Position;
import com.starsep.sokoban.gamelogic.Tile;

public class GameView extends View implements ViewEventsListener {
	private Rect dimension;
	private int size; // size of tile
	private Paint textPaint;
	private Position screenDelta;
	private GameController gameController;
	private GameModel gameModel;
	private Dialog winDialog;

	public GameView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);

		screenDelta = new Position(0, 0);

		Textures.init(getContext());

		int size = Math.min(getWidth(), getHeight()) / 10;
		dimension = new Rect(0, 0, size, size);
		textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
	}

	private void setDrawingDimension(int x, int y) {
		dimension.set(screenDelta.x + x * size, screenDelta.y + y * size,
				screenDelta.x + (x + 1) * size, screenDelta.y + (y + 1) * size);
	}

	private void drawHero(@NonNull Canvas canvas) {
		setDrawingDimension(gameModel.player().x, gameModel.player().y);
		canvas.drawBitmap(gameModel.lastMove().heroTexture(), null, dimension, null);
	}

	private void drawTiles(@NonNull Canvas canvas) {
		Level level = gameModel.level();
		for (int y = 0; y < level.height(); y++) {
			for (int x = 0; x < level.width(); x++) {
				setDrawingDimension(x, y);
				if (!Tile.isGrass(level.tile(y, x))) {
					canvas.drawBitmap(level.texture(y, x), null, dimension, null);
				}
			}
		}
	}

	private void updateSize() {
		Level level = gameModel.level();
		int newSize = Math.min(getWidth() / level.width(), getHeight() / level.height());
		if (newSize != size) {
			size = newSize;
			textPaint.setTextSize(size);
			screenDelta.x = (getWidth() - level.width() * size) / 2;
			screenDelta.y = (getHeight() - level.height() * size) / 2;
		}
	}

	@Override
	protected void onDraw(@NonNull Canvas canvas) {
		super.onDraw(canvas);
		if (gameModel == null) {
			return;
		}
		updateSize();
		drawTiles(canvas);
		drawHero(canvas);
	}

	@Override
	public void showWinDialog(int levelNumber, @NonNull HighScore levelStats, @Nullable HighScore highScore) {
		if (highScore == null) {
			highScore = levelStats;
		}
		int minutes = levelStats.time / 60;
		int seconds = levelStats.time % 60;
		int minutesBest = highScore.time / 60;
		int secondsBest = highScore.time % 60;
		String msg = String.format(getResources().getString(R.string.win_msg),
				levelStats.moves, highScore.moves, levelStats.pushes, highScore.pushes,
				minutes, seconds, minutesBest, secondsBest);
		winDialog = new AlertDialog.Builder(getContext())
				.setTitle(String.format(getResources().getString(R.string.win_title), levelNumber))
				.setMessage(msg)
				.setPositiveButton(getResources().getString(R.string.win_positive), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						gameModel.nextLevel();
					}
				})
				.setNegativeButton(getResources().getString(R.string.win_negative), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						gameModel.repeatLevel();
					}
				})
				.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						gameModel.nextLevel();
					}
				})
				.setIcon(android.R.drawable.ic_dialog_info)
				.create();
		winDialog.show();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (winDialog != null) {
			winDialog.dismiss();
		}
	}

	@Override
	public void onUpdate() {
		invalidate();
		if (gameController != null) {
			gameController.onStatsChanged();
		}
	}

	public void setGameController(@NonNull GameController controller) {
		gameController = controller;
		onUpdate();
	}

	public void setGameModel(@NonNull GameModel model) {
		gameModel = model;
	}
}
