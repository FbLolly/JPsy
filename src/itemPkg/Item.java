package itemPkg;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;

import graphicsPkg.ImageList;
import mainPkg.Game;

public abstract class Item {
    protected int type;
    public String ID;
    protected HashMap<String, Object> attributes;

    public Item(int type){
        this.type = type;

        this.ID = "Item@Default";
        attributes = new HashMap<>();
    }

    public void update(Game game){

    }

    public int activate(Game game){
        return 0;
    }

    public void paintComponent(Graphics g, Point pos, ImageList imageList){
    }
}
