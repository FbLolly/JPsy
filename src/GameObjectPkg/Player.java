package GameObjectPkg;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;

import gameManagement.Map;
import graphicsPkg.ImageList;
import mainPkg.Defines;
import mainPkg.Game;
import utilsPkg.Camera;
import utilsPkg.RayPoint;

public class Player extends Entity {
    public HashMap<String, Integer> inputMap;
    private double prevx, prevy;
    private Point facing;
    public Inventory inv;

    public String gFacing = "";
    public String movement = "";
    
    public Player(double x, double y, double width, double height) {
        super(x, y, width, height);

        facing = new Point(0, +1);
        inv = new Inventory();

        movement = "idle";
        gFacing = "down";
    }
    
    public void loadInput(HashMap<String, Integer> map){
        this.inputMap = map;
    }

    public void updateWithKeys(){
        this.movement = "idle";

        if (this.inputMap.get("W") == 1){
            this.setSpeedY(-Defines.defaultSpeed);
            facing.x = 0; facing.y = -1;
            this.movement = "walking";
            this.gFacing = "up";

            return;
        }
        if (this.inputMap.get("S") == 1){
            this.setSpeedY(Defines.defaultSpeed);
            facing.x = 0; facing.y = +1;
            this.movement = "walking";
            this.gFacing = "down";

            return;
        }

        if (this.inputMap.get("A") == 1){
            this.setSpeedX(-Defines.defaultSpeed);
            facing.x = -1; facing.y = 0;
            this.movement = "walking";
            this.gFacing = "left";

            return;
        }
        if (this.inputMap.get("D") == 1){
            this.setSpeedX(Defines.defaultSpeed);
            facing.x = +1; facing.y = 0;
            this.movement = "walking";
            this.gFacing = "right";

            return;
        }
    }

    public void sprintListen(){
        if (this.inputMap.get("SPACE") == 1){
            this.setSpeedX(this.getSpeedX() * 1.5);
            this.setSpeedY(this.getSpeedY() * 1.5);

            if (this.movement.equals("walking"))
                this.movement = "running";

            return;
        }

        if (this.getSpeedX() > Defines.defaultSpeed || this.getSpeedX() < -Defines.defaultSpeed)
            this.setSpeedX(this.getSpeedX()/2);
        if (this.getSpeedY() > Defines.defaultSpeed || this.getSpeedY() < -Defines.defaultSpeed)
            this.setSpeedY(this.getSpeedY()/2);
    }

    public Point getInteractingPos(){
        Point playerPos = Map.getMapPosFromPoint(this.x, this.y);

        return new Point(
            playerPos.x + this.facing.x,
            playerPos.y + this.facing.y
        );
    }

    public void updateInteracting(Map map){
        Point interactingPos = getInteractingPos();

        if (!map.isValidPoint(interactingPos))
            return;

        if (this.inputMap.get("E") != 1)
            return;
        this.inputMap.replace("E", 0);

        if (!map.map[interactingPos.x][interactingPos.y].isInteractable())
            return;

        InteractiveObject obj = (InteractiveObject) map.map[interactingPos.x][interactingPos.y];
        obj.interact(this);
    }

    public void updatePos(){
        this.setSpeedX(0);
        this.setSpeedY(0);

        this.updateWithKeys();
        this.sprintListen();
    }


    public void update(Game game){
        //updates player pos (and speed) with input
        if (this.inputMap == null)
            return;

        prevx = this.getX();
        prevy = this.getY();

        updatePos();

        super.update();

        this.updateInteracting(game.map);
        inv.update(game);

        if (!game.map.isValidPoint(Map.getMapPosFromPoint(x, y))){
            game.map = new Map(""+game.map.nextRoom);
            
            RayPoint pos = game.map.getSpawn();
            this.x = pos.x;
            this.y = pos.y;
        }
    }

    public void automaticRound(Rectangle rect){
        if (rect.width > 3)
            this.y = this.prevy;
        if (rect.height > 3)
            this.x = this.prevx;
    }

    public void paintComponent(Graphics g, ImageList imageList){
        g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages(this.movement+"_"+this.gFacing, (int)Defines.tileSize*2, (int)Defines.tileSize*2)),
                    (int)(this.x - Defines.tileSize/2), (int)(this.y - Defines.tileSize), null);
    }

    public void paintInventory(Graphics g, ImageList imageList, Camera cam){
        inv.paintComponent(g, imageList, cam);
    }
}
