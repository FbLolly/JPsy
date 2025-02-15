package GameObjectPkg;

import java.awt.Graphics;

import graphicsPkg.ImageList;
import mainPkg.Defines;

public class MapObject extends GameObject {
    private boolean collidable;
    public int type;
    
    public MapObject(double x, double y, double width, double height, boolean collidable, int type) {
        super(x, y, width, height);
        //TODO Auto-generated constructor stub

        this.collidable = collidable;
        this.type = type;
    }

    public boolean isCollidable(){
        return collidable;
    }
    public void isCollidable(boolean collidable){
        this.collidable = collidable;
    }

    public void paintComponent(Graphics g, ImageList imageList){
        if (this.outsideCamera())
            return;
        
        g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages(""+this.type)), (int)this.x, (int)this.y, null);
    }
}
