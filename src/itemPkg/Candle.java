package itemPkg;

import mainPkg.Defines;
import mainPkg.Game;
import java.awt.Graphics;
import java.awt.Point;

import graphicsPkg.ImageList;

public class Candle extends Item {
    public Candle() {
        super(100);

        this.ID = "Item@Candle";

        this.attributes.put("candleCount", 0);
        this.attributes.put("lit", false);
    }

    public int getCandleCount(){
        return (Integer)this.attributes.get("candleCount");
    }
    public void setCandleCount(int candleCount){
        this.attributes.put("candleCount", candleCount);
    }
    
    public void addCandle(){
        if ((Integer)this.attributes.get("candleCount") > 3)
            return;

        this.attributes.put("candleCount", (Integer)(this.attributes.get("candleCount"))+1);
    }

    public void light(){
        if ((Integer)this.attributes.get("candleCount") != 3)
            return;

        this.attributes.put("lit", true);
    }
    
    public void update(Game game){
        if (!(Boolean)this.attributes.get("lit"))
            return;
        
        game.map.lit = true;
    }

    public void paintComponent(Graphics g, Point pos, ImageList imageList){
        int add = 0;
        if ((Boolean)this.attributes.get("lit")) add++;
        
        g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages(""+(this.type + (Integer)this.attributes.get("candleCount") + add), (int)Defines.invItemSize, (int)Defines.invItemSize)), pos.x, pos.y, null);
    }
}
