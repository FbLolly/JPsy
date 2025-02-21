package mainPkg;

import java.awt.Graphics;
import java.awt.Graphics2D;

import GameObjectPkg.DeathTimer;
import GameObjectPkg.Player;
import gameManagement.Dialogue;
import gameManagement.Map;
import graphicsPkg.ImageList;
import utilsPkg.Camera;
import utilsPkg.KeyHandler;
import utilsPkg.Mouse;
import utilsPkg.RayPoint;

public class Game {
    public Map map;
    public Player player;
    private Camera cam;
    private ImageList imageList;
    private DeathTimer deathTimer;
    private Dialogue dialogue;

    public KeyHandler keyHandler;
    private Mouse mouse;

    private JApp app; //only used for input, nothing else

    public Game(JApp app){
        this.map = new Map("1");

        RayPoint playerPos = this.map.getSpawn();
        this.player = new Player(playerPos.x, playerPos.y, Defines.tileSize, Defines.tileSize);
        this.cam = new Camera(0, 0);
        this.imageList = new ImageList();
        this.deathTimer = new DeathTimer();
        this.dialogue = new Dialogue("1");

        this.keyHandler = new KeyHandler();
        this.mouse = new Mouse();

        this.app = app;
    }

    public void updateIO(){
      this.keyHandler.handleKeys(this.app);
      this.mouse.updateMousePos(this.app);
    }

    public void update(){
      this.updateIO();

      this.dialogue.update(this.keyHandler.KeyMap);

      this.cam.update(this.player);
      this.player.loadInput(this.keyHandler.KeyMap);
      this.player.update(this);

      this.deathTimer.update(this.map, this.player);

      this.map.update(this.player);
    }

    public void paintComponent(Graphics g){
        //paint background
		g.setColor(Defines.bgc);
		g.fillRect(0, 0, Defines.width, Defines.height);
		//-

		if (Defines.fontMetrics == null){
			Defines.fontMetrics = g.getFontMetrics();
			this.dialogue.updateShowing();
		}

		this.cam.translate((Graphics2D) g);

		this.map.paintComponent(g, this.imageList);
		this.player.paintComponent(g, this.imageList);

		this.map.paintLighting(g, this.imageList, this.cam);
		this.dialogue.paintComponent(g, cam);
		this.player.paintInventory(g, this.imageList, this.cam);
		this.deathTimer.paintComponent(g, this.imageList, this.cam);

		this.cam.untranslate((Graphics2D) g);
    }
}