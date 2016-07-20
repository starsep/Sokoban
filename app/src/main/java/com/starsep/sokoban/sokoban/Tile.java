package com.starsep.sokoban.sokoban;

import android.util.Log;

public class Tile {
    public final static int GRASS = 0;
    public final static int MOVABLE = 0;
    public final static int SOLID = 1;
    public final static int GROUND = 2;
    public final static int ENDPOINT = 4;
    public final static int CRATE = 8;

    public final static char wall = 'W';
    public final static char crate = '#';
    public final static char ground = '.';
    public final static char endpoint = 'X';
    public final static char grass = '_';
    public final static char crateOnEndpoint = '&';

    public final static int CRATE_MASK = SOLID | CRATE;

    public static boolean isMovable(char c) {
        return (mask(c) & SOLID) == MOVABLE;
    }

    public static boolean isCrate(char c) {
        return (mask(c) & CRATE) == CRATE;
    }

    public static int mask(char c) {
        switch (c) {
            case wall:
                return SOLID;
            case crate:
                return GROUND | CRATE | SOLID;
            case ground:
                return GROUND;
            case endpoint:
                return GROUND | ENDPOINT;
            case grass:
                return GRASS;
            case crateOnEndpoint:
                return mask(endpoint) | mask(crate);
            default: {
                Log.e("Tile.mask", "Unknown tile " + c);
                return MOVABLE;
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
        Log.e("Tile.maskToChar", "Unknown mask " + m);
        return grass;
    }
}
