package start;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

import javax.swing.Timer;

public class CoreGame extends Canvas {
	private byte[][] cells, newCells;

	private long cycles;
	private int size = 100;
	private Timer t;
	private BufferStrategy bf;
	private GameFrame g;

	private int speed = 500;

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	CoreGame(final GameFrame g) {
		this.g = g;
		bf = getBufferStrategy();
		setSize(size, size);
		setBackground(Color.WHITE);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int x = e.getX() / (getWidth() / size);
				int y = e.getY() / (getHeight() / size);

				// kliknutie mimo mriezky
				if ((x > 100) || (y > 100)) {
					x = 0;
					y = 0;

				}

				cells[x][y] = (byte) (~cells[x][y] & 1); // ozivnutie bunky

				repaint();
			}
		});
		t = new Timer(speed, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cycle();
				g.buttons.setCycles();
				g.game.setSpeed(1000);
			}
		});

	}

	@Override
	public void paint(Graphics g) {
		createBufferStrategy(1);

		bf = getBufferStrategy();
		g = null;
		try {
			g = bf.getDrawGraphics();
			render(g);
		} finally {
			g.dispose();
		}
		bf.show();
		Toolkit.getDefaultToolkit().sync();

	}

	// vykreslenie hracej plochy
	private void render(Graphics g) {
		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				if (cells[x][y] == 1) {
					g.setColor(Color.black);
					g.fillRect(x * (getWidth() / size), y * (getHeight() / size), getWidth() / size, getWidth() / size);
				}
				g.setColor(Color.GRAY);
				g.drawRect(x * (getWidth() / size), y * (getHeight() / size), getWidth() / size, getWidth() / size);
			}
		}
	}

	// resetnutie hracej plochy
	public void init() {
		cells = new byte[size][size];
		// nastavenie vsetkych buniek ako mrtve
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j] = 0;
			}
		}
		cycles = 0;
		g.buttons.setCycles();
		repaint();
	}

	// kolko ubehlo tickov od zaciatku simulacie
	public long getCycles() {
		return cycles;
	}

	// start simulacie
	public void start() {
		t.start();

	}

	// zastavenie simulacie
	public void stop() {
		t.stop();

	}

	// v tejto metode zistim kolko ma dana bunka susedov a teda ci zije alebo je
	// mrtva alebo sa ma vytvorit nova bunka
	private void cycle() {
		cycles++;
		newCells = new byte[size][size];
		int totalSum = 0;
		// The array should wrap around so that cells[100][101] refers to the
		// top right cell
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				// pocita ju sa sa pozicie susednych riadkov a stlpcov, pomocou
				// modula si zistim kde sa ma nachadzat
				// riadok/stlpec v nekonecnom poli
				int ip = (((i + 1) % cells.length) + cells.length) % cells.length;
				int im = (((i - 1) % cells.length) + cells.length) % cells.length;
				int jp = (((j + 1) % cells[i].length) + cells[i].length) % cells[i].length;
				int jm = (((j - 1) % cells[i].length) + cells[i].length) % cells[i].length;

				byte[] neighboursCells = { cells[im][j], cells[ip][j], cells[i][jp], cells[i][jm], cells[ip][jp],
						cells[ip][jm], cells[im][jp], cells[im][jm] };

				byte sum = 0;
				for (byte b : neighboursCells) {
					sum += b;
				}
				totalSum += sum;
				if (cells[i][j] == 1) {
					// pocet zivych susediacich buniek
					if (sum < 2) {
						newCells[i][j] = 0;
					} else if (sum > 3) {
						newCells[i][j] = 0;
					} else {
						newCells[i][j] = 1;
					}
				} else if (sum == 3) {
					// 3 zive bunky
					newCells[i][j] = 1;
				} else {
					newCells[i][j] = 0;
				}
			}
		}
		if (totalSum == 0) {
			// simulacia sa zastavi ak su vsetky bunky mrtve

			stop();
		}
		cells = newCells;
		repaint();
	}
}