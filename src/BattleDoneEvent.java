import javafx.event.Event;
import javafx.event.EventType;

public class BattleDoneEvent extends Event {
    public static final EventType<BattleDoneEvent> BATTLE_DONE = new EventType<>(Event.ANY, "BATTLE_DONE");

    public BattleDoneEvent() {
        super(BATTLE_DONE);

    }
}
