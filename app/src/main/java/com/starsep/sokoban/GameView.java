package com.starsep.sokoban;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.starsep.sokoban.gamelogic.GameModel;
import com.starsep.sokoban.gamelogic.HighScore;
import com.starsep.sokoban.gamelogic.Level;
import com.starsep.sokoban.gamelogic.Position;
import com.starsep.sokoban.gamelogic.Tile;

public class GameView extends View implements ViewEventsListener {
	private Rect dimension;
	private int size; // size of tile
	private Paint textPaint;
	private Position screenDelta;
	private BitmapShader grassShader;
	private Paint grassPaint;
	private Rect backgroundRect;
	private GameController gameController;
	private GameModel gameModel;

	public GameView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);

		screenDelta = new Position(0, 0);

		Textures.init(getContext());

		backgroundRect = new Rect();
		grassShader = new BitmapShader(Textures.grassTexture(), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		Matrix backgroundScaleMatrix = new Matrix();
		backgroundScaleMatrix.setScale(0.5f, 0.5f);
		grassShader.setLocalMatrix(backgroundScaleMatrix);
		grassPaint = new Paint();
		grassPaint.setStyle(Paint.Style.FILL);
		grassPaint.setShader(grassShader);

		int size = Math.min(getWidth(), getHeight()) / 10;
		dimension = new Rect(0, 0, size, size);
		textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
	}

	private void setDrawingDimension(int x, int y) {
		dimension.set(screenDelta.x + x * size, screenDelta.y + y * size,
				screenDelta.x + (x + 1) * size, screenDelta.y + (y + 1) * size);
	}

	private void drawBackground(Canvas canvas) {
		canvas.drawRGB(0, 200, 0);
		canvas.drawRect(backgroundRect, grassPaint);
	}

	private void drawHero(Canvas canvas) {
		setDrawingDimension(gameModel.player().x, gameModel.player().y);
		canvas.drawBitmap(gameModel.lastMove().heroTexture(), null, dimension, null);
	}

	private void drawTiles(Canvas canvas) {
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
			backgroundRect.set(0, 0, getWidth(), getHeight());
			textPaint.setTextSize(size);
			screenDelta.x = (getWidth() - level.width() * size) / 2;
			screenDelta.y = (getHeight() - level.height() * size) / 2;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (gameModel == null) {
			return;
		}
		updateSize();
		drawBackground(canvas);
		drawTiles(canvas);
		drawHero(canvas);
	}

	@Override
	public void showWinDialog(int levelNumber, HighScore levelStats) {
		int minutes = levelStats.time / 60;
		int seconds = levelStats.time % 60;
		String msg = String.format(getResources().getString(R.string.win_msg),
				levelStats.moves, levelStats.pushes, minutes, seconds);
		new AlertDialog.Builder(getContext())
				.setTitle(String.format(getResources().getString(R.string.win_title), levelNumber))
				.setMessage(msg)
				.setPositiveButton(getResources().getString(R.string.win_positive), (dialog, which) -> {
					gameModel.nextLevel();
				})
				.setNegativeButton(getResources().getString(R.string.win_negative), (dialog, which) -> {
					gameModel.repeatLevel();
				})
				.setIcon(android.R.drawable.ic_dialog_info)
				.setCancelable(false)
				.show();
	}

	@Override
	public void onUpdate() {
		invalidate();
		if (gameController != null) {
			gameController.onStatsChanged();
		}
	}

	@Override
	public boolean editMode() {
		return isInEditMode();
	}

	public void setGameController(GameController controller) {
		gameController = controller;
		onUpdate();
	}

	public void setGameModel(GameModel model) {
		gameModel = model;
	}
}
