import javafx.scene.image.ImageView;

public class NPC {

    private String name;
    private String[] dialogue;
    private ImageView sprite;
    private Effet effet;
    private Item[] Inventaire;
    private int[] haveResponse;
    private String[] response;

    public NPC(String name, String[] dialogue, ImageView sprite, Effet effet, Item[] Inventaire) {
        this.name = name;
        this.dialogue = dialogue;
        this.sprite = sprite;
        this.effet = effet;
        this.Inventaire = Inventaire;
    }

}
