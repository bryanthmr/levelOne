import javafx.event.Event;
import javafx.event.EventType;


//Event for when the player chooses to attack

public class AttackChoiceEvent extends Event {
    public static final EventType<AttackChoiceEvent> ATTACK = new EventType<>(Event.ANY, "ATTACK");
    public final int choice;
    public AttackChoiceEvent(int choice) {
        super(ATTACK);
        this.choice = choice;
    }
}
