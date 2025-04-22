package gameManagement;

import eventPkg.Event;
import eventPkg.EventHandler;
import mainPkg.Game;

public class ObjectEventHandler implements Event{
    public ObjectEventHandler() {
    }

    public void ManageEvent(Game game, Integer event) {
        if (game.map.name == 3 && event == 20)
            EventHandler.addEvent(this);
    }

    @Override
    public void eventTriggered(Game game) {
        game.dialogue.refresh(1000);
    }
}
