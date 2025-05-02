package eventPkg;

import java.util.ArrayList;

import mainPkg.Game;

public class EventHandler implements Runnable{
    private static ArrayList<Event> events;
    private Thread thread;
    private Game game;

    public EventHandler(Game game){
        events = new ArrayList<>();
        this.game = game;

        thread = new Thread(this);
    }

    public void run(){
        while (thread != null){
            for (Event e : events){
                e.eventTriggered(game);
            }

            events.clear();
        }
    }

    public static void addEvent(Event event){
        events.add(event);
    }
}
