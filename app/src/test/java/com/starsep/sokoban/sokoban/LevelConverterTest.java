package com.starsep.sokoban.sokoban;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

public class LevelConverterTest {
	public abstract class CheckThrow {
		protected abstract void run() throws Throwable;

		protected void check(boolean shouldThrow) {
			Throwable r = null;
			try {
				run();
			} catch (Throwable t) {
				r = t;
			}
			assertTrue(r != null == shouldThrow);
		}
	}

	public abstract class ShouldThrow extends CheckThrow {
		public void check() {
			check(true);
		}
	}

	public abstract class ShouldNotThrow extends CheckThrow {
		public void check() {
			check(false);
		}
	}

	@Test
	public void convertTest() throws LevelConverter.LevelConverterException {
		new ShouldNotThrow() {
			@Override
			protected void run() throws Throwable {
				char[][] exampleLevel = {
						"#####".toCharArray(),
						"#+.*#".toCharArray(),
						"#####".toCharArray(),
				};
				LevelConverter.convert(exampleLevel);
			}
		}.check();
	}

	@Test
	public void convertTwoPlayersTest() throws LevelConverter.LevelConverterException {
		new ShouldThrow() {
			@Override
			protected void run() throws Throwable {
				char[][] exampleLevel = {
						"#####".toCharArray(),
						"#@$@#".toCharArray(),
						"#####".toCharArray(),
				};
				LevelConverter.convert(exampleLevel);
			}
		}.check();
	}

	@Test
	public void convertUnknownTileTest() throws LevelConverter.LevelConverterException {
		new ShouldThrow() {
			@Override
			protected void run() throws Throwable {
				char[][] exampleLevel = {
						"#####".toCharArray(),
						"#@$~#".toCharArray(),
						"#####".toCharArray(),
				};
				LevelConverter.convert(exampleLevel);
			}
		}.check();
	}

	@Test
	public void convertNoPlayerTileTest() throws LevelConverter.LevelConverterException {
		new ShouldThrow() {
			@Override
			protected void run() throws Throwable {
				char[][] exampleLevel = {
						"#####".toCharArray(),
						"# $.#".toCharArray(),
						"#####".toCharArray(),
				};
				LevelConverter.convert(exampleLevel);
			}
		}.check();
	}
}
