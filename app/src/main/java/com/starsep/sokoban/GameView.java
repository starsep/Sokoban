package com.starsep.sokoban;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.starsep.sokoban.gamelogic.Gameplay;
import com.starsep.sokoban.gamelogic.Level;
import com.starsep.sokoban.gamelogic.Position;
import com.starsep.sokoban.gamelogic.Tile;

public class GameView extends View {
	private Rect dimension;
	private Gameplay gameplay;
	private int size; // size of tile
	private Paint textPaint;
	private AchievementListener achievementListener;
	private Position screenDelta;
	private BitmapShader grassShader;
	private Paint grassPaint;
	private Rect backgroundRect;

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
		gameplay = new Gameplay(this);
		setOnTouchListener(new OnSwipeTouchListener(getContext()) {
			public void onSwipeTop() {
				level().moveUp();
			}

			public void onSwipeRight() {
				level().moveRight();
			}

			public void onSwipeLeft() {
				level().moveLeft();
			}

			public void onSwipeBottom() {
				level().moveDown();
			}
		});
		textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
	}

	private Level level() {
		return gameplay.currentLevel();
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
		setDrawingDimension(level().player().x, level().player().y);
		canvas.drawBitmap(Textures.heroTexture(), null, dimension, null);
	}

	private void drawTiles(Canvas canvas) {
		for (int y = 0; y < level().height(); y++) {
			for (int x = 0; x < level().width(); x++) {
				setDrawingDimension(x, y);
				if (!Tile.isGrass(level().tiles()[y][x])) {
					canvas.drawBitmap(level().texture(y, x), null, dimension, null);
				}
			}
		}
	}

	private void drawTextOnRight(Canvas canvas, String text, int line) {
		final float textMargin = size / 2f;
		float movesWidth = textPaint.measureText(text);
		canvas.drawText(text, getWidth() - movesWidth - textMargin, line * size, textPaint);
	}

	private void drawStats(Canvas canvas) {
		drawTextOnRight(canvas, "Moves: " + gameplay.moves(), 1);
		drawTextOnRight(canvas, "Pushes: " + gameplay.pushes(), 2);
		drawTextOnRight(canvas, "Time: " + gameplay.time(), 3);
	}

	private void updateSize() {
		int newSize = Math.min(getWidth() / level().width(), getHeight() / level().height());
		if (newSize != size) {
			size = newSize;
			backgroundRect.set(0, 0, getWidth(), getHeight());
			textPaint.setTextSize(size);
			screenDelta.x = ((getWidth() / size - level().width()) * size + getWidth() % size) / 2;
			screenDelta.y = ((getHeight() / size - level().height()) * size + getHeight() % size) / 2;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		updateSize();
		drawBackground(canvas);
		drawTiles(canvas);
		drawHero(canvas);
		// drawStats(canvas);
		// achievementListener.onAchievementUnlock(R.string.achievement_leet);
	}

	public void setAchievementListener(AchievementListener achievementListener) {
		this.achievementListener = achievementListener;
	}

	public void update() {
		invalidate();
	}

	public void showWinDialog(int levelNumber, int moves, int pushes, int time) {
		String msg = "Moves: " + moves + "\n" +
				"Pushes: " + pushes + "\n" +
				"Time: " + time + "\n" +
				"Next level?";
		new AlertDialog.Builder(getContext())
				.setTitle("Level " + levelNumber + " completed!")
				.setMessage(msg)
				.setPositiveButton("Sure!", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						gameplay.nextLevel();
					}
				})
				.setNegativeButton("Repeat", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						gameplay.repeatLevel();
					}
				})
				.setIcon(android.R.drawable.ic_dialog_info)
				.setCancelable(false)
				.show();
	}
}
