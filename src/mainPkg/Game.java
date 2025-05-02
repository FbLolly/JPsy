package mainPkg;

import java.awt.Graphics;
import java.awt.Graphics2D;

import EffectPkg.DeathTimer;
import GameObjectPkg.Dog;
import GameObjectPkg.Entity;
import GameObjectPkg.playerPkg.Player;
import eventPkg.EventHandler;
import gameManagement.Dialogue;
import gameManagement.Map;
import gameManagement.ObjectEventHandler;
import graphicsPkg.ImageList;
import menusPkg.DeathScreen;
import menusPkg.JumpScare;
import particlesPkg.Particles;
import utilsPkg.Camera;
import utilsPkg.KeyHandler;
import utilsPkg.Mouse;
import utilsPkg.RayPoint;

public class Game {
    public Map map;
    public Player player;
    public Dog dog;
    public Camera cam;
    public ImageList imageList;
    public DeathTimer deathTimer;
    public Dialogue dialogue;
    public ObjectEventHandler oeh;
    public DeathScreen ds;
    public KeyHandler keyHandler;
    public JApp app;
    private Mouse mouse;
    private Particles particles;
    private EventHandler eventHandler;
    public JumpScare jumpScare;

    public Game(JApp app) {
        this.dialogue = new Dialogue();
        this.map = new Map(1, this);
        this.oeh = new ObjectEventHandler();
        this.ds = new DeathScreen(this);

        RayPoint playerPos = this.map.getSpawn();

        this.player = new Player(playerPos.x, playerPos.y, Defines.tileSize, Defines.tileSize);
        this.dog = new Dog(playerPos.x, playerPos.y, Defines.tileSize, Defines.tileSize, this.player);
        this.cam = new Camera(0, 0);
        this.imageList = new ImageList();
        this.deathTimer = new DeathTimer(Defines.standardDeathTimer);

        this.keyHandler = new KeyHandler();
        this.mouse = new Mouse();

        this.particles = new Particles();
        this.eventHandler = new EventHandler(this);
        this.jumpScare = new JumpScare();
        this.app = app;
    }

    public void updateIO() {
        this.keyHandler.handleKeys(this.app);
        this.mouse.updateMousePos(this.app);
    }

    public void update() {
        this.updateIO();
        this.dialogue.update(this.keyHandler.KeyMap);
        this.cam.update(this.player);
        this.player.loadInput(this.keyHandler.KeyMap);
        this.player.update(this);

        try {
            this.dog.update(this);
        } catch (NullPointerException e) {
        }

        this.deathTimer.update(this);
        this.particles.update();
        this.map.update(
                new Entity[]{this.player, this.dog}
        );
    }

    public int paintComponent(Graphics g) {
        //paint background
        g.setColor(Defines.bgc);
        g.fillRect(0, 0, Defines.width, Defines.height);
        //-

        if (Defines.fontMetrics == null) {
            Defines.fontMetrics = g.getFontMetrics();
            Dialogue.updateShowing();
        }

        //Zoomer.update(this);
        //Zoomer.Zoom(2);

        ((Graphics2D) g).scale(Defines.zoom, Defines.zoom);

        this.cam.translate((Graphics2D) g);
        this.map.paintComponent(g, this.imageList, cam);
        this.player.paintComponent(g, this.imageList);

        try {
            this.dog.paintComponent(g, imageList);
        } catch (NullPointerException e) {
        }

        this.particles.paintComponent(g, imageList);
        this.map.paintLighting(g, this);
        this.deathTimer.paintComponent(g, this);
        this.player.paintInventory(g, this);
        this.dialogue.paintComponent(g, this);
        return this.jumpScare.paintComponent(g, this);
    }

    public void changeDialogue(int dialogue) {
        this.dialogue.refresh(dialogue);
    }
}
