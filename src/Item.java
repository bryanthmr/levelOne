import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Item {

    private String itemName;
    private String itemDescription;
    private Effet effet;
    private int quantity;
    private ImageView itemImage;

    public Item(String itemName, String itemDescription, Effet effet, int quantity, ImageView itemImage) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.effet = effet;
        this.quantity=quantity;
        this.itemImage=itemImage;
        this.itemImage.setFitWidth(50);
        this.itemImage.setFitHeight(50);
    }


    public void useItem(Monster m) {
        switch (effet) {
            case PVPLUS:
                m.setPv(m.getPv()+(int)(m.getMaxPv()*0.3));
                Main.player.myPvProperty.set((double)m.getPv()/m.getMaxPv()*163);
                break;
            case SUPERPVPLUS:
                m.setPv(m.getMaxPv());
                Main.player.myPvProperty.set((double)m.getPv()/m.getMaxPv()*163);
                break;
            case XPPLUS:
                m.setXp((int)(m.getXp()*1.3));
                break;
            case COCAINED:
                m.getSpriteFront().setImage(new Image("img/pokemon/passajotabloCocained2.png"));
                m.getSpriteBack().setImage(new Image("img/pokemon/passajotabloCocained.png"));
                m.realLv=m.getNiveau();
                m.setNiveau(999);
                Main.player.myLevelProperty.set(""+999);

                break;
            case UNCOCAINED:
                m.getSpriteFront().setImage(new Image("img/pokemon/passajotabloUncocained2.png"));
                m.getSpriteBack().setImage(new Image("img/pokemon/passajotabloUncocained.png"));
                m.setNiveau(m.realLv);
                Main.player.myLevelProperty.set(""+m.getNiveau());
                break;
        }
        this.setQuantity(this.getQuantity()-1);

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if(quantity>=0) {
            this.quantity = quantity;
        }

    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Effet getEffet() {
        return effet;
    }

    public void setEffet(Effet effet) {
        this.effet = effet;
    }


    public ImageView getItemImage() {
        return itemImage;
    }
}
