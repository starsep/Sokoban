package com.starsep.sokoban.sokoban;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.starsep.sokoban.GameView;

import java.io.IOException;
import java.io.InputStream;
import java.util.EventListener;
import java.util.Scanner;

public class Level {
    private char[][] tiles;
    private int playerX;
    private int playerY;
    private View view;

    private Level(int width, int height) {
        view = null;
        tiles = new char[height][];
        for (int i = 0; i < height; i++) {
            tiles[i] = new char[width];
        }
    }

    public char[][] tiles() {
        return tiles;
    }

    public Bitmap texture(int y, int x) {
        return Textures.tile(tiles[y][x]);
    }

    public int height() {
        return tiles.length;
    }

    public int width() {
        return tiles[0].length;
    }

    public static Level load(Context context, String filename) throws IOException {
        InputStream inputStream;
        inputStream = context.getAssets().open(filename);
        Scanner scanner = new Scanner(inputStream);
        int height = scanner.nextInt(), width = scanner.nextInt();
        Level result = new Level(width, height);
//        Log.d("Sokoban", width + " " + height);
        for (int i = 0; i < result.tiles().length; i++) {
            String line = scanner.next();
//            Log.d("Sokoban", line);
            for (int j = 0; j < result.tiles()[i].length; j++) {
                result.tiles()[i][j] = line.charAt(j);
                if (Tile.isHero(line.charAt(j))) {
                    result.playerX = j;
                    result.playerY = i;
                }
            }
        }
        return result;
    }

    public int playerX() {
        return playerX;
    }

    public int playerY() {
        return playerY;
    }

    private void move(int dx, int dy) {
        if (canMove(playerX() + dx, playerY() + dy)) {
            playerX += dx;
            playerY += dy;
            updateView();
        }
    }

    private void updateView() {
        if (view != null) {
            view.invalidate();
        }
    }

    private boolean canMove(int x, int y) {
        return Tile.isMovable(tiles()[y][x]);
    }

    public void moveLeft() {
        move(-1, 0);
    }

    public void moveRight() {
        move(1, 0);
    }

    public void moveUp() {
        move(0, -1);
    }

    public void moveDown() {
        move(0, 1);
    }

    public void setView(View view) {
        this.view = view;
    }
}
