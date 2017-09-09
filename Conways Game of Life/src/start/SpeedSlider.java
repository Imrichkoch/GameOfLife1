package start;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpeedSlider extends JPanel {

	private GameFrame g;
	public JLabel lblResult;
	public JSlider slider;
	private int sliSpeed;

	public int getSliSpeed() {
		return sliSpeed;
	}

	public void setSliSpeed(int sliSpeed) {
		this.sliSpeed = sliSpeed;
	}

	public SpeedSlider(final GameFrame g) {
		this.g = g;
		this.setSize(500, 50);
		lblResult = new JLabel();

		slider = new JSlider(1, 1000, 500);

		slider.setBorder(new TitledBorder("Speed"));
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {

				lblResult.setText("ms/cycle: " + String.valueOf(slider.getValue() + g.game.getSpeed()));
				int s = slider.getValue();
				sliSpeed = slider.getValue();

			}

		});
		add(lblResult);
		add(slider);

	}
}
