package eventPkg;

import java.util.ArrayList;

import mainPkg.Game;

public class EventHandler {
    private static ArrayList<Event> events;

    public EventHandler(){
        events = new ArrayList<>();
    }

    public void update(Game game){
        for (Event e : events){
            e.eventTriggered(game);
        }

        events = new ArrayList<>();
    }

    public static void addEvent(Event event){
        events.add(event);
    }
}
