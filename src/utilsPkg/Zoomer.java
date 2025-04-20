package utilsPkg;

import mainPkg.Defines;
import mainPkg.Game;

public class Zoomer {
    private static double action = 0;
    
    public static void Zoom(double objective){
        action = (objective - Defines.zoom)/64;
    }

    public static void update(Game game){
        Defines.zoom += action;

        if (action < 0.1)
            return;

        Defines.font = Defines.font.deriveFont((float)(Defines.fontSize/Defines.zoom));
        game.app.setFont(Defines.font);
        game.dialogue.refresh(game.dialogue.currentNum);
    }
}
