package utilsPkg;

import java.awt.Rectangle;

public class RayRectangle {
    public double x, y, width, height;

    public RayRectangle(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle toRect(){
        return new Rectangle((int)x, (int)y, (int)width, (int)height);
    }

    public boolean collidingWith(RayRectangle rect){
        return (
            this.x + this.width > rect.x &&
            this.y + this.height > rect.y &&
            rect.x + rect.width > this.x &&
            rect.y + rect.height > this.y
        );
    }
}
