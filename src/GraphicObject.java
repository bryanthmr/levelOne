import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

//classe qui devait faire beaucoup de choses mais j'ai opté pour une mauvaise architecture
public class GraphicObject extends ImageView {


    private Rectangle2D colliderBox;

    public GraphicObject(String spritePath){
        super(spritePath);

        colliderBox=new Rectangle2D(this.getLayoutX()+this.getTranslateX(),this.getLayoutY()+this.getTranslateY(),this.getFitWidth(),this.getFitHeight());


    }


    public Rectangle2D getColliderBox() {
        return colliderBox;
    }

    public void setColliderBox(Rectangle2D colliderBox) {
        this.colliderBox = colliderBox;
    }
}
