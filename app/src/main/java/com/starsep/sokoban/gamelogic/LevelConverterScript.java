package com.starsep.sokoban.gamelogic;

import java.util.ArrayList;
import java.util.Scanner;

public class LevelConverterScript {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<String> data = new ArrayList<>();
		while (input.hasNextLine()) {
			String line = input.nextLine();
			if (line != null) {
				data.add(line);
			}
		}
		int height = data.size();
		int width = data.get(0).length();
		char[] level = new char[height * width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				level[i * width + j] = data.get(i).charAt(j);
			}
		}
		try {
			Level l = LevelConverter.convert(level, width);
			System.out.print(l);
		} catch (LevelConverter.LevelConverterException e) {
			e.printStackTrace();
		}
		input.close();
	}
}
