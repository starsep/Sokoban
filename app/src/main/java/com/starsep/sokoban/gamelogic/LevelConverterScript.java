package com.starsep.sokoban.gamelogic;

import java.util.ArrayList;
import java.util.Scanner;

public class LevelConverterScript {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<char[]> data = new ArrayList<>();
		while (input.hasNextLine()) {
			String line = input.nextLine();
			if (line != null) {
				data.add(line.toCharArray());
			}
		}
		char[][] level = new char[data.size()][];
		for (int i = 0; i < level.length; i++) {
			level[i] = new char[data.get(i).length];
			System.arraycopy(data.get(i), 0, level[i], 0, data.get(i).length);
		}
		try {
			Level l = LevelConverter.convert(level);
			System.out.print(l);
		} catch (LevelConverter.LevelConverterException e) {
			e.printStackTrace();
		}
	}
}
