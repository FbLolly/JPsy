package itemPkg;

import java.awt.Graphics;
import java.awt.Point;

import graphicsPkg.ImageList;
import mainPkg.JApp;

public abstract class Item {
    protected int type;
    public String ID;

    public Item(int type){
        this.type = type;

        this.ID = "Item@Default";
    }

    public void update(JApp app){

    }

    public int activate(JApp app){
        return 0;
    }

    public void paintComponent(Graphics g, Point pos, ImageList imageList){
    }
}
