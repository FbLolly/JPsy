package GameObjectPkg;

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

    public void updateWithKeys(){
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

    public void sprintListen(){
        if (this.map.get("SPACE") == 1){
            this.setSpeedX(this.getSpeedX() * 1.5);
            this.setSpeedY(this.getSpeedY() * 1.5);
        }else{
            if (this.getSpeedX() > Defines.defaultSpeed || this.getSpeedX() < -Defines.defaultSpeed)
                this.setSpeedX(this.getSpeedX()/2);
            if (this.getSpeedY() > Defines.defaultSpeed || this.getSpeedY() < -Defines.defaultSpeed)
                this.setSpeedY(this.getSpeedY()/2);
        }
    }

    public void updatePos(){
        this.setSpeedX(0);
        this.setSpeedY(0);

        this.updateWithKeys();

        this.sprintListen();
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
        g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages("player")), (int)this.x, (int)this.y, null);
    }
}
