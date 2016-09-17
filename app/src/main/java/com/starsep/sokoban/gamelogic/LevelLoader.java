package com.starsep.sokoban.gamelogic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.starsep.sokoban.Sokoban;
import com.starsep.sokoban.mvc.GameModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class LevelLoader {
	@NonNull
	public static Level load(@NonNull Context context, @NonNull String filename, @NonNull GameModel model) throws IOException {
		InputStream inputStream;
		inputStream = context.getAssets().open(filename);
		Scanner scanner = new Scanner(inputStream);
		int height = scanner.nextInt(), width = scanner.nextInt();
		Position player = new Position(scanner.nextInt(), scanner.nextInt());
		char[] data = new char[width * height];
		for (int i = 0; i < height; i++) {
			String line = scanner.next();
			if (line.length() != width) {
				Log.e(Sokoban.TAG, "Level line: " + line + " has bad length. Should have " + width);
			}
			System.arraycopy(line.toCharArray(), 0, data, i * width, line.length());
		}
		scanner.close();
		Level result = new Level(data, width, player);
		result.setGameModel(model);
		if (!result.valid()) {
			Log.e(Sokoban.TAG, "Level.load: " + "Loaded level is invalid");
		}
		return result;
	}
}
