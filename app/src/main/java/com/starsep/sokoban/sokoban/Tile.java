package com.starsep.sokoban.sokoban;

public class Tile {
    public final static char wall = 'W';
    public final static char crate = '#';
    public final static char ground = '.';
    public final static char endpoint = 'X';
    public final static char grass = '_';
    public final static char hero = '@';

    public static boolean isHero(char c) {
        return hero == c;
    }
    public static boolean isMovable(char c) {
        return c == grass || c == endpoint || c == ground;
    }
}
