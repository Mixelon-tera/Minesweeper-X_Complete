package test;

import static org.junit.Assert.*;
import logic.Board;
import logic.cell.InfoCell;

import org.junit.Test;

public class TestInfoCell {

	@Test
	public void testConstructor() {
		InfoCell infoCell = new InfoCell(1,1);
		assertEquals(1,infoCell.getNumber());
		assertFalse(infoCell.isOpened());
		assertFalse(infoCell.isFlagged());
	}
	
	@Test
	public void testSetupCell() {
		InfoCell infoCell = new InfoCell(1,1);
		infoCell.increaseNumber();
		assertEquals(2,infoCell.getNumber());
		for(int i = 0; i<10; i++)
			infoCell.increaseNumber();
		assertEquals(8,infoCell.getNumber());
		infoCell.setFlagged(true);
		assertTrue(infoCell.isFlagged());
	}
	
	@Test
	public void testCellScore() {
		Board board = new Board(1,1,0,0);
		InfoCell infoCell = new InfoCell(0,0);
		board.setCellAt(0, 0, infoCell);
		assertEquals(0,board.getPlayer().getScore());
		board.leftClickAt(0, 0);
		assertEquals(2,board.getPlayer().getScore());
		
		board = new Board(1,1,0,0);
		infoCell = new InfoCell(0,0);
		infoCell.increaseNumber();
		board.setCellAt(0, 0, infoCell);
		assertEquals(0,board.getPlayer().getScore());
		board.leftClickAt(0, 0);
		assertEquals(4,board.getPlayer().getScore());
		
		board = new Board(1,1,0,0);
		infoCell = new InfoCell(0,0);
		for(int i=0; i<8; i++)
			infoCell.increaseNumber();
		board.setCellAt(0, 0, infoCell);
		assertEquals(0,board.getPlayer().getScore());
		board.leftClickAt(0, 0);
		assertEquals(16,board.getPlayer().getScore());
	}
	
	@Test
	public void testDelete(){
		Board board = new Board(1,1,0,0);
		InfoCell infoCell = new InfoCell(0,0);
		board.setCellAt(0, 0, infoCell);
		assertTrue(board.getCellAt(0, 0) instanceof InfoCell);
		
		infoCell.delete(board);	
		assertEquals(null,board.getCellAt(0, 0));
		assertTrue(board.getPlayer().isGameover());
		assertTrue(board.getPlayer().isWin());
	}

}
