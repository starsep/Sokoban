package com.starsep.sokoban.gamelogic;

import android.util.Log;

import com.starsep.sokoban.Sokoban;

public class Tile {
	private final static int GRASS_MASK = 0;
	public final static int WALKABLE_MASK = 0;
	private final static int SOLID_MASK = 1;
	private final static int GROUND_MASK = 2;
	private final static int ENDPOINT_MASK = 4;
	private final static int CRATE_MASK = 8;

	public final static char wall = 'W';
	public final static char crate = '#';
	public final static char ground = '.';
	public final static char endpoint = 'X';
	public final static char grass = '_';
	public final static char crateOnEndpoint = '&';

	public static boolean isWalkable(char c) {
		return (mask(c) & SOLID_MASK) == WALKABLE_MASK;
	}

	public static boolean isCrate(char c) {
		return (mask(c) & CRATE_MASK) == CRATE_MASK;
	}

	public static int mask(char c) {
		switch (c) {
			case wall:
				return SOLID_MASK;
			case crate:
				return GROUND_MASK | CRATE_MASK | SOLID_MASK;
			case ground:
				return GROUND_MASK;
			case endpoint:
				return GROUND_MASK | ENDPOINT_MASK;
			case grass:
				return GRASS_MASK;
			case crateOnEndpoint:
				return mask(endpoint) | mask(crate);
			default: {
				Log.e(Sokoban.TAG, "Tile.mask: " + "Unknown tile " + c);
				return WALKABLE_MASK;
			}
		}
	}

	public static char maskToChar(int m) {
		char[] tiles = {wall, crate, ground, endpoint, grass, crateOnEndpoint};
		for (char c : tiles) {
			if (m == mask(c)) {
				return c;
			}
		}
		Log.e(Sokoban.TAG, "Tile.maskToChar: " + "Unknown mask " + m);
		return grass;
	}

	public static boolean isGrass(char c) {
		return c == grass;
	}
	private static int crateMask() {
		return Tile.CRATE_MASK | Tile.SOLID_MASK;
	}

	public static char withoutCrate(char tile) {
		return maskToChar(mask(tile) ^ crateMask());
	}

	public static char withCrate(char tile) {
		return maskToChar(mask(tile) | crateMask());
	}



}
