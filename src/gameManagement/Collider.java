package gameManagement;

import java.awt.Point;
import java.awt.Rectangle;

import GameObjectPkg.Player;
import utilsPkg.RayRectangle;

public class Collider {
    public static void manageCollisions(Map map, Player player){
        Point p;
        RayRectangle playerRect;

        p = Map.getMapPosFromPoint(player.getPoint().x, player.getPoint().y);
        playerRect = player.getRect();


        for (int i = p.x-2; i < p.x+2; i++){
            for (int ii = p.y-2; ii < p.y+2; ii++){
                if (!map.isValidPoint(new Point(i, ii)))
                    continue;
                    
                if (!map.map[i][ii].isCollidable())
                    continue;

                if (!playerRect.collidingWith(map.map[i][ii].getRect()))
                    continue;

                Rectangle collision = new Rectangle();
                Rectangle.intersect(player.getRect().toRect(), map.map[i][ii].getRect().toRect(), collision);
                player.automaticRound(collision);
            }
        }
    }
}
