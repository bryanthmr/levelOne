import javafx.event.Event;
import javafx.event.EventType;
//Event for when the player chooses to attack
public class FuiteEvent extends Event{

    public static final EventType<FuiteEvent> FUITE = new EventType<>(Event.ANY, "FUITE");

    public FuiteEvent() {
        super(FUITE);

    }
}
