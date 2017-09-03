package start;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class GameButtons extends  JPanel  {


	private GameFrame g;
	public JToggleButton stopStart;
	private JLabel CyclesCount;

	GameButtons(final GameFrame g) {
	    this.g = g;
		this.setSize(300, 50);
		stopStart = new JToggleButton("Start",false);
		stopStart.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				
				if(ev.getStateChange()== ItemEvent.SELECTED){
					stopStart.setText("Running");
					g.game.start();
				} else if(ev.getStateChange() == ItemEvent.DESELECTED) {
					stopStart.setText("Stopped");
					g.game.stop();
				}
				
			}
		});

		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				g.game.init();
				repaint();
			}
		});
		CyclesCount = new JLabel("Cycles:"+g.game.getCycles());
		add(stopStart);
		add(reset);
		add(CyclesCount);
	}
	//pocitadlo tikov
	public void setCycles() {
		CyclesCount.setText("Cycles:"+g.game.getCycles());
	}
}