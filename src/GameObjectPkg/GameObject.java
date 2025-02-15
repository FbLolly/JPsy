package GameObjectPkg;

import java.awt.Graphics;

import graphicsPkg.ImageList;
import mainPkg.Defines;
import utilsPkg.RayPoint;
import utilsPkg.RayRectangle;

public class GameObject {
    protected double x, y;
    protected double width, height;

    public GameObject(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update(){

    }

    public boolean outsideCamera(){
        if (this.x < -Defines.cameraX || this.y < -Defines.cameraY)
            return true;
        if (this.x > -Defines.cameraX + Defines.width || this.y > -Defines.cameraY + Defines.height)
            return true;

        return false;
    }

    public void paintComponent(Graphics g, ImageList imageList){
        if (this.outsideCamera())
            return;

        g.drawImage(imageList.getImages("0").get(0), (int)this.x, (int)this.y, null);
    }

    public RayPoint getPoint(){
        return new RayPoint(x, y);
    }

    public RayRectangle getRect(){
        return new RayRectangle(x, y, width, height);
    }

    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
}
