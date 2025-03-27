package GameObjectPkg;

import java.awt.Graphics;

import graphicsPkg.ImageList;
import mainPkg.Defines;

public class MapObject extends GameObject {
    private boolean collidable;
    public int type;
    private boolean interactable;
    
    public MapObject(double x, double y, double width, double height, boolean collidable, int type) {
        super(x, y, width, height);

        this.collidable = collidable;
        this.type = type;
        this.interactable = false;
    }

    public boolean isCollidable(){
        return collidable;
    }
    public void setCollidable(boolean collidable){
        this.collidable = collidable;
    }

    public void paintComponent(Graphics g, ImageList imageList){
        if (this.outsideCamera())
            return;
        
        if (this.type >= 30 && this.type < 40){
            //just a floor effect

            g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages("10", (int)Defines.tileSize, (int)Defines.tileSize)), (int)this.x, (int)this.y, null);
        }
        g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages(""+this.type, (int)Defines.tileSize, (int)Defines.tileSize)), (int)this.x, (int)this.y, null);
    }

    public boolean isInteractable(){
        return this.interactable;
    }
    protected void setInteractable(boolean interactable){
        this.interactable = interactable;
    }
}
