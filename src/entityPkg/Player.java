package entityPkg;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;

import graphicsPkg.ImageList;
import mainPkg.Defines;

public class Player extends Entity {
    public HashMap<String, Integer> map;
    private double prevx, prevy;
    
    public Player(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
    
    public void loadInput(HashMap<String, Integer> map){
        this.map = map;
    }

    public void updatePos(){
        this.setSpeedX(0);
        this.setSpeedY(0);

        if (this.map.get("W") == 1){
            this.setSpeedY(-Defines.defaultSpeed);
            return;
        }
        if (this.map.get("S") == 1){
            this.setSpeedY(Defines.defaultSpeed);
            return;
        }

        if (this.map.get("A") == 1){
            this.setSpeedX(-Defines.defaultSpeed);
            return;
        }
        if (this.map.get("D") == 1){
            this.setSpeedX(Defines.defaultSpeed);
            return;
        }
    }

    @Override
    public void update(){
        //updates player pos (and speed) with input
        if (this.map == null)
            return;

        prevx = this.getX();
        prevy = this.getY();

        updatePos();

        super.update();
    }

    public void automaticRound(Rectangle rect){
        if (rect.width > 3)
            this.y = this.prevy;
        if (rect.height > 3)
            this.x = this.prevx;
    }

    public void paintComponent(Graphics g, ImageList imageList){
        g.drawImage(imageList.getImages("player").get(0), (int)this.x, (int)this.y, null);
    }
}
