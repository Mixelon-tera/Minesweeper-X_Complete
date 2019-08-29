package logic.cell;
/* fill code */

import logic.Board;

public class BombCell extends Cell{

	public BombCell(int x, int y) {
		super(x,y);
		this.x=x;
		this.y=y;
	}

	@Override
	protected void actionOnOpen(Board board) {
		board.getPlayer().setLife(board.getPlayer().getLife()-1);
	}
}
