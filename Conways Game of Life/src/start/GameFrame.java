package start;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
public class GameFrame {

    public CoreGame game;
    public GameButtons buttons;
    
    
    GameFrame() {
        
        JFrame frame = new JFrame("Game Of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game = new CoreGame(this);
        buttons = new GameButtons(this);
        frame.setLayout(new BorderLayout());
        frame.add(buttons,BorderLayout.PAGE_START);
        frame.add(game,BorderLayout.CENTER);
        game.init();
        frame.pack();
        frame.setSize(800, 800);
        frame.setVisible(true);
        
        
        //vycentruje mi okno
        Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width / 2) - (frame.getWidth() / 2);
		int yPos = (dim.height / 2) - (frame.getHeight() / 2);
		frame.setLocation(xPos, yPos);
        
    }

}
