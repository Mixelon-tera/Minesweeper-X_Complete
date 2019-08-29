package logic.cell;
/* fill code */

import logic.Board;
import logic.IDeletable;

public class EmptyCell extends Cell implements IDeletable{

	public EmptyCell(int x, int y) {
		super(x,y);
		this.x=x;
		this.y=y;
	}

	@Override
	protected void actionOnOpen(Board board) {
		board.getPlayer().setScore(board.getPlayer().getScore()+1);
		
		if(!board.outOfBoard(x-1, y-1)){
			if(!board.getCellAt(x-1, y-1).isFlagged() && !board.getCellAt(x-1, y-1).isOpened()){
				if(board.getCellAt(x-1, y-1) instanceof EmptyCell){
					board.getCellAt(x-1, y-1).open(board);
					board.getCellAt(x-1, y-1).setOpened(true);
				}
				else if(board.getCellAt(x-1, y-1) instanceof InfoCell){
					board.getCellAt(x-1, y-1).setOpened(true);
				}
			}
		}
		
		if(!board.outOfBoard(x-1, y)){
			if(!board.getCellAt(x-1, y).isFlagged() && !board.getCellAt(x-1, y).isOpened()){
				if(board.getCellAt(x-1, y) instanceof EmptyCell){
					board.getCellAt(x-1, y).open(board);
					board.getCellAt(x-1, y).setOpened(true);
				}
				else if(board.getCellAt(x-1, y) instanceof InfoCell){
					board.getCellAt(x-1, y).setOpened(true);
				}
			}
		}
		
		if(!board.outOfBoard(x-1, y+1)){
			if(!board.getCellAt(x-1, y+1).isFlagged() && !board.getCellAt(x-1, y+1).isOpened()){
				if(board.getCellAt(x-1, y+1) instanceof EmptyCell){
					board.getCellAt(x-1, y+1).open(board);
					board.getCellAt(x-1, y+1).setOpened(true);
				}
				else if(board.getCellAt(x-1, y+1) instanceof InfoCell){
					board.getCellAt(x-1, y+1).setOpened(true);
				}
			}
		}
		
		if(!board.outOfBoard(x, y-1)){
			if(!board.getCellAt(x, y-1).isFlagged() && !board.getCellAt(x, y-1).isOpened()){
				if(board.getCellAt(x, y-1) instanceof EmptyCell){
					board.getCellAt(x, y-1).open(board);
					board.getCellAt(x, y-1).setOpened(true);
				}
				else if(board.getCellAt(x, y-1) instanceof InfoCell){
					board.getCellAt(x, y-1).setOpened(true);
				}
			}
		}
		
		if(!board.outOfBoard(x, y+1)){
			if(!board.getCellAt(x, y+1).isFlagged() && !board.getCellAt(x, y+1).isOpened()){
				if(board.getCellAt(x, y+1) instanceof EmptyCell){
					board.getCellAt(x, y+1).open(board);
					board.getCellAt(x, y+1).setOpened(true);
				}
				else if(board.getCellAt(x, y+1) instanceof InfoCell){
					board.getCellAt(x, y+1).setOpened(true);
				}
			}
		}
		
		if(!board.outOfBoard(x+1, y-1)){
			if(!board.getCellAt(x+1, y-1).isFlagged() && !board.getCellAt(x+1, y-1).isOpened()){
				if(board.getCellAt(x+1, y-1) instanceof EmptyCell){
					board.getCellAt(x+1, y-1).open(board);
					board.getCellAt(x+1, y-1).setOpened(true);
				}
				else if(board.getCellAt(x+1, y-1) instanceof InfoCell){
					board.getCellAt(x+1, y-1).setOpened(true);
				}
			}
		}
		
		if(!board.outOfBoard(x+1, y)){
			if(!board.getCellAt(x+1, y).isFlagged() && !board.getCellAt(x+1, y).isOpened()){
				if(board.getCellAt(x+1, y) instanceof EmptyCell){
					board.getCellAt(x+1, y).open(board);
					board.getCellAt(x+1, y).setOpened(true);
				}
				else if(board.getCellAt(x+1, y) instanceof InfoCell){
					board.getCellAt(x+1, y).setOpened(true);
				}
			}
		}
		
		if(!board.outOfBoard(x+1, y+1)){
			if(!board.getCellAt(x+1, y+1).isFlagged() && !board.getCellAt(x+1, y+1).isOpened()){
				if(board.getCellAt(x+1, y+1) instanceof EmptyCell){
					board.getCellAt(x+1, y+1).open(board);
					board.getCellAt(x+1, y+1).setOpened(true);
				}
				else if(board.getCellAt(x+1, y+1) instanceof InfoCell){
					board.getCellAt(x+1, y+1).setOpened(true);
				}
			}
		}
		
		
	}

	@Override
	public void delete(Board board) {
		board.setCellAt(x, y, null);
		board.getPlayer().openNewNonBombCell();
	}
}
