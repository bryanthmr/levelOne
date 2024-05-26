import javafx.event.Event;
import javafx.event.EventType;

//Event for when the battle is done
public class BattleEvent extends Event {
    public static final EventType<BattleEvent> BATTLE = new EventType<>(Event.ANY, "BATTLE");
    public Monster m;
    public BattleEvent(Monster m) {
        super(BATTLE);
        this.m=m;
    }

}
