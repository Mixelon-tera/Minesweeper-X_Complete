package lib;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import logic.Board;
import logic.cell.BombCell;
import logic.cell.Cell;
import logic.cell.EmptyCell;
import logic.cell.HeartCell;
import logic.cell.InfoCell;

public class GameScreen extends JPanel {

	private static BufferedImage getImage(String directory) {
		try {
			URL url = GameScreen.class.getClassLoader().getResource(directory);
			return ImageIO.read(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return null;
	}

	private static final Font font = new Font("Tahoma", Font.PLAIN, 20);
	private static final int TILE_SIZE = 17;
	private static final int TIMER_TICK = 20;
	private Board board;
	private int w, h;
	private int timerTick = TIMER_TICK;
	private BufferedImage[] im;

	private GameScreen(int width, int height, final Board board) {
		super();
		w = width;
		h = height;
		setPreferredSize(new Dimension(width * TILE_SIZE + 10, height
				* TILE_SIZE + 20));
		this.board = board;
		im = new BufferedImage[15];
		im[0] = getImage("res/img/empty.png");
		for (int i = 1; i <= 8; i++) {
			im[i] = getImage("res/img/" + i + ".png");
		}
		im[9] = getImage("res/img/exposed mine.png");
		im[10] = getImage("res/img/covered.png");
		im[11] = getImage("res/img/flag.png");
		im[12] = getImage("res/img/failed.png");
		im[13] = getImage("res/img/covered mine.png");
		im[14] = getImage("res/img/heart.png");

		setDoubleBuffered(true);

		addMouseListener(new MyMouseAdapter());

		new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (board.getPlayer().isGameover())
					return;
				timerTick--;
				if (timerTick == 0) {
					board.deleteOnTimer();
					timerTick = TIMER_TICK;
				}
				repaint();
			}
		}).start();
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setBackground(Color.BLACK);
		g2.clearRect(0, 0, (int) getSize().getWidth(), (int) getSize()
				.getHeight());
		Cell cell;
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				cell = board.getCellAt(x, y);
				if (cell == null)
					continue;
				if (cell.isFlagged()) {
					if (board.getPlayer().isGameover()
							&& !(cell instanceof BombCell))
						g2.drawImage(im[12], null, x * TILE_SIZE + 1, y
								* TILE_SIZE + 1);
					else
						g2.drawImage(im[11], null, x * TILE_SIZE + 1, y
								* TILE_SIZE + 1);
				} else if (!cell.isOpened()) {
					if (board.getPlayer().isGameover()) {
						if (cell instanceof BombCell)
							g2.drawImage(board.getPlayer().isWin() ? im[10]
									: im[13], null, x * TILE_SIZE + 1, y
									* TILE_SIZE + 1);
						else if (cell instanceof HeartCell)
							g2.drawImage(im[14], null, x * TILE_SIZE + 1, y
									* TILE_SIZE + 1);
						else
							g2.drawImage(im[10], null, x * TILE_SIZE + 1, y
									* TILE_SIZE + 1);
					} else
						g2.drawImage(im[10], null, x * TILE_SIZE + 1, y
								* TILE_SIZE + 1);
				} else {
					if (cell instanceof EmptyCell)
						g2.drawImage(im[0], null, x * TILE_SIZE + 1, y
								* TILE_SIZE + 1);
					else if (cell instanceof InfoCell)
						g2.drawImage(im[((InfoCell) cell).getNumber()], null, x
								* TILE_SIZE + 1, y * TILE_SIZE + 1);
					else if (cell instanceof HeartCell)
						g2.drawImage(im[14], null, x * TILE_SIZE + 1, y
								* TILE_SIZE + 1);
					else {
						if (!board.getPlayer().isWin())
							g2.drawImage(im[9], null, x * TILE_SIZE + 1, y
									* TILE_SIZE + 1);
					}
				}
			}
		}

		g2.setFont(font);
		if (board.getPlayer().isGameover()) {
			Rectangle2D txtRect = g2.getFontMetrics(font).getStringBounds(
					board.getPlayer().isWin() ? "Win" : "Gameover", g2);
			g2.setColor(Color.WHITE);
			g2.drawString(board.getPlayer().isWin() ? "Win" : "Gameover",
					(int) (w * TILE_SIZE - txtRect.getWidth()) / 2, (int) (h
							* TILE_SIZE - txtRect.getHeight()) / 2);
		}

		g2.setColor(Color.WHITE);
		g2.drawString("LIFE " + board.getPlayer().getLife() + "/3", 2, h
				* TILE_SIZE + 20 - 2);
		g2.drawString("SCORE " + board.getPlayer().getScore(), w * TILE_SIZE
				/ 2, h * TILE_SIZE + 20 - 2);

		g2.setColor(Color.GREEN);
		int tickMeterHeight = (h * TILE_SIZE - 4) * timerTick / TIMER_TICK;
		g2.fillRect(w * TILE_SIZE + 2, h * TILE_SIZE - 2 - tickMeterHeight, 6,
				tickMeterHeight);
	}

	private class MyMouseAdapter extends MouseAdapter {
		boolean isLeftPressed = false;
		boolean isRightPressed = false;

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1 && !isLeftPressed) {
				isLeftPressed = true;
				board.leftClickAt(e.getX() / TILE_SIZE, e.getY() / TILE_SIZE);
				repaint();
			} else if (e.getButton() == MouseEvent.BUTTON3 && !isRightPressed) {
				isRightPressed = true;
				board.rightClickAt(e.getX() / TILE_SIZE, e.getY() / TILE_SIZE);
				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				isLeftPressed = false;
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				isRightPressed = false;
			}
		}
	}

	public static void createGameScreen(int width, int height, Board board) {
		JFrame frame = new JFrame("Minesweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new GameScreen(width, height, board));
		frame.pack();
		frame.setVisible(true);
	}
}
