package test;

import static org.junit.Assert.*;
import logic.Board;
import logic.PlayerStatus;
import logic.cell.BombCell;
import logic.cell.Cell;
import logic.cell.EmptyCell;
import logic.cell.InfoCell;

import org.junit.Test;

public class TestEmptyCell {

	@Test
	public void testActionOnOpen() {
		//fail("Not yet implemented");
		
		//Test Does it increase score of the player +1 and field "opened" sets to true when it is opened?
		int width = 1;
		int height = 1;
		
		Board board = new Board(width,height,0,0);
		EmptyCell empty = new EmptyCell(0,0);
		board.setCellAt(0, 0, empty);
		
		empty.open(board);
		
		assertEquals(true,empty.isOpened());
		assertEquals(1,board.getPlayer().getScore());
		
		//Test Does it open EmptyCells and InfoCells around it?
		width = 5;
		height = 5;
		
		board = new Board(width,height,0,0);
		empty = new EmptyCell(2,2);
		
		board.setCellAt(2, 2, empty);
		board.setCellAt(3, 2, new InfoCell(3,2));
		board.setCellAt(1, 2, new InfoCell(1,2));
		board.setCellAt(2, 3, new InfoCell(2,3));
		board.setCellAt(2, 1, new InfoCell(2,1));
		
		empty.open(board);
		
		for (int i = 0 ;i < width; i++)
		{
			for (int j = 0; j< height ;j++)
			{
				assertEquals(true,board.getCellAt(i, j).isOpened());
			}
		}
		
		//EmptyCell shouldn't open BombCells or HeartCells or FlaggedCells around it when it is opened.
		width = 3;
		height = 3;
		
		board = new Board(width,height,0,0);
		empty = new EmptyCell(1,1);
		
		Cell flaggedCell = new EmptyCell(0,1);
		BombCell bomb = new BombCell(0,2);
		
		board.setCellAt(0, 1, flaggedCell);
		board.setCellAt(0, 2, bomb);
		board.setCellAt(1, 1, empty);
		
		flaggedCell.setFlagged(true);
		
		empty.open(board);
		
		for (int i = 1 ;i < height;i++)
		{
			assertEquals(false,board.getCellAt(0, i).isOpened());
		}
	}
	
	@Test
	public void testDelete(){
		int width = 1;
		int height = 1;
		
		//Test Does it set cell to null and call openNewNonBombCell?
		Board board = new Board(width,height,0,0);
		EmptyCell empty = new EmptyCell(0,0);
		board.setCellAt(0, 0, empty);
		
		empty.delete(board);
		
		assertEquals(null,board.getCellAt(0, 0));
		assertEquals(true,board.getPlayer().isGameover());
		assertEquals(true,board.getPlayer().isWin());
	}

}
