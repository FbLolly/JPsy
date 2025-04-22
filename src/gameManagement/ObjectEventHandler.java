package gameManagement;

import mainPkg.Game;

public class ObjectEventHandler {
    public ObjectEventHandler() {
    }

    public void ManageEvent(Game game, Integer event) {
        switch (game.map.name) {
            case 3:
                if (event == 20) {
                    game.dialogue.refresh(1000);
                }

                break;
            default:
                break;
        }
    }
}
