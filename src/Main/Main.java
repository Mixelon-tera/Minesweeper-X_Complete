package Main;
import lib.GameScreen;
import logic.Board;

public class Main {
	public static void main(String[] args) {
		Board board = new Board(16, 16, 30, 3);
		GameScreen.createGameScreen(16, 16, board);
	}
}
