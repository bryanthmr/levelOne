import javafx.event.Event;
import javafx.event.EventType;

public class ItemChoiceEvent extends Event {
    public static final EventType<ItemChoiceEvent> ITEM_CHOICE = new EventType<>(Event.ANY, "ITEM_CHOICE");



    public ItemChoiceEvent() {
        super(ITEM_CHOICE);

    }



}
