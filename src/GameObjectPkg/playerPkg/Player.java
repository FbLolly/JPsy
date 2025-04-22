package GameObjectPkg.playerPkg;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;

import GameObjectPkg.Entity;
import GameObjectPkg.InteractiveObject;
import gameManagement.Map;
import graphicsPkg.ImageList;
import mainPkg.Defines;
import mainPkg.Game;
import particlesPkg.Particles;
import saves.Save;
import utilsPkg.RayPoint;

public class Player extends Entity {
    public HashMap<String, Integer> inputMap;
    public Point facing;
    public Inventory inv;
    public String gFacing = "";
    public String movement = "";
    private Stamina stamina;
    private boolean reset = false;

    public Player(double x, double y, double width, double height) {
        super(x, y, width, height);

        facing = new Point(0, +1);
        inv = new Inventory();

        movement = "idle";
        gFacing = "down";

        this.stamina = new Stamina(this);
    }

    public void loadInput(HashMap<String, Integer> map) {
        this.inputMap = map;
    }

    public void updateWithKeys() {
        if (Defines.lockedDialogue)
            return;

        this.movement = "idle";

        Point old = new Point(facing);

        facing.y = -this.inputMap.get("W") + this.inputMap.get("S");
        facing.x = -this.inputMap.get("A") + this.inputMap.get("D");

        if (facing.x == 0 && facing.y == 0) {
            facing = old;
            return;
        }

        if (facing.x == 1 && facing.y == 0) {
            this.gFacing = "right";
        } else if (facing.x == -1 && facing.y == 0) {
            this.gFacing = "left";
        } else if (facing.x == 0 && facing.y == 1) {
            this.gFacing = "down";
        } else if (facing.x == 0 && facing.y == -1) {
            this.gFacing = "up";
        } else {
            facing = old;
        }

        this.setSpeedX(facing.x * Defines.defaultSpeed);
        this.setSpeedY(facing.y * Defines.defaultSpeed);

        this.movement = "walking";
        return;
    }

    public void sprintListen() {
        if (Defines.lockedDialogue)
            return;

        if (this.inputMap.get("SPACE") == 1) {
            this.setSpeedX(this.getSpeedX() * 1.5);
            this.setSpeedY(this.getSpeedY() * 1.5);

            if (!this.reset) {
                Defines.animationTimer = 0;
                this.reset = true;
            }

            if (this.movement.equals("walking"))
                this.movement = "running";

            return;
        } else {
            this.reset = false;
        }

        if (this.getSpeedX() > Defines.defaultSpeed || this.getSpeedX() < -Defines.defaultSpeed)
            this.setSpeedX(this.getSpeedX() / 2);
        if (this.getSpeedY() > Defines.defaultSpeed || this.getSpeedY() < -Defines.defaultSpeed)
            this.setSpeedY(this.getSpeedY() / 2);
    }

    public Point getInteractingPos() {
        Point playerPos = Map.getMapPosFromPoint(this.x, this.y);

        return new Point(
                playerPos.x + this.facing.x,
                playerPos.y + this.facing.y
        );
    }

    public void updateInteracting(Game game) {
        if (Defines.lockedDialogue)
            return;

        Point interactingPos = getInteractingPos();

        if (!game.map.isValidPoint(interactingPos))
            return;

        if (this.inputMap.get("E") != 1)
            return;
        this.inputMap.replace("E", 0);

        if (!game.map.map[interactingPos.x][interactingPos.y].isInteractable())
            return;

        InteractiveObject obj = (InteractiveObject) game.map.map[interactingPos.x][interactingPos.y];
        obj.interact(this, game);
    }

    public void updatePos() {
        this.setSpeedX(0);
        this.setSpeedY(0);

        this.updateWithKeys();
        this.sprintListen();
    }


    public void update(Game game) {
        //updates player pos (and speed) with input
        if (this.inputMap == null)
            return;

        prevx = this.getX();
        prevy = this.getY();

        updatePos();

        super.update();

        this.updateInteracting(game);
        inv.update(game);

        if (!game.map.isValidPoint(Map.getMapPosFromPoint(x, y))) {
            game.map = new Map(game.map.nextRoom, game);

            Save.saveEverything(game);

            RayPoint pos = game.map.getSpawn();
            this.x = pos.x;
            this.y = pos.y;
        }

        /* walking effects */

        Point p = Map.getMapPosFromPoint(this.x, this.y);
        if (game.map.indexedEntry(p.x, p.y).type == 30 && !(this.movement.equals("idle"))) {
            if (Defines.timer % (int) (Defines.FPS / 4) != 0)
                return;

            Particles.addParticle(new Point((int) (this.x + Defines.tileSize / 2.0), (int) (this.y + Defines.tileSize / 2.0)), new Point(0, 0), "blood", 0.2, 0.4, 2);
        }

        this.stamina.update();
    }

    public void paintComponent(Graphics g, ImageList imageList) {
        g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages(this.movement + "_" + this.gFacing, (int) Defines.tileSize * 2, (int) Defines.tileSize * 2)),
                (int) (this.x - Defines.tileSize / 2), (int) (this.y - Defines.tileSize), null);

        this.stamina.paintComponent(g);
    }

    public void paintInventory(Graphics g, Game game) {
        inv.paintComponent(g, game);
    }
}
