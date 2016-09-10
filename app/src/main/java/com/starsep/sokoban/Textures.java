package com.starsep.sokoban;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.starsep.sokoban.gamelogic.Tile;

public class Textures {
	private static Bitmap wall;
	private static Bitmap crate;
	private static Bitmap ground;
	private static Bitmap endpoint;
	private static Bitmap heroDown;
	private static Bitmap heroUp;
	private static Bitmap heroLeft;
	private static Bitmap heroRight;
	private static Bitmap grass;
	private static Bitmap crateOnEndpoint;

	public static Bitmap tile(char tile) {
		switch (tile) {
			case Tile.wall:
				return wall;
			case Tile.crate:
				return crate;
			case Tile.endpoint:
				return endpoint;
			case Tile.grass:
				return grass;
			case Tile.crateOnEndpoint:
				return crateOnEndpoint;
			case Tile.ground:
				return ground;
			default: {
				Log.e(Sokoban.TAG, "Textures.tile: " + "Unknown tile " + tile);
				return ground;
			}
		}
	}

	public static void init(Context context) {
		wall = BitmapFactory.decodeResource(context.getResources(), R.drawable.wall);
		crate = BitmapFactory.decodeResource(context.getResources(), R.drawable.crate);
		ground = BitmapFactory.decodeResource(context.getResources(), R.drawable.ground);
		endpoint = BitmapFactory.decodeResource(context.getResources(), R.drawable.endpoint);
		grass = BitmapFactory.decodeResource(context.getResources(), R.drawable.grass);
		heroDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero_down);
		heroUp = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero_up);
		heroLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero_left);
		heroRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero_right);
		crateOnEndpoint = BitmapFactory.decodeResource(context.getResources(), R.drawable.crate_on_endpoint);
	}

	public static Bitmap heroDown() {
		return heroDown;
	}

	public static Bitmap heroUp() {
		return heroUp;
	}

	public static Bitmap heroLeft() {
		return heroLeft;
	}

	public static Bitmap heroRight() {
		return heroRight;
	}

	public static Bitmap grassTexture() { return grass; }
}
