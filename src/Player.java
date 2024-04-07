import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Player extends GraphicObject{

    protected Timeline walkingAnimationRight;
    protected Timeline walkingAnimationLeft;
    private static final int WALKING_ANIMATION_DURATION=180;

    private int currentFrame = 0;
    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }


    public Player(Zone zone){

        Image image=null;
        try {
            image= new Image(new FileInputStream("../img/joueur.png"));

        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        corps=new ImageView(image);
        ((ImageView) corps).setViewport(new Rectangle2D(0, 0, 25, 30));
        ((ImageView) corps).setX(0);
        ((ImageView) corps).setY(0);
        ((ImageView) corps).setFitWidth(50);
        ((ImageView) corps).setFitHeight(50);

        walkingAnimationRight = new Timeline(
                new KeyFrame(Duration.millis(WALKING_ANIMATION_DURATION), e -> walkRight()));
        walkingAnimationRight.setCycleCount(Animation.INDEFINITE);

        walkingAnimationLeft = new Timeline(
                new KeyFrame(Duration.millis(WALKING_ANIMATION_DURATION), e -> walkLeft()));
        walkingAnimationLeft.setCycleCount(Animation.INDEFINITE);

    }

    public void updateSpriteRight(){
        switch (this.getCurrentFrame()){
            case 0:
                ((ImageView) corps).setViewport(new Rectangle2D(109.7, 0, 17, 30));
                break;
            case 1:
                ((ImageView) corps).setViewport(new Rectangle2D(126.6, 0, 17, 30));
                break;
            case 2:
                ((ImageView) corps).setViewport(new Rectangle2D(146.6, 0, 17, 30));
                this.setCurrentFrame(0);
                break;

        }
    }
    public void updateSpriteLeft(){
        switch (this.getCurrentFrame()){
            case 0:
                ((ImageView) corps).setViewport(new Rectangle2D(153.7, 0, 17, 30));
                break;
            case 1:
                ((ImageView) corps).setViewport(new Rectangle2D(126.6, 0, 17, 30));
                break;
            case 2:
                ((ImageView) corps).setViewport(new Rectangle2D(146.6, 0, 17, 30));
                this.setCurrentFrame(0);
                break;

        }
    }

    public void walkRight(){

        ((ImageView) corps).setX(((ImageView) corps).getX()+20);
        //((ImageView) corps).setY(((ImageView) corps).getY()+dy);
        currentFrame++;
        updateSpriteRight();
    }

    public void walkLeft(){
        ((ImageView) corps).setX(((ImageView) corps).getX()-20);
        currentFrame++;
        updateSpriteLeft();
    }

}
