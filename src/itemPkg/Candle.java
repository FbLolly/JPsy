package itemPkg;

import mainPkg.Defines;
import mainPkg.Game;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;

import graphicsPkg.ImageList;

public class Candle extends Item {
    public Candle() {
        super(100);

        this.ID = "Item@Candle";

        this.attributesInt.put("candleCount", 0);
        this.attributesBool.put("lit", false);
    }
    public Candle(HashMap<String, Integer> ints, HashMap<String, Boolean> bools) {
        super(100);

        this.ID = "Item@Candle";

        this.attributesInt = ints;
        this.attributesBool = bools;
    }

    public int getCandleCount(){
        return this.attributesInt.get("candleCount");
    }
    public void setCandleCount(int candleCount){
        this.attributesInt.put("candleCount", candleCount);
    }
    
    public void addCandle(){
        if (this.attributesInt.get("candleCount") > 3)
            return;

        this.attributesInt.put("candleCount", this.attributesInt.get("candleCount")+1);
    }

    public void light(){
        if (this.attributesInt.get("candleCount") != 3)
            return;

        this.attributesBool.put("lit", true);
    }
    
    public void update(Game game){
        if (!this.attributesBool.get("lit"))
            return;
        
        game.map.lit = true;
    }

    public void paintComponent(Graphics g, Point pos, ImageList imageList){
        int add = 0;
        if (this.attributesBool.get("lit")) add++;
        
        g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages(""+(this.type + this.attributesInt.get("candleCount") + add), (int)Defines.invItemSize, (int)Defines.invItemSize)), pos.x, pos.y, null);
    }
}
