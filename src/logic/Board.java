package logic;

/* fill code */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lib.GameScreen;
import logic.cell.BombCell;
import logic.cell.Cell;
import logic.cell.EmptyCell;
import logic.cell.HeartCell;
import logic.cell.InfoCell;

public class Board {
	private PlayerStatus player;
	private Cell[][] board;
	private int width, height;
	private int bombCount, heartCount;

	public Board(int width, int height, int bombCount, int heartCount) {
		this.width = width;
		this.height = height;
		this.bombCount = bombCount;
		this.heartCount = heartCount;
		if (width <= 0 || height <= 0) {
			width = 3;
			height = 3;
		}

		if (width * height <= bombCount + heartCount) {
			bombCount = width * height - 1;
			heartCount = 0;
		}

		player = new PlayerStatus(width * height);

		board = new Cell[width][height];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				board[i][j] = new EmptyCell(i, j);
			}
		}

		generateGameBoard();
	}

	public PlayerStatus getPlayer() {
		return player;
	}

	public Cell[][] getBoard() {
		return board;
	}

	public void setBoard(Cell[][] board) {
		this.board = board;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getBombCount() {
		return bombCount;
	}

	public void setBombCount(int bombCount) {
		this.bombCount = bombCount;
	}

	public int getHeartCount() {
		return heartCount;
	}

	public void setHeartCount(int heartCount) {
		this.heartCount = heartCount;
	}

	public void setPlayer(PlayerStatus player) {
		this.player = player;
	}

	public boolean outOfBoard(int x, int y) {
		if ((x >= 0 && x <= (width - 1)) && (y >= 0 && y <= (height - 1))
				&& board[x][y] != null) {
			return false;
		} else {
			return true;
		}
	}

	public Cell getCellAt(int x, int y) {
		if (!this.outOfBoard(x, y)) {
			return board[x][y];
		} else {
			return null;
		}
	}

	public void setCellAt(int x, int y, Cell cell) {
		board[x][y] = cell;
	}

	public void leftClickAt(int x, int y) {
		if (!this.outOfBoard(x, y) && !player.isGameover()
				&& !this.getCellAt(x, y).isOpened()
				&& !this.getCellAt(x, y).isFlagged()) {
			Cell cell = board[x][y];
			cell.open(this);
		}
	}

	public void rightClickAt(int x, int y) {
		if (!this.outOfBoard(x, y) && !player.isGameover()
				&& !this.getCellAt(x, y).isOpened()) {
			Cell cell = board[x][y];
			cell.setFlagged(!cell.isFlagged());
		}
	}

	public void deleteOnTimer() {
		if (!this.getPlayer().isGameover()
				&& (this.getPlayer().getRequiredCellsOpening() > 0)) {

			ArrayList<Cell> Acell = new ArrayList<Cell>();
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					Acell.add(this.getCellAt(i, j));
				}
			}

			Collections.shuffle(Acell);
			for (Cell a : Acell) {
				if (a instanceof IDeletable) {
					((IDeletable) a).delete(this);
					break;
				}
			}

		}
	}

	private void generateGameBoard() {
		int countB=0,countH=0;
		
		while(countB<bombCount){
		int x = (int) (Math.random() * width);
		int y = (int) (Math.random() * height);
		if (x > width)
			x--;
		if (y > height)
			y--;
			
			if(this.getCellAt(x, y) instanceof EmptyCell){
				this.setCellAt(x, y, new BombCell(x, y));
				countB++;
			}
		}
		
		for(int i =0;i<width;i++){
			for(int j = 0 ;j<height ;j++){
				if(this.getCellAt(i, j) instanceof BombCell){
					
					if(!this.outOfBoard(i-1, j-1) && !(this.getCellAt(i-1, j-1) instanceof BombCell)){
						if(this.getCellAt(i-1, j-1) instanceof EmptyCell){
							this.setCellAt(i-1, j-1, new InfoCell(i-1, j-1));
						}
						else if(this.getCellAt(i-1, j-1) instanceof InfoCell){
							((InfoCell)this.getCellAt(i-1, j-1)).increaseNumber();
						}
					}
					
					if(!this.outOfBoard(i-1, j) && !(this.getCellAt(i-1, j) instanceof BombCell)){
						if(this.getCellAt(i-1, j) instanceof EmptyCell){
							this.setCellAt(i-1, j, new InfoCell(i-1, j));
						}
						else if(this.getCellAt(i-1, j) instanceof InfoCell){
							((InfoCell)this.getCellAt(i-1, j)).increaseNumber();
						}
					}
					
					if(!this.outOfBoard(i-1, j+1) && !(this.getCellAt(i-1, j+1) instanceof BombCell)){
						if(this.getCellAt(i-1, j+1) instanceof EmptyCell){
							this.setCellAt(i-1, j+1, new InfoCell(i-1, j+1));
						}
						else if(this.getCellAt(i-1, j+1) instanceof InfoCell){
							((InfoCell)this.getCellAt(i-1, j+1)).increaseNumber();
						}
					}
					
					if(!this.outOfBoard(i, j-1) && !(this.getCellAt(i, j-1) instanceof BombCell)){
						if(this.getCellAt(i, j-1) instanceof EmptyCell){
							this.setCellAt(i, j-1, new InfoCell(i, j-1));
						}
						else if(this.getCellAt(i, j-1) instanceof InfoCell){
							((InfoCell)this.getCellAt(i, j-1)).increaseNumber();
						}
					}
					
					if(!this.outOfBoard(i, j+1) && !(this.getCellAt(i, j+1) instanceof BombCell)){
						if(this.getCellAt(i, j+1) instanceof EmptyCell){
							this.setCellAt(i, j+1, new InfoCell(i, j+1));
						}
						else if(this.getCellAt(i, j+1) instanceof InfoCell){
							((InfoCell)this.getCellAt(i, j+1)).increaseNumber();
						}
					}
					
					if(!this.outOfBoard(i+1, j-1) && !(this.getCellAt(i+1, j-1) instanceof BombCell)){
						if(this.getCellAt(i+1, j-1) instanceof EmptyCell){
							this.setCellAt(i+1, j-1, new InfoCell(i+1, j-1));
						}
						else if(this.getCellAt(i+1, j-1) instanceof InfoCell){
							((InfoCell)this.getCellAt(i+1, j-1)).increaseNumber();
						}
					}
					
					if(!this.outOfBoard(i+1, j) && !(this.getCellAt(i+1, j) instanceof BombCell)){
						if(this.getCellAt(i+1, j) instanceof EmptyCell){
							this.setCellAt(i+1, j, new InfoCell(i+1, j));
						}
						else if(this.getCellAt(i+1, j) instanceof InfoCell){
							((InfoCell)this.getCellAt(i+1, j)).increaseNumber();
						}
					}
					
					if(!this.outOfBoard(i+1, j+1) && !(this.getCellAt(i+1, j+1) instanceof BombCell)){
						if(this.getCellAt(i+1, j+1) instanceof EmptyCell){
							this.setCellAt(i+1, j+1, new InfoCell(i+1, j+1));
						}
						else if(this.getCellAt(i+1, j+1) instanceof InfoCell){
							((InfoCell)this.getCellAt(i+1, j+1)).increaseNumber();
						}
					}
					
					
				}
			}
		}
		
		
		while(countH<heartCount){
			int x = (int) (Math.random() * width);
			int y = (int) (Math.random() * height);
			if (x > width)
				x--;
			if (y > height)
				y--;
				
				if(this.getCellAt(x, y) instanceof EmptyCell){
					this.setCellAt(x, y, new HeartCell(x, y));
					countH++;
				}
			}

	}
	
	
	
	
	
	
	
	
}
