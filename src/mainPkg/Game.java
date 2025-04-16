package mainPkg;

import java.awt.Graphics;
import java.awt.Graphics2D;

import GameObjectPkg.DeathTimer;
import GameObjectPkg.Dog;
import GameObjectPkg.Entity;
import GameObjectPkg.Player;
import gameManagement.Dialogue;
import gameManagement.Map;
import gameManagement.ObjectEventHandler;
import graphicsPkg.ImageList;
import menusPkg.DeathScreen;
import particlesPkg.Particles;
import saves.Save;
import utilsPkg.Camera;
import utilsPkg.KeyHandler;
import utilsPkg.Mouse;
import utilsPkg.RayPoint;

public class Game {
    public Map map;
    public Player player;
    public Dog dog;
    public Camera cam;
    private ImageList imageList;
    public DeathTimer deathTimer;
    public Dialogue dialogue;
    public ObjectEventHandler oeh;

    public DeathScreen ds = new DeathScreen(this);

    public KeyHandler keyHandler;
    private Mouse mouse;

    Particles particles;

    private JApp app; //only used for input, nothing else

    public Game(JApp app){
        this.dialogue = new Dialogue();
        this.map = new Map(1, this);
        this.oeh = new ObjectEventHandler();

        RayPoint playerPos = this.map.getSpawn();
        this.player = new Player(playerPos.x, playerPos.y, Defines.tileSize, Defines.tileSize);
        this.dog = new Dog(playerPos.x, playerPos.y, Defines.tileSize, Defines.tileSize, this.player);
        this.cam = new Camera(0, 0);
        this.imageList = new ImageList();
        this.deathTimer = new DeathTimer(Defines.standardDeathTimer);

        this.keyHandler = new KeyHandler();
        this.mouse = new Mouse();

        this.particles = new Particles();

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
      try{
        this.dog.update(this);
      }catch(NullPointerException e){}

      this.deathTimer.update(this.map, this.player);
      this.particles.update();

      this.map.update(
        new Entity[]{this.player, this.dog}
      );
    }

    public void paintComponent(Graphics g){
      //paint background
      g.setColor(Defines.bgc);
      g.fillRect(0, 0, Defines.width, Defines.height);
      //-

      if (Defines.fontMetrics == null){
        Defines.fontMetrics = g.getFontMetrics();
        Dialogue.updateShowing();
      }

      if (Defines.scale){
        Defines.scale = true;
        ((Graphics2D) g).scale(Defines.zoom, Defines.zoom);
      }

      this.cam.translate((Graphics2D) g);

      this.map.paintComponent(g, this.imageList, cam);
      this.player.paintComponent(g, this.imageList);
      try{
        this.dog.paintComponent(g, imageList);
      }catch(NullPointerException e){}

      this.particles.paintComponent(g, imageList);


      this.map.paintLighting(g, this.imageList, this.cam);
      this.player.paintInventory(g, this.imageList, this.cam);
      this.deathTimer.paintComponent(g, this.imageList, this.cam);

      this.dialogue.paintComponent(g, cam);

      this.cam.untranslate((Graphics2D) g);
    }

    public void changeDialogue(int dialogue){
      this.dialogue.refresh(dialogue);
    }
}