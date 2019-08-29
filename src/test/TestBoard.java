package test;

import static org.junit.Assert.*;
import logic.Board;
import logic.cell.BombCell;
import logic.cell.EmptyCell;
import logic.cell.HeartCell;
import logic.cell.InfoCell;

import org.junit.Test;

public class TestBoard {

	@Test
	public void testConstructor() {
		//fail("Not yet implemented");
		Board board = new Board(16,16,0,0);
		
		for (int i = 0 ; i < 16 ; i++)
		{
			for (int j = 0; j < 16; j++)
			{
				assertEquals(true, board.getCellAt(i, j) instanceof EmptyCell);
			}
		}
	}
	
	@Test
	public void testOutOfBoard(){
		Board board = new Board(3,3,0,0);
		
		//Test return false if cell is in the board.
		assertEquals(false,board.outOfBoard(1,1));
		
		//Test return true if cell is out of the board.
		assertEquals(true,board.outOfBoard(4, 3));
		assertEquals(true,board.outOfBoard(-1, 5));
		
		//Test return true if cell is deleted.
		((EmptyCell) board.getCellAt(1, 2)).delete(board);
		assertEquals(true,board.outOfBoard(1,2));
	}
	
	@Test
	public void testGetCellAt(){
		Board board = new Board(3,3,0,0);
		
		//Test return null if outOfBound
		assertEquals(null,board.getCellAt(-1,-1));
		assertEquals(null,board.getCellAt(5, 5));
		assertEquals(null,board.getCellAt(-1, 5));
		
		//Test if cell is deleted
		((EmptyCell) board.getCellAt(0, 0)).delete(board);
		assertEquals(null,board.getCellAt(0,0));
	}
	
	@Test
	public void testLeftClickAt(){
		Board board = new Board(1,1,0,0);
		
		//Error shouldn't occur
		board.leftClickAt(-1, -1);
		
		//Test Cell can't open when game is over.
		board.getPlayer().setGameover(true);
		board.leftClickAt(0, 0);
		assertEquals(false,board.getCellAt(0, 0).isOpened());
		board.getPlayer().setGameover(false);
		
		//Test Cell can't open when it is flagged?
		board.getCellAt(0, 0).setFlagged(true);
		board.leftClickAt(0, 0);
		assertEquals(false,board.getCellAt(0, 0).isOpened());
		board.getCellAt(0, 0).setFlagged(false);
		
		//Test Cell can open normally when game isn't over and it hasn't opened yet.
		board.leftClickAt(0, 0);
		assertEquals(true,board.getCellAt(0, 0).isOpened());
		
		//Test Cell can't open again if it has already opened.
		board.leftClickAt(0, 0);
		assertEquals(1,board.getPlayer().getScore());
	}
	
	@Test
	public void testRightClickAt(){
		Board board = new Board(2,1,0,0);
		InfoCell info = new InfoCell(1,0);
		board.setCellAt(1, 0, info);
		
		//Error shouldn't occur
		board.rightClickAt(-1, -1);
		
		//Test Cell can't flag when game is over.
		board.getPlayer().setGameover(true);
		board.rightClickAt(0, 0);
		assertEquals(false,board.getCellAt(0, 0).isFlagged());
		board.getPlayer().setGameover(false);
		
		//Test Cell can flag normally when game isn't over and it hasn't opened yet.
		board.rightClickAt(1, 0);
		assertEquals(true,board.getCellAt(1, 0).isFlagged());
		board.rightClickAt(1, 0);
		assertEquals(false,board.getCellAt(1, 0).isFlagged());
		board.leftClickAt(1, 0);
		board.rightClickAt(1, 0);
		assertEquals(false,board.getCellAt(1, 0).isFlagged());
	}
	
	@Test
	public void testDeleteOnTimer(){
		Board board = new Board(2,2,0,0);
		InfoCell info1 = new InfoCell(1,0);
		InfoCell info2 = new InfoCell(1,1);
		board.setCellAt(1, 0, info1);
		board.setCellAt(1, 1, info2);
		
		//Test When game is over, EmptyCells or InfoCells shouldn't be deleted.
		board.getPlayer().setGameover(true);
		board.deleteOnTimer();
		
		for (int i = 0;i < 2 ;i++)
		{
			for (int j = 0;j < 2;j++)
			{
				assertNotEquals(null, board.getCellAt(i, j));
			}
		}
		
		//Test Does it delete a EmptyCell or an InfoCell when we call?
		board.getPlayer().setGameover(false);
		for (int i = 0; i < 4;i++)
		{
			board.deleteOnTimer();
		}
		
		for (int i = 0;i < 2 ;i++)
		{
			for (int j = 0;j < 2;j++)
			{
				assertEquals(null, board.getCellAt(i, j));
			}
		}
		
		//Test Does it delete a BombCell or a HeartCell when we call?
		board = new Board(1,1,0,0);
		BombCell bomb = new BombCell(0,0);
		board.setCellAt(0, 0,bomb);
		board.deleteOnTimer();
		assertEquals(bomb,board.getCellAt(0, 0));
		
		board = new Board(1,1,0,0);
		HeartCell heart = new HeartCell(0,0);
		board.setCellAt(0, 0,heart);
		board.deleteOnTimer();
		assertEquals(heart,board.getCellAt(0, 0));
	}
	
	@Test
	public void testGenerateGameBoard(){
		//method generateGameBoard should be called when Board is instantiated.
		int totalBomb = 30;
		int totalHeart = 10;
		Board board = new Board(16,16,totalBomb,totalHeart);
		
		//Test Does it generate BombCells and HeartCells correctly?
		int bomb_count = 0;
		int heart_count = 0;
		
		for (int i = 0 ;i < 16;i++)
		{
			for (int j = 0; j < 16;j++)
			{
				if (board.getCellAt(i, j) instanceof BombCell){
					bomb_count++;
				}
				else if (board.getCellAt(i, j) instanceof HeartCell){
					heart_count++;
				}
			}
		}
		
		assertEquals(30, bomb_count);
		assertEquals(10, heart_count);
		
		//Test Does it generate InfoCells with correct number?
		board = new Board(3,3,8,0);
		InfoCell info = new InfoCell(0,0);
		int infoX = 0;
		int infoY = 0;
		
		for (int i = 0 ;i < 3;i++)
		{
			for (int j = 0; j < 3;j++)
			{
				if (board.getCellAt(i, j) instanceof InfoCell){
					info = (InfoCell) board.getCellAt(i, j);
					infoX = i;
					infoY = j;
					break;
				}
			}
		}
		
		if (infoX == 1 && infoY == 1)
		{
			assertEquals(8,info.getNumber());
		}
		else if (infoX == infoY || Math.abs(infoX - infoY) == 2)
		{
			assertEquals(3,info.getNumber());
		}
		else
		{
			assertEquals(5,info.getNumber());
		}
	}
}
