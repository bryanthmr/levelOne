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

    //methode qui permet d'utiliser un item et appliquer un effet , potentiel : infini
    public void useItem(Monster m) {

        if(BagTransition.k==0){

            Main.b_bag.fireEvent(new ItemChoiceEvent());
            this.getItemImage().fireEvent(new BagCloseEvent());
        }
        switch (effet) {
            case PVPLUS:
                m.setPv(m.getPv()+(int)(m.getMaxPv()*0.3));
                Main.player.myPvProperty.set((double)m.getPv()/m.getMaxPv()*163);
                this.setQuantity(this.getQuantity()-1);
                break;
            case SUPERPVPLUS:
                m.setPv(m.getMaxPv());
                Main.player.myPvProperty.set((double)m.getPv()/m.getMaxPv()*163);
                this.setQuantity(this.getQuantity()-1);
                break;
            case XPPLUS:
                m.setXp((int)(m.getXp()*1.3));
                this.setQuantity(this.getQuantity()-1);
                break;
            case COCAINED:

                m.getSpriteFront().setImage(new Image("img/pokemon/passajotabloCocained2.png"));
                m.getSpriteBack().setImage(new Image("img/pokemon/passajotabloCocained.png"));
                m.realLv=m.getNiveau();
                m.setNiveau(999);
                Main.player.myLevelProperty.set(""+999);
                this.setQuantity(this.getQuantity()-1);

                break;
            case UNCOCAINED:
                m.getSpriteFront().setImage(new Image("img/pokemon/passajotabloUncocained2.png"));
                m.getSpriteBack().setImage(new Image("img/pokemon/passajotabloUncocained.png"));
                m.setNiveau(m.realLv);
                Main.player.myLevelProperty.set(""+m.getNiveau());
                this.setQuantity(this.getQuantity()-1);

                break;
            case BASTOS:
                double minDistance=0;
                double calcul;
                NPC minNpc=null;
                if(BagTransition.k==1){
                    for(NPC npc : Main.lstNPC){
                        if(npc.isMonster){
                            if(minDistance==0){
                                minDistance=Math.sqrt(Math.pow(npc.getSprite().getLayoutX()-Main.player.getCorps().getLayoutX(),2)+Math.pow(npc.getSprite().getLayoutY()-Main.player.getCorps().getLayoutY(),2));
                                minNpc=npc;
                            }
                            else{
                                calcul= Math.sqrt(Math.pow(npc.getSprite().getLayoutX()-Main.player.getCorps().getLayoutX(),2)+Math.pow(npc.getSprite().getLayoutY()-Main.player.getCorps().getLayoutY(),2));
                                minDistance=Math.min(minDistance,calcul);
                                if(minDistance==calcul){
                                    minNpc=npc;
                                }
                            }



                        }
                    }
                    if(minNpc!=null){
                        Main.mapPane.getChildren().remove(minNpc.getSprite());
                    }
                    this.setQuantity(this.getQuantity()-1);
                }


        }


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
