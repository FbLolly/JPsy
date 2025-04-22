package itemPkg;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;

import graphicsPkg.ImageList;
import mainPkg.Game;

public class Item {
    public int type;
    public String ID;
    public HashMap<String, Integer> attributesInt;
    public HashMap<String, Boolean> attributesBool;

    public Item(int type) {
        this.type = type;

        this.ID = "Item@Default";
        attributesInt = new HashMap<>();
        attributesBool = new HashMap<>();
    }

    public Item(int type, HashMap<String, Integer> ints, HashMap<String, Boolean> bools) {
        this.type = type;

        this.ID = "Item@Default";
        attributesInt = ints;
        attributesBool = bools;
    }

    public void update(Game game) {

    }

    public int activate(Game game) {
        return 0;
    }

    public void paintComponent(Graphics g, Point pos, ImageList imageList) {
    }
}
