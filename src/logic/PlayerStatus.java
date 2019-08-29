package logic;
/* fill code */

public class PlayerStatus {

	private boolean gameover, win;
	private int score;
	private int life;
	private int requiredCellsOpening;

	public PlayerStatus(int requiredCellsOpening) {
		this.gameover=false;
		this.win=false;
		this.life=1;
		this.score=0;
		this.requiredCellsOpening=requiredCellsOpening;
	}

	public int getRequiredCellsOpening() {
		return requiredCellsOpening;
	}

	public void setRequiredCellsOpening(int requiredCellsOpening) {
		this.requiredCellsOpening = requiredCellsOpening;
	}

	public boolean isGameover() {
		return gameover;
	}

	public void setGameover(boolean gameover) {
		this.gameover = gameover;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life=life;
		if(life<=0){
			this.life=0;
			this.gameover=true;
		}
		if(life>3){
			this.life=3;
		}
	}

	public void openNewNonBombCell() {
		requiredCellsOpening--;
		if(requiredCellsOpening==0){
			this.setWin(true);
			this.setGameover(true);
		}
	}
}
