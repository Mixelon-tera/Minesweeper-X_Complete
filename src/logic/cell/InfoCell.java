package logic.cell;
/* fill code */
import java.util.ArrayList;
/* fill code */

import logic.Board;
import logic.IDeletable;

public class InfoCell extends Cell implements IDeletable{

	public void setNumber(int number) {
		this.number = number;
	}

	private int  number;
	
	public InfoCell(int x, int y) {
		super(x,y);
		this.number=1;
		this.x=x;
		this.y=y;
	}
	
	public int getNumber(){
		return number;
	}
	
	public void increaseNumber(){
		this.number++;
		if(number>=8){
			number=8;
		}
	}

	@Override
	protected void actionOnOpen(Board board) {
		board.getPlayer().setScore(board.getPlayer().getScore()+(2*this.number));
	}

	@Override
	public void delete(Board board) {
		board.setCellAt(x, y, null);
		board.getPlayer().openNewNonBombCell();
	}

}
