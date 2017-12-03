package com.starsep.sokoban.release.gamelogic;

import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class TileTest {
	@Test
	public void crateIsCrate() {
		assertTrue(Tile.INSTANCE.isCrate(Tile.INSTANCE.getCrate()));
	}

	@Test
	public void crateIsNotWalkable() {
		assertFalse(Tile.INSTANCE.isWalkable(Tile.INSTANCE.getCrate()));
	}

	@Test
	public void groundAndGrassIsWalkable() {
		assertTrue(Tile.INSTANCE.isWalkable(Tile.INSTANCE.getGround()));
		assertTrue(Tile.INSTANCE.isWalkable(Tile.INSTANCE.getGrass()));
	}

	@Test
	public void maskToMaskToCharIsIdentity() {
		char[] tiles = {
                Tile.INSTANCE.getCrate(), Tile.INSTANCE.getGrass(), Tile.INSTANCE.getGround(), Tile.INSTANCE.getEndpoint(), Tile.INSTANCE.getCrateOnEndpoint(), Tile.INSTANCE.getWall()};
		for (char tile : tiles) {
			assertEquals(Tile.INSTANCE.maskToChar(Tile.INSTANCE.mask(tile)), tile);
		}
	}

	@Test
	public void defaultMaskWalkable() {
		PowerMockito.mockStatic(Log.class);
		assertEquals(Tile.INSTANCE.mask('~'), Tile.INSTANCE.getWALKABLE_MASK());
		PowerMockito.verifyStatic();
	}

	@Test
	public void defaultMaskToCharGrass() {
		PowerMockito.mockStatic(Log.class);
		assertEquals(Tile.INSTANCE.maskToChar(1337), Tile.INSTANCE.getGrass());
		PowerMockito.verifyStatic();
	}
}
