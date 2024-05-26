import javafx.scene.image.ImageView;

import java.util.ArrayList;

//classe NPC mais en vrai ça peut tout faire


public class NPC {

    private String name;
    private String[] dialogue;
    private ImageView sprite;
    private Effet effet;
    private ArrayList<Item> Inventaire;
    private boolean isTrainer;
    private int[] haveResponse;
    private String[] response;

    public boolean isMonster() {
        return isMonster;
    }

    public void setMonster(boolean monster) {
        isMonster = monster;
    }

    public boolean isMonster;

    public boolean isInDialog() {
        return inDialog;
    }

    public void setInDialog(boolean inDialog) {
        this.inDialog = inDialog;
    }

    public boolean isTrainer() {
        return isTrainer;
    }

    public void setTrainer(boolean trainer) {
        isTrainer = trainer;
    }

    private boolean inDialog;

    public NPC(String name, String[] dialogue, boolean isMonster, ImageView sprite, Effet effet, ArrayList<Item> inventaire, int[] haveResponse, boolean isTrainer, String[] response) {
        this.name = name;
        this.dialogue = dialogue;
        this.sprite = sprite;
        this.effet = effet;
        Inventaire = inventaire;
        this.haveResponse = haveResponse;
        this.isTrainer = isTrainer;
        this.response = response;
        this.inDialog=false;
        this.isMonster=isMonster;
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

    public ArrayList<Item> getInventaire() {
        return Inventaire;
    }

    public void setInventaire(ArrayList<Item> inventaire) {
        Inventaire = inventaire;
    }

    public String[] getResponse() {
        return response;
    }

    public void setResponse(String[] response) {
        this.response = response;
    }
}
