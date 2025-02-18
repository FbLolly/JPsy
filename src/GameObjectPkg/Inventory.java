package GameObjectPkg;

import java.awt.Graphics;
import java.awt.Graphics2D;

import graphicsPkg.ImageList;
import itemPkg.Item;
import mainPkg.Defines;
import utilsPkg.Camera;

public class Inventory {
    protected Item[] inv;

    public Inventory(){
        inv = new Item[Defines.inventorySize];
    }

    public void addToInv(Item item){
        for (int i = 0; i < Defines.inventorySize; i++){
            if (inv[i] == null)
                continue;

            inv[i] = item;
        }
    }

    public void paintComponent(Graphics g, ImageList imageList, Camera cam){
        cam.untranslate((Graphics2D) g);

        int defx = Defines.width - (int)Defines.invTileSize*2;
        int defy = (Defines.height/2) - (int)(Defines.invTileSize*1.5) - (int)Defines.invTileSize;

        for (int i = 0; i < Defines.inventorySize; i++){
            g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages("slot", (int)Defines.invTileSize, (int)Defines.invTileSize)), defx, defy, null);
            defy += Defines.invTileSize + Defines.invTileSize/3.0;
        }

        cam.translate((Graphics2D) g);
    }
}
