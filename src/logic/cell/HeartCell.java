package logic.cell;
/* fill code */

import logic.Board;

public class HeartCell extends Cell{

	public HeartCell(int x, int y) {
		super(x,y);
		this.x=x;
		this.y=y;
	}

	@Override
	protected void actionOnOpen(Board board) {
		board.getPlayer().setLife(board.getPlayer().getLife()+1);
	}

}
