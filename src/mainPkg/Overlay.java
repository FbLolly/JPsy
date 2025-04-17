package mainPkg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Overlay extends JPanel {
    private Game game;
    
    public Overlay(Game game){
        this.game = game;

		this.setDoubleBuffered(true);
		this.setPreferredSize(new Dimension(Defines.width, Defines.height));
		this.setFocusable(true);

		this.setSize(new Dimension(Defines.width, Defines.height));
		this.setBounds(0, 0, Defines.width, Defines.height);
		this.setVisible(true);
    }

    public void paintComponent(Graphics g){
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, Defines.width, Defines.height);

        this.game.deathTimer.paintComponent(g, this.game.imageList, this.game.cam);

        this.game.dialogue.paintComponent(g, this.game.cam);
    }
}
