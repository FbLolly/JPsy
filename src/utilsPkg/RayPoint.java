package utilsPkg;

import java.awt.Point;

public class RayPoint {
    public double x, y;

    public RayPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static RayPoint pointToRayPoint(Point p) {
        return new RayPoint((double) p.x, (double) p.y);
    }

    public Point toPoint() {
        return new Point((int) this.x, (int) this.y);
    }

    public RayPoint getInverted() {
        return new RayPoint(this.x * -1, this.y * -1);
    }
}
