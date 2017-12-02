package com.starsep.sokoban.gamelogic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MoveTest {
	@Test
	public void toStringFromCharIsIdentity() {
		Move[] moves = {
                Move.Companion.getDOWN(), Move.Companion.getLEFT(), Move.Companion.getRIGHT(), Move.Companion.getUP(),
                Move.Companion.getPUSH_DOWN(), Move.Companion.getPUSH_LEFT(), Move.Companion.getPUSH_RIGHT(), Move.Companion.getPUSH_UP()
		};
		for (Move m : moves) {
			try {
				assertEquals(Move.Companion.fromChar(m.toChar()), m);
			} catch (Move.UnknownMoveException e) {
				assertTrue(false);
			}
		}
	}
}
