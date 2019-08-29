package test;

import static org.junit.Assert.*;
import logic.PlayerStatus;

import org.junit.Test;

public class TestPlayerStatus {

	@Test
	public void testConstructor() {
		//Does it set default field correctly?
		
		PlayerStatus player = new PlayerStatus(10);
		assertFalse(player.isGameover());
		assertFalse(player.isWin());
		assertEquals(0,player.getScore());
		assertEquals(1,player.getLife());
	}
	
	@Test
	public void testSetGameover() {
		PlayerStatus player = new PlayerStatus(1);
		assertFalse(player.isGameover());
		player.setGameover(true);
		assertTrue(player.isGameover());
	}
		
	@Test
	public void testOpenNewNonBombCell() {
		PlayerStatus player = new PlayerStatus(256);
		
		//Test Does it decrease field "requiredCellsOpening" correctly?
		player.openNewNonBombCell();
		assertEquals(false, player.isGameover());
		assertEquals(false,player.isWin());
		
		//Test Does it set field "gameover" and "win" to true when field "requiredCellsOpening" equals 0?
		for(int i = 1; i<256; i++)
			player.openNewNonBombCell();
		assertEquals(true, player.isGameover());
		assertEquals(true,player.isWin());
	}
	
	@Test
	public void testLife() {
		PlayerStatus player = new PlayerStatus(30);
		assertEquals(1,player.getLife());
		
		//Test Does PlayerStatus set field life correctly?
		player.setLife(player.getLife()+1);
		assertEquals(2,player.getLife());
		
		//Test if assigned life is more than 3, life should be set 3.
		player.setLife(5);
		assertEquals(3,player.getLife());
		
		//Test Does PlayerStatus set field life correctly?
		player.setLife(player.getLife()-1);
		assertEquals(2,player.getLife());
		
		//Test  if assigned life is less than 0, life should be set 0 and gameover equal true.
		player.setLife(-10);
		assertEquals(0,player.getLife());
		assertEquals(true,player.isGameover());
		
		//Test if assigned life equal 0, life should be set 0 and gameover equal true.
		player.setLife(0);
		assertEquals(0,player.getLife());
		assertEquals(true,player.isGameover());
	}

}
