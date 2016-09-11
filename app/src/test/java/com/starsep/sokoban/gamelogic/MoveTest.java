package com.starsep.sokoban.gamelogic;

import org.junit.Test;

import static org.junit.Assert.*;

public class MoveTest {
	@Test
	public void toStringFromCharIsIdentity() {
		Move[] moves = {
				Move.DOWN, Move.LEFT, Move.RIGHT, Move.UP,
				Move.PUSH_DOWN, Move.PUSH_LEFT, Move.PUSH_RIGHT, Move.PUSH_UP
		};
		for (Move m : moves) {
			try {
				assertEquals(Move.fromChar(m.toChar()), m);
			} catch (Move.UnknownMoveException e) {
				assertTrue(false);
			}
		}
	}
}
