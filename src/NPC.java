import javafx.scene.image.ImageView;

public class NPC {

    private String name;
    private String[] dialogue;
    private ImageView sprite;
    private Effet effet;
    private Item[] Inventaire;
    private boolean isTrainer;
    private int[] haveResponse;
    private String[] response;

    public NPC(String name, String[] dialogue, ImageView sprite, Effet effet, Item[] inventaire, int[] haveResponse, boolean isTrainer, String[] response) {
        this.name = name;
        this.dialogue = dialogue;
        this.sprite = sprite;
        this.effet = effet;
        Inventaire = inventaire;
        this.haveResponse = haveResponse;
        this.isTrainer = isTrainer;
        this.response = response;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getDialogue() {
        return dialogue;
    }

    public void setDialogue(String[] dialogue) {
        this.dialogue = dialogue;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public void setSprite(ImageView sprite) {
        this.sprite = sprite;
    }

    public Effet getEffet() {
        return effet;
    }

    public void setEffet(Effet effet) {
        this.effet = effet;
    }

    public int[] getHaveResponse() {
        return haveResponse;
    }

    public void setHaveResponse(int[] haveResponse) {
        this.haveResponse = haveResponse;
    }

    public Item[] getInventaire() {
        return Inventaire;
    }

    public void setInventaire(Item[] inventaire) {
        Inventaire = inventaire;
    }

    public String[] getResponse() {
        return response;
    }

    public void setResponse(String[] response) {
        this.response = response;
    }
}
