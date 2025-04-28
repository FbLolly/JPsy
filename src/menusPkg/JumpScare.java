package menusPkg;

import java.awt.Graphics;

import mainPkg.Defines;
import mainPkg.Game;

public class JumpScare {
    public static String what = null;
    
    public static void jumpscare(String what){
        JumpScare.what = what;
    }

    public int paintComponent(Graphics g, Game game){
        if (what == null)
            return 0;

        if (Defines.animationTimer >= (Defines.FPS*2)){
            return 1;
        }
        
        g.drawImage(Defines.getCurrentAnimationImage(game.imageList.getImages(what, (int)(Defines.width/Defines.zoom), (int)(Defines.height/Defines.zoom))),
                    (int)(game.player.getX() - Defines.width/2/Defines.zoom),
                    (int)(game.player.getY() - Defines.height/2/Defines.zoom), null);
        return 0;
    }
}
