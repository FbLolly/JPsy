package GameObjectPkg;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import graphicsPkg.ImageList;
import itemPkg.Candle;
import itemPkg.Item;
import mainPkg.Defines;
import mainPkg.Game;
import utilsPkg.Camera;

public class Inventory {
    public Item[] inv;
    public int selected;

    public Inventory(){
        inv = new Item[Defines.inventorySize];

        selected = 0;
    }

    public void update(Game game){
        for (int i = 0; i < Defines.inventorySize; i++){
            if (game.player.inputMap.get((i+1) + "") == 1)
                selected = i;

            if (this.inv[i] == null)
                continue;
            
            this.inv[i].update(game);
        }

        if (this.inv[selected] == null)
            return;

        if (game.keyHandler.KeyMap.get("F") != 1)
            return;
        game.keyHandler.KeyMap.replace("F", 0);

        int ret = this.inv[selected].activate(game);
        System.out.println(ret);
        switch (ret) {
            case 1:
            for (int i = 0; i < Defines.inventorySize; i++){
                if (this.inv[i] == null)
                    continue;
                if (!this.inv[i].ID.equals("Item@Candle"))
                    continue;
                ((Candle) this.inv[i]).light();
            }
            break;
            case -1:
                this.inv[selected] = null;
                System.gc();
            break;
            default:
            break;
        }
    }

    public void addToInv(Item item){
        for (int i = 0; i < Defines.inventorySize; i++){
            if (inv[i] != null)
                continue;

            inv[i] = item;
            return;
        }
    }

    public void paintComponent(Graphics g, ImageList imageList, Camera cam){
        cam.untranslate((Graphics2D) g);

        int defx = Defines.width - (int)Defines.invTileSize*2;
        int defy = (Defines.height/2) - (int)(Defines.invTileSize*1.5) - (int)Defines.invTileSize;

        for (int i = 0; i < Defines.inventorySize; i++){
            double size = 0;
            
            if (this.selected == i){
                size = Defines.selectedInvSize;
                g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages("selectedSlot", (int)Defines.selectedInvSize, (int)Defines.selectedInvSize)),
                                                             (int)(defx + Defines.invTileSize/2.0 - Defines.selectedInvSize/2.0), defy, null);
            }else{
                size = Defines.invTileSize;
                g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages("slot", (int)Defines.invTileSize, (int)Defines.invTileSize)), defx, defy, null);
            }

            if (this.inv[i] != null)
                this.inv[i].paintComponent(g, new Point((int)(defx + Defines.invTileSize/2.0 - Defines.invItemSize/2.0),
                                                        (int)(defy + Defines.invTileSize/2.0 - Defines.invItemSize/2.0)), imageList);
            
            defy += size + size/3.0;
        }

        cam.translate((Graphics2D) g);
    }
}
