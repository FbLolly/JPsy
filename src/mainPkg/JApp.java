package mainPkg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;


import javax.swing.JPanel;

import entityPkg.Player;
import gameManagement.Dialogue;
import gameManagement.Map;
import utilsPkg.Camera;
import utilsPkg.KeyHandler;
import utilsPkg.Mouse;

public class JApp extends JPanel implements Runnable{
	private double drawInterval;
	private double nextDrawTime;
	private double remainingTime;
	private long timer;

	private Player player;
	private Map map;
	private Camera cam;
	
	private Thread thread;
	private KeyHandler keyHandler;

	private Dialogue dialogue;

	private Mouse mouse;

	private boolean isWindows;

	public JApp() {
		this.keyHandler = new KeyHandler();
		this.mouse = new Mouse();

		this.timer = 0;
		this.isWindows();
		
		try {
    		InputStream is = this.getClass().getResourceAsStream("fonts/CaskaydiaCove-Bold.ttf"); //im forced to put it in the same pkg
    		Font f = Font.createFont(Font.TRUETYPE_FONT, is);
    		Font sizedFont = f.deriveFont(Defines.fontSize);
    		this.setFont(sizedFont);
    	}catch (IOException | FontFormatException e) {
    		this.setFont(new Font("serif", Font.BOLD, 30));
    	}

		this.setDoubleBuffered(true);
		this.setPreferredSize(new Dimension(Defines.width, Defines.height));
		this.setSize(new Dimension(Defines.width, Defines.height));
		this.addMouseListener(mouse);
		this.setFocusable(true);
		this.setVisible(true);

		this.player = new Player(0, 0, Defines.tileSize, Defines.tileSize);
		this.cam = new Camera(0, 0);
		this.map = new Map("standard");
		this.dialogue = new Dialogue("1");
		
		this.startGame();
	}
	
	private void startGame() {
		thread = new Thread(this);
		thread.start();
	}
	
	private long getNewTimeAndSleep() {
    	drawInterval = 1000000000/Defines.FPS; //recalculate drawInterval in case the fps changes
		
    	try {
			remainingTime = nextDrawTime - System.nanoTime();
			remainingTime /= 1000000;
    	
			if (remainingTime < 0)
				remainingTime = 0;
			
			Thread.sleep((long)remainingTime);
			nextDrawTime += drawInterval;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	return (long)remainingTime;
    }

	private void isWindows(){
		String osName;
	
		osName = System.getProperty("os.name");

		this.isWindows = false;

		if (osName.length() > 7 && osName.substring(0, 7).equals("windows")){
			this.isWindows = true;
		}
	}
	
	@Override
	public void run() {
		drawInterval = 1000000000/Defines.FPS;
    	nextDrawTime = System.nanoTime() + drawInterval;
    	remainingTime = 0;
    	timer = 0;
    	
    	while (thread != null) {
        	getNewTimeAndSleep();
        	
        	update();
    		repaint();
    		
			if (!(this.isWindows)){
				Toolkit.getDefaultToolkit().sync();
			}
			
    		timer += 1;
    	}
	}

	private void updateIO(){
		this.keyHandler.handleKeys(this);
		this.mouse.updateMousePos(this);
	}
	
	private void update() {
		this.updateIO();

		dialogue.update(this.keyHandler.KeyMap);

		player.loadInput(this.keyHandler.KeyMap);
		player.update();

		cam.update(player);
		map.update(player);
	}
	
	public void paintComponent(Graphics g) {
		//paint background
		g.setColor(Color.black);
		g.fillRect(0, 0, Defines.width, Defines.height);
		//-

		cam.translate((Graphics2D) g);

		map.paintComponent(g);
		player.paintComponent(g);
		dialogue.paintComponent(g, cam);
		
		cam.untranslate((Graphics2D) g);
	}
}
