package logic.cell;
import logic.Board;

/* fill code */

public abstract class Cell {

	protected int x, y;
	private boolean opened = false;
	private boolean flagged = false;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public Cell(int x, int y) {
		this.x=x;
		this.y=y;
	}

	public boolean isOpened() {
		return opened;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public void open(Board board) {
		opened = true;
		actionOnOpen(board);
		if (!(this instanceof BombCell)) {
			board.getPlayer().openNewNonBombCell();
		}
	}

	public void setFlagged(boolean value) {
		flagged = value;
	}

	abstract void actionOnOpen(Board board);
}
