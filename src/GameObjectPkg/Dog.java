package GameObjectPkg;
import java.awt.Graphics;
import java.awt.Point;

import gameManagement.Map;
import graphicsPkg.ImageList;
import mainPkg.Defines;
import mainPkg.Game;
import particlesPkg.Particles;
import utilsPkg.Camera;
import utilsPkg.RayPoint;

public class Dog extends Entity {
    private Entity follow;
    private boolean running;

    private String action;
    private String direction;

    private boolean sat_down;

    public Dog(double x, double y, double width, double height, Entity follow) {
        super(x, y, width, height);
        this.follow = follow;

        this.running = false;
        this.sat_down = false;
        action = "idle";
        direction = "right";
    }

    public void update(Game game){
        this.prevx = this.x;
        this.prevy = this.y;

        if (this.x >= follow.x - (Math.random()*10) && this.x <= follow.x + (Math.random()*10)){
            if (this.running && (int)(Math.random()*3) == 0){
                this.x -= Defines.runningSpeed;
                this.x += Defines.defaultSpeed;
            }
        
            this.running = true;
            this.sat_down = false;
        }

        if (this.running){
            if ((int)(Math.random()*50) == 0)
                this.y -= Defines.defaultSpeed;
            if ((int)(Math.random()*50) == 0)
                this.y += Defines.defaultSpeed;
            this.x += Defines.runningSpeed;

            this.sat_down = false;
            this.action = "walking";
            this.direction = "right";
        }
        
        if (this.x >= follow.x + (int)(Math.random()*400)+200){
            this.running = false;
            this.sat_down = false;
            this.action = "sitting";
            this.direction = "left";
        }

        if (!game.map.isValidPoint(Map.getMapPosFromPoint(x, y))){
            //means dog is outside the map

            Particles.addParticle(new Point((int)x, (int)y), new Point(-1, 0), "blood", 0.2, 1, 50);
            game.dog = null;

            game.cam.shake(0.5, 60);
        }
    }
    
    public void paintComponent(Graphics g, ImageList imageList){
        if (Defines.FPS < Defines.animationTimer && this.action == "sitting")
            this.sat_down = true;

        if (this.action == "sitting" && this.sat_down){
            this.action = "sat";
        }

        g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages("dog_"+this.action+"_"+this.direction, (int)Defines.tileSize*2, (int)Defines.tileSize*2)),
                (int)(this.x - Defines.tileSize/2), (int)(this.y - Defines.tileSize), null);
    }
}
