package com.starsep.sokoban.sokoban;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Level {
    private char[][] tiles;
    private final int playerStartX;
    private final int playerStartY;

    public Level(int width, int height, int playerX, int playerY) {
        tiles = new char[height][];
        for (int i = 0; i < height; i++) {
            tiles[i] = new char[width];
        }
        playerStartX = playerX;
        playerStartY = playerY;
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
        int pY = scanner.nextInt(), pX = scanner.nextInt();
        int height = scanner.nextInt(), width = scanner.nextInt();
        Level result = new Level(width, height, pX, pY);
        Log.d("Sokoban", width + " " + height + " " + pX + " " + pY);
        for (int i = 0; i < result.tiles().length; i++) {
            String line = scanner.next();
            Log.d("Sokonan", line);
            for (int j = 0; j < result.tiles()[i].length; j++) {
                result.tiles()[i][j] = line.charAt(j);
            }
        }
        return result;
    }

    public int playerX() {
        return playerStartX;
    }

    public int playerY() {
        return playerStartY;
    }
}
