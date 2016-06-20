package com.starsep.sokoban.sokoban;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Level {
    private char[][] tiles;
    private int playerX;
    private int playerY;

    private Level(int width, int height) {
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
        Log.d("Sokoban", width + " " + height);
        for (int i = 0; i < result.tiles().length; i++) {
            String line = scanner.next();
            Log.d("Sokonan", line);
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
}
