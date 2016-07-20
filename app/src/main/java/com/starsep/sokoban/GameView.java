package com.starsep.sokoban;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.starsep.sokoban.sokoban.Level;
import com.starsep.sokoban.sokoban.Textures;
import com.starsep.sokoban.sokoban.Tile;

import java.io.IOException;
import java.util.EventListener;

public class GameView extends View {
    private Rect dimension;
    private Level level;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Textures.init(getContext());
        int size = Math.min(getWidth(), getHeight()) / 10;
        dimension = new Rect(0, 0, size, size);
        try {
            level = Level.load(getContext(), "levels/1.level");
        } catch (IOException e) {
            Log.e("Sokoban", "Load error (1.level) :<");
        }
        level.setView(this);
        setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeTop() {
                level.moveUp();
            }

            public void onSwipeRight() {
                level.moveRight();
            }

            public void onSwipeLeft() {
                level.moveLeft();
            }

            public void onSwipeBottom() {
                level.moveDown();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = Math.min(getWidth() / level.width(), getHeight() / level.height());
        for (int y = 0; y < level.height(); y++) {
            for (int x = 0; x < level.width(); x++) {
                dimension.set(x * size, y * size, (x + 1) * size, (y + 1) * size);
                canvas.drawBitmap(Textures.tile(Tile.ground), null, dimension, null);
            }
        }
        for (int y = 0; y < level.height(); y++) {
            for (int x = 0; x < level.width(); x++) {
                dimension.set(x * size, y * size, (x + 1) * size, (y + 1) * size);
                canvas.drawBitmap(level.texture(y, x), null, dimension, null);
            }
        }
        dimension.set(level.playerX() * size, level.playerY() * size, (level.playerX() + 1) * size, (level.playerY() + 1) * size);
        canvas.drawBitmap(Textures.heroTexture(), null, dimension, null);
    }
}
