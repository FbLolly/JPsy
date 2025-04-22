package GameObjectPkg;

import java.awt.Rectangle;

public abstract class Entity extends GameObject {
    protected double prevx, prevy;
    private double speedX;
    private double speedY;

    public Entity(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public void update() {
        this.prevx = this.x;
        this.prevy = this.y;

        this.x += this.speedX;
        this.y += this.speedY;
    }

    public void automaticRound(Rectangle rect) {
        if (rect.width > 3)
            this.y = this.prevy;
        if (rect.height > 3)
            this.x = this.prevx;
    }

    public double getSpeedX() {
        return this.speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return this.speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
