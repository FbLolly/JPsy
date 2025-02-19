package GameObjectPkg;

import java.awt.Graphics;

import graphicsPkg.ImageList;
import itemPkg.Candle;
import itemPkg.Lighters;
import mainPkg.Defines;

public class InteractiveObject extends MapObject {
    private int drawType;

    public InteractiveObject(double x, double y, double width, double height, boolean collidable, int type) {
        super(x, y, width, height, collidable, type);

        this.setInteractable(true);
        this.setCollidable(true);

        this.drawType = (int)(Math.random()*4)+10;
    }

    public void interact(Player player){
        if (!this.isInteractable())
            return;

        switch (this.type) {
            case 20:
                player.inv.addToInv(new Candle());
            break;
            case 21:
                if (player.inv.inv[player.inv.selected] == null)
                    return;

                if (!player.inv.inv[player.inv.selected].ID.equals("Item@Candle"))
                    return;
                
                ((Candle)player.inv.inv[player.inv.selected]).addCandle();
            break;
            case 22:
                player.inv.addToInv(new Lighters());
            break;
            default:
            break;
        }

        this.setCollidable(false);
        this.setInteractable(false);
        this.type = this.drawType;
    }

    public void update(){
    }

    public void paintComponent(Graphics g, ImageList imageList){
        if (this.outsideCamera())
            return;
        
        g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages(""+this.drawType, (int)Defines.tileSize, (int)Defines.tileSize)), (int)this.x, (int)this.y, null);
        g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages(""+this.type, (int)Defines.tileSize, (int)Defines.tileSize)), (int)this.x, (int)this.y, null);
    }
}
