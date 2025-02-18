package itemPkg;

import mainPkg.Defines;
import mainPkg.JApp;
import java.awt.Graphics;
import java.awt.Point;

import graphicsPkg.ImageList;

public class Candle extends Item {
    private int candleCount;
    private boolean lit;

    public Candle() {
        super(100);

        this.candleCount = 0;
        this.lit = false;
        this.ID = "Item@Candle";
    }
    
    public void addCandle(){
        if (candleCount > 3)
            return;

        this.candleCount++;
    }

    public void light(){
        if (this.candleCount != 3)
            return;

        this.lit = true;
    }
    
    public void update(JApp app){
        if (!this.lit)
            return;
        
        app.map.lit = true;
    }

    public void paintComponent(Graphics g, Point pos, ImageList imageList){
        int add = 0;
        if (this.lit) add++;
        
        g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages(""+(this.type + this.candleCount + add), (int)Defines.invItemSize, (int)Defines.invItemSize)), pos.x, pos.y, null);
    }
}
