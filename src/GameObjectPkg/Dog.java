package GameObjectPkg;
import java.awt.Graphics;

import graphicsPkg.ImageList;
import mainPkg.Defines;

public class Dog extends Entity {
    private Entity follow;

    public Dog(double x, double y, double width, double height, Entity follow) {
        super(x, y, width, height);
        this.follow = follow;
    }

    @Override
    public void update(){
        this.x += follow.getSpeedX() * 0.95;

        if ((int)(Math.random()*3) == 0){
            this.x += follow.getSpeedX();
        }
    }
    
    public void paintComponent(Graphics g, ImageList imageList){
            g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages("1", (int)Defines.tileSize*2, (int)Defines.tileSize*2)),
                    (int)(this.x - Defines.tileSize/2), (int)(this.y - Defines.tileSize), null);
    }
}
