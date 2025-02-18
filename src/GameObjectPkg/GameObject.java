package GameObjectPkg;

import java.awt.Graphics;

import graphicsPkg.ImageList;
import mainPkg.Defines;
import utilsPkg.RayPoint;
import utilsPkg.RayRectangle;

public abstract class GameObject {
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
        if (this.x < -Defines.cameraX - Defines.tileSize || this.y < -Defines.cameraY - Defines.tileSize)
            return true;
        if (this.x > -Defines.cameraX + Defines.width || this.y > -Defines.cameraY + Defines.height)
            return true;

        return false;
    }

    public void paintComponent(Graphics g, ImageList imageList){
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
