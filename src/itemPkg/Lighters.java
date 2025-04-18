package itemPkg;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;

import graphicsPkg.ImageList;
import mainPkg.Defines;
import mainPkg.Game;
import particlesPkg.Particles;
import utilsPkg.RayPoint;

public class Lighters extends Item {
    public Lighters() {
        super(105);

        this.ID = "Item@Lighters";

        this.attributesInt.put("durability", 20);
    }
    
    public Lighters(HashMap<String, Integer> ints, HashMap<String, Boolean> bools) {
        super(105);

        this.ID = "Item@Lighters";

        this.attributesInt = ints;
        this.attributesBool = bools;
    }

    public void update(Game game){

    }

    public int activate(Game game){
        this.attributesInt.put("durability", this.attributesInt.get("durability")-1);

        RayPoint playerGraphicalPoint = game.player.getPoint();
        playerGraphicalPoint.x += Defines.tileSize/2;

        if (this.attributesInt.get("durability") <= 0){
            Particles.addParticle(playerGraphicalPoint.toPoint(), new Point(0, 0), "matchFail", 0, 2, 30);

            return -1; //Death signal
        }

        if (Math.random()*100 > 50){
            Particles.addParticle(playerGraphicalPoint.toPoint(), game.player.facing, "fire", 0, 3, 20);

            return 1;
        }
        Particles.addParticle(playerGraphicalPoint.toPoint(), RayPoint.pointToRayPoint(game.player.facing).getInverted().toPoint(), "matchFail", 0, 3, 10);
        
        return 0;
    }

    public void paintComponent(Graphics g, Point pos, ImageList imageList){
        DurabilityItem.paint(g, pos, type, this.attributesInt.get("durability"), imageList);
    }
}
