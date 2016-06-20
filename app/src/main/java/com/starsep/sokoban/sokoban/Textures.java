package com.starsep.sokoban.sokoban;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.starsep.sokoban.R;

public class Textures {
    private static Bitmap wallTexture;
    private static Bitmap crateTexture;
    private static Bitmap groundTexture;
    private static Bitmap endpointTexture;
    private static Bitmap heroTexture;
    private static Bitmap grassTexture;

    public static Bitmap tile(char tile) {
        switch (tile) {
            case Tile.wall:
                return wallTexture;
            case Tile.crate:
                return crateTexture;
            case Tile.endpoint:
                return endpointTexture;
            case Tile.grass:
                return grassTexture;
            default:
                return groundTexture;
        }
    }

    public static void init(Context context) {
        wallTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.wall);
        crateTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.crate);
        groundTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.ground);
        endpointTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.endpoint);
        grassTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.grass);
        heroTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero);
    }

    public static Bitmap heroTexture() {
        return heroTexture;
    }
}
