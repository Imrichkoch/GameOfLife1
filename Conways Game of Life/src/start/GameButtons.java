package start;

import java.awt.event.ItemEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class GameButtons extends JPanel {

	private GameFrame g;
	public JToggleButton stopStart;
	private JLabel CyclesCount;

	GameButtons(final GameFrame g) {
		this.g = g;
		this.setSize(300, 50);
		stopStart = new JToggleButton("Start", false);
		stopStart.addItemListener(e -> {

			if (e.getStateChange() == ItemEvent.SELECTED) {
				stopStart.setText("Running");
				g.game.start();
			} else if (e.getStateChange() == ItemEvent.DESELECTED) {
				stopStart.setText("Stopped");
				g.game.stop();
			}

		});

		JButton reset = new JButton("Reset");
		reset.addActionListener(e -> {

			g.game.init();
			repaint();

		});

		CyclesCount = new JLabel("Cycles:" + g.game.getCycles());
		add(stopStart);
		add(reset);
		add(CyclesCount);
	}

	// pocitadlo tikov
	public void setCycles() {
		CyclesCount.setText("Cycles:" + g.game.getCycles());
	}
}