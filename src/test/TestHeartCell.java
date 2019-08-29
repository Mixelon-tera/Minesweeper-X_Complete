package test;

import static org.junit.Assert.*;
import logic.Board;
import logic.cell.HeartCell;

import org.junit.Test;

public class TestHeartCell {

	@Test
	public void testActionOnOpen() {
		//fail("Not yet implemented");
		Board board = new Board(5,5,0,0);
		HeartCell heart1 = new HeartCell(2,2);
		HeartCell heart2 = new HeartCell(2,3);
		board.setCellAt(2, 2, heart1);
		board.setCellAt(2, 3, heart2);
		
		//Test Does it increase player life by 1 when HeartCell is open?
		heart1.open(board);
		assertEquals(true,heart1.isOpened());
		assertEquals(2,board.getPlayer().getLife());
		
		heart2.open(board);
		assertEquals(true,heart2.isOpened());
		assertEquals(3, board.getPlayer().getLife());
	}

}
