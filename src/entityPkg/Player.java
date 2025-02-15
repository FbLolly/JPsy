package entityPkg;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;

import gameManagement.Map;
import mainPkg.Defines;

public class Player extends Entity {
    public HashMap<String, Integer> map;
    
    public Player(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
    
    public void loadInput(HashMap<String, Integer> map){
        this.map = map;
    }

    @Override
    public void update(){
        //updates player pos (and speed) with input
        if (this.map == null)
            return;

        this.setSpeedX(0);
        this.setSpeedY(0);

        if (this.map.get("W") == 1){
            this.setSpeedY(-Defines.defaultSpeed);
        }
        if (this.map.get("S") == 1){
            this.setSpeedY(Defines.defaultSpeed);
        }

        if (this.map.get("A") == 1){
            this.setSpeedX(-Defines.defaultSpeed);
        }
        if (this.map.get("D") == 1){
            this.setSpeedX(Defines.defaultSpeed);
        }

        super.update();
    }

    public void roundToClosestTileX(){
        double posx;

        Point defaultPoint = Map.getMapPosFromPoint(x, y);

        posx = this.x / Defines.tileSize;
        posx -= (double)((int)(posx));

        if (posx > 0.5){
            defaultPoint.x += 1;
        }

        this.x = defaultPoint.x*Defines.tileSize;
    }

    public void roundToClosestTileY(){
        double posy;

        Point defaultPoint = Map.getMapPosFromPoint(x, y);

        posy = this.y / Defines.tileSize;
        posy -= (double)((int)(posy));

        if (posy > 0.5){
            defaultPoint.y += 1;
        }

        this.y = defaultPoint.y*Defines.tileSize;
    }

    public void roundToClosestTile(){
        this.roundToClosestTileX();
        this.roundToClosestTileY();
    }

    public void automaticRound(Rectangle rect){
        if (rect.width <= 3){
            this.roundToClosestTileX();
            return;
        }

        if (rect.height <= 3){
            this.roundToClosestTileY();
            return;
        }

        this.roundToClosestTile();
    }
}
