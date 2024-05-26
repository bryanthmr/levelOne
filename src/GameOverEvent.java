import javafx.event.Event;
import javafx.event.EventType;

public class GameOverEvent extends Event {
    public static final EventType<GameOverEvent> GAME_OVER = new EventType<>(Event.ANY, "GAME_OVER");

    public boolean isFinitoPipo() {
        return finitoPipo;
    }

    public void setFinitoPipo(boolean finitoPipo) {
        this.finitoPipo = finitoPipo;
    }

    private boolean finitoPipo;
    public GameOverEvent(boolean finitoPipo) {
        super(GAME_OVER);
        this.finitoPipo = finitoPipo;
    }
}
