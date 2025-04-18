package itemPkg;

import java.awt.Graphics;
import java.awt.Point;

import graphicsPkg.ImageList;
import mainPkg.Defines;
import mainPkg.Game;
import particlesPkg.Particles;
import utilsPkg.RayPoint;

public class Lighters extends Item {
    public Lighters() {
        super(105);

        this.attributes.put("durability", 20);
        this.ID = "Item@Lighters";
    }

    public void update(Game game){

    }

    public int activate(Game game){
        this.attributes.put("durability", (Integer)this.attributes.get("durability")-1);

        RayPoint playerGraphicalPoint = game.player.getPoint();
        playerGraphicalPoint.x += Defines.tileSize/2;

        if ((Integer)this.attributes.get("durability") <= 0){
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
        DurabilityItem.paint(g, pos, type, (Integer)this.attributes.get("durability"), imageList);
    }
}
