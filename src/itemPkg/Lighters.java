package itemPkg;

import java.awt.Graphics;
import java.awt.Point;

import graphicsPkg.ImageList;
import mainPkg.Game;

public class Lighters extends Item {
    private int durability;

    public Lighters() {
        super(105);

        this.durability = 20;
        this.ID = "Item@Lighters";
    }

    public void update(Game game){

    }

    public int activate(Game game){
        this.durability--;

        if (this.durability <= 0)
            return -1; //Death signal

        if (Math.random()*100 > 50){
            return 1;
        }

        return 0;
    }

    public void paintComponent(Graphics g, Point pos, ImageList imageList){
        DurabilityItem.paint(g, pos, type, durability, imageList);
    }
}
