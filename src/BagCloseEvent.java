import javafx.event.Event;
import javafx.event.EventType;

public class BagCloseEvent extends Event {
    public static final EventType<BagCloseEvent> BAG_CLOSE = new EventType<>(Event.ANY, "BAG_CLOSE");

    public BagCloseEvent() {
        super(BAG_CLOSE);
    }
}
