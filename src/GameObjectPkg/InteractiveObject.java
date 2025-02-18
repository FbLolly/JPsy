package GameObjectPkg;

public class InteractiveObject extends MapObject {

    public InteractiveObject(double x, double y, double width, double height, boolean collidable, int type) {
        super(x, y, width, height, collidable, type);

        this.setInteractable(true);
    }

    public void interact(){
        this.type = 0;
    }

    public void update(){
    }
}
