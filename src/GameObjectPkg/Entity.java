package GameObjectPkg;

public abstract class Entity extends GameObject {
    private double speedX;
    private double speedY;    
    
    public Entity(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public void update(){
        this.x += this.speedX;
        this.y += this.speedY;
    }

    public double getSpeedX(){
        return this.speedX;
    }
    public double getSpeedY(){
        return this.speedY;
    }

    public void setSpeedX(double speedX){
        this.speedX = speedX;
    }
    public void setSpeedY(double speedY){
        this.speedY = speedY;
    }

    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
}
