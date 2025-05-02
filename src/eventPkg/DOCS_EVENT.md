## Event system documentation

To trigger an event simply call
EventHandler.addEvent(Event e)

passing in your event,
an event is nothing other then an interface
used to specify the triggered action.

## Event Example

public class Player() implements Event{
    public void checkEventAdded(){
        if (this.x <= 0){
            EventHandler.addEvent(this);
        }
    }

    public void eventTriggered(Game game){
        game.dialogue.refresh(1);
    }
}

# this example simply calls the dialogue and refreshes it
# (no syncronization needed) when the player x is lesser then 0