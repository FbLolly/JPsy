package utilsPkg;

import java.awt.Graphics2D;

import entityPkg.GameObject;
import mainPkg.Defines;

public class Camera{
    private double x, y;

    public Camera(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void update(GameObject player){
        RayRectangle p = player.getRect();
        
        this.x = -p.x + Defines.width/2 + p.width/2.0;
        this.y = -p.y + Defines.height/2 + p.width/2.0;
    }

    public void translate(Graphics2D g){
        g.translate(this.x, this.y);
    }
    public void untranslate(Graphics2D g){
        g.translate(-this.x, -this.y);
    }
}
