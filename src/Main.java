import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;



public class Main extends Application{
    private final double widthWindow=800;
    private final double heightWindow=600;
    private final Pane container = new Pane();

    private final Line line=new Line(0,200,widthWindow,200);

    private final Zone zoneCentral=new Zone(0,0,widthWindow,heightWindow);
    private final Player player=new Player(zoneCentral);





    public static void main(String[] args){
        launch(args);
    }

    private void createContent(){
        container.getChildren().add(line);
        container.getChildren().add(player.getCorps());
    }

    @Override
    public void start(Stage window) throws Exception{
        window.setWidth(widthWindow);
        window.setHeight(heightWindow);
        window.setTitle("LevelOne");
        Scene scene=new Scene(container);
        window.setScene(scene);
        createContent();



        scene.setOnKeyPressed(event -> {

            if (event.getCode() == KeyCode.RIGHT) {
                player.walkingAnimationRight.play();
            }
            else if(event.getCode()==KeyCode.LEFT){
                player.walkingAnimationLeft.play();
            }
        });
        scene.setOnKeyReleased(event -> {
            if(event.getCode()==KeyCode.RIGHT){
                player.walkingAnimationRight.stop();
                player.setCurrentFrame(0);
                player.updateSpriteRight();
            }
            else if(event.getCode()==KeyCode.LEFT){
                player.walkingAnimationLeft.stop();
                player.setCurrentFrame(0);
                player.updateSpriteLeft();
            }

        });


        window.show();
    }




}