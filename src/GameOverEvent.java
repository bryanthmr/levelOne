import javafx.event.Event;
import javafx.event.EventType;

public class GameOverEvent extends Event {
    public static final EventType<GameOverEvent> GAME_OVER = new EventType<>(Event.ANY, "GAME_OVER");

    public boolean isVictory() {
        return victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    private boolean victory;
    public GameOverEvent(boolean victory) {
        super(GAME_OVER);
        this.victory = victory;
    }
}
