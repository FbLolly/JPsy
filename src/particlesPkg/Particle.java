package particlesPkg;

import java.awt.Point;

import utilsPkg.RayPoint;

public class Particle {
    protected RayPoint pos;
    protected RayPoint speed;
    protected int transparency;

    public Particle(Point startingPos, Point direction, double minSpeed, double maxSpeed){
        this.pos = new RayPoint((double)startingPos.x, (double)startingPos.y);
        this.speed = new RayPoint(makeX(direction.x, minSpeed, maxSpeed), makeY(direction.y, minSpeed, maxSpeed));

        transparency = 255;
    }

    private double makeX(int x, double min, double max){
        min *= x;
        max *= x;

        if (min > max){
            double temp = min;
            min = max;
            max = temp;
        }

        return (Math.random()*(max-min))+min;
    }
    private double makeY(int y, double min, double max){
        min *= y;
        max *= y;

        if (min > max){
            double temp = min;
            min = max;
            max = temp;
        }

        return (Math.random()*(max-min))+min;
    }

    public void update(){
        pos.x += speed.x;
        pos.y += speed.y;

        if ((int)(Math.random()*10) == 0)
            this.transparency -= 40;

        if ((int)(Math.random()*2) == 0){
            this.speed.x -= this.speed.x/80.0;
            this.speed.y -= this.speed.y/80.0;
        }
    }
}
