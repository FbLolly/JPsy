package entityPkg;

public class MapObject extends GameObject {
    private boolean collidable;
    
    public MapObject(double x, double y, double width, double height, boolean collidable) {
        super(x, y, width, height);
        //TODO Auto-generated constructor stub

        this.collidable = collidable;
    }

    public boolean isCollidable(){
        return collidable;
    }
    public void isCollidable(boolean collidable){
        this.collidable = collidable;
    }
}
