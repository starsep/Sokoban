package com.starsep.sokoban.sokoban;

public class LevelConverter {
	public static abstract class LevelConverterException extends Exception {}

	public static class NoPlayerTileException extends LevelConverterException {}

	public static class ManyPlayerTilesException extends LevelConverterException {}

	public static class UnknownTileException extends LevelConverterException {}

	public static Level convert(char[][] data) throws LevelConverterException {
		Position player = findPlayer(data);
		convertTiles(data);
		return new Level(data, player);
	}

	private static void convertTiles(char[][] data) throws LevelConverterException {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				data[i][j] = tileMapping(data[i][j]);
			}
		}
	}

	private static class CommonLevelFormat {
		private final static char wall = '#';
		private final static char player = '@';
		private final static char playerOnEndpoint = '+';
		private final static char crate = '$';
		private final static char crateOnEndpoint = '*';
		private final static char endpoint = '.';
		private final static char floor = ' ';
	}

	private static char tileWithoutPlayer(char tile) {
		switch (tile) {
			case CommonLevelFormat.player:
				return CommonLevelFormat.floor;
			case CommonLevelFormat.playerOnEndpoint:
				return CommonLevelFormat.endpoint;
			default:
				return tile;
		}
	}

	private static char tileMapping(char tile) throws LevelConverterException {
		switch (tile) {
			case CommonLevelFormat.crate:
				return Tile.crate;
			case CommonLevelFormat.crateOnEndpoint:
				return Tile.crateOnEndpoint;
			case CommonLevelFormat.endpoint:
				return Tile.endpoint;
			case CommonLevelFormat.floor:
				return Tile.ground;
			case CommonLevelFormat.wall:
				return Tile.wall;
			default:
				throw new UnknownTileException();
		}
	}

	private static boolean isPlayerTile(char tile) {
		return tileWithoutPlayer(tile) != tile;
	}

	private static Position findPlayer(char[][] data) throws NoPlayerTileException, ManyPlayerTilesException {
		Position result = null;
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				if (isPlayerTile(data[i][j])) {
					if (result != null) {
						throw new ManyPlayerTilesException();
					}
					data[i][j] = tileWithoutPlayer(data[i][j]);
					result = new Position(i, j);
				}
			}
		}
		if (result == null) {
			throw new NoPlayerTileException();
		}
		return result;
	}
}
