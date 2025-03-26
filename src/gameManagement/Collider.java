package gameManagement;

import java.awt.Point;
import java.awt.Rectangle;

import GameObjectPkg.Entity;
import GameObjectPkg.Player;
import utilsPkg.RayRectangle;

public class Collider {
    public static void manageCollisions(Map map, Entity entity){
        Point p;
        RayRectangle playerRect;

        if (entity == null)
            return;

        p = Map.getMapPosFromPoint(entity.getPoint().x, entity.getPoint().y);
        playerRect = entity.getRect();


        for (int i = p.x-2; i < p.x+2; i++){
            for (int ii = p.y-2; ii < p.y+2; ii++){
                if (!map.isValidPoint(new Point(i, ii)))
                    continue;
                    
                if (!map.map[i][ii].isCollidable())
                    continue;

                if (!playerRect.collidingWith(map.map[i][ii].getRect()))
                    continue;

                Rectangle collision = new Rectangle();
                Rectangle.intersect(entity.getRect().toRect(), map.map[i][ii].getRect().toRect(), collision);
                entity.automaticRound(collision);
            }
        }
    }
}
