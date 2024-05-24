import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;




public class BagTransition {

    public static Scene bagScene;

    private static final ImageView backgroundImage=new ImageView("img/bag_background.png");

    private static int sensTransition=0;

    private static Timeline animationTransition;
    private static Timeline animationBagScene;
    
    public static Pane bagPane;
    private static ImageView itemPlusGrand;

    private static int i=0;
    private static double e=0;
    public static Button b_sortir;
    public static Button b_use;
    public static int k;

    private static Label pk_name;
    private static Line pvBarre;

    private static GridPane bagGrid;

    private static Label itemDescription;
    private static Label itemQuantity;
    private static Label itemName;

    private static Label lvPokemon;



    public static void genGridPane() {
        int n = 0;
        int l = 0;
        for (int m = 0; m < Main.player.getInventaire().length; m++) {
            if (Main.player.getInventaire()[m].getQuantity() > 0) {


                if (m % 8 == 0) {
                    n = 0;
                    l++;
                }


                bagGrid.add(Main.player.getInventaire()[m].getItemImage(), n, l);
                Main.player.getInventaire()[m].getItemImage().setOnMouseClicked((e) -> {

                    itemPlusGrand.setImage(((ImageView) e.getTarget()).getImage());

                    for (int j = 0; j < Main.player.getInventaire().length; j++) {
                        if (Main.player.getInventaire()[j].getItemImage().equals((e.getTarget()))) {

                            itemDescription.setText(Main.player.getInventaire()[j].getItemDescription());
                            itemQuantity.setText("Quantité : " + Main.player.getInventaire()[j].getQuantity());
                            itemName.setText(Main.player.getInventaire()[j].getItemName());
                        }
                    }


                });
                n++;
            }
        }
}





    public static void setBagArea(){
        if(e<=1){
            e+=0.1;
            for (int i = 0; i < bagPane.getChildren().size(); i++) {
                bagPane.getChildren().get(i).setOpacity(e);
            }

        }
        else{
            animationBagScene.stop();
            Main.player.setBougeable(false);
            e=0;

        }
    }

    public static void updateSpriteNothing(Stage window){
        if(sensTransition==0) {
                Main.player.getCorps().setOpacity(0);

                BagTransition.animationBagScene.play();
                window.setScene(bagScene);
                BagTransition.animationTransition.stop();


        }
        else {
            if(k==1){
                Main.player.getCorps().setOpacity(1);
                window.setScene(Main.scene);
                BagTransition.animationTransition.stop();
            }
            else{
                Main.player.getCorps().setOpacity(1);
                window.setScene(Main.battleScene);
                BagTransition.animationTransition.stop();
            }
        }

    }

    public static void transitionScene(Stage window,int k) {
        System.out.println("Transition");
        BagTransition.k=k;

        BagTransition.animationTransition = new Timeline(
                new KeyFrame(Duration.millis(5), e -> BagTransition.updateSpriteNothing(window)));


        animationTransition.setCycleCount(Animation.INDEFINITE);
        if (i == 0) {
            sensTransition = 0;
            animationTransition.play();
        }




        bagPane = new Pane();
        bagPane.setPrefSize(Main.worldWidth, Main.worldHeight);
        bagScene = new Scene(bagPane, Main.W_WIDTH, Main.W_HEIGHT);
        bagScene.setFill(Color.BLACK);


        bagPane.getChildren().add(backgroundImage);

        Main.player.getPoke().getSpriteFront().setLayoutX(30);
        Main.player.getPoke().getSpriteFront().setLayoutY(200);
        Main.player.getPoke().getSpriteFront().setFitWidth(200);
        Main.player.getPoke().getSpriteFront().setFitHeight(200);
        bagPane.getChildren().add(Main.player.getPoke().getSpriteFront());

        backgroundImage.setFitWidth(Main.W_WIDTH);
        backgroundImage.setFitHeight(Main.W_HEIGHT);

        pk_name = new Label("Passajotablo");
        pk_name.setLayoutX(50);
        pk_name.setLayoutY(30);
        pk_name.setTextFill(Color.BLACK);
        pk_name.setStyle("-fx-font-size: 30px;");
        bagPane.getChildren().add(pk_name);

        pvBarre = new Line(0, 80, 163, 80);
        pvBarre.setStroke(Color.GREEN);
        pvBarre.setStrokeWidth(10);
        pvBarre.endXProperty().bind(Main.player.myPvProperty);
        pvBarre.setLayoutX(50);
        pvBarre.setLayoutY(1);
        bagPane.getChildren().add(pvBarre);

        lvPokemon = new Label(""+Main.player.getPoke().getNiveau());
        lvPokemon.setLayoutX(220);
        lvPokemon.setLayoutY(120);
        lvPokemon.setTextFill(Color.BLACK);

        lvPokemon.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;");
        lvPokemon.textProperty().bind(Main.player.myLevelProperty);
        bagPane.getChildren().add(lvPokemon);


        b_sortir = new Button("Sortir");
        b_sortir.setLayoutX(650);
        b_sortir.setLayoutY(530);
        b_sortir.getStylesheets().add("css/attack.css");
        b_sortir.setVisible(true);
        b_sortir.setOnMouseClicked(e -> {
            b_sortir.fireEvent(new BagCloseEvent());
        });
        bagPane.getChildren().add(b_sortir);

        itemPlusGrand = new ImageView();
        itemPlusGrand.setFitWidth(100);
        itemPlusGrand.setFitHeight(100);
        itemPlusGrand.setLayoutX(20);
        itemPlusGrand.setLayoutY(490);
        bagPane.getChildren().add(itemPlusGrand);

        b_use = new Button("Utiliser");
        b_use.setLayoutX(650);
        b_use.setLayoutY(460);
        b_use.getStylesheets().add("css/fuite.css");
        b_use.setVisible(true);
        b_use.setOnMouseClicked(e -> {
            for (int u = 0; u < Main.player.getInventaire().length; u++) {

                if (Main.player.getInventaire()[u].getItemImage().getImage()== itemPlusGrand.getImage()) {
                    Main.player.getInventaire()[u].useItem(Main.player.getPoke());
                    itemQuantity.setText("Quantité : " + Main.player.getInventaire()[u].getQuantity());
                    if (Main.player.getInventaire()[u].getQuantity() == 0) {
                        bagGrid.getChildren().remove(Main.player.getInventaire()[u].getItemImage());

                        itemDescription.setText("");
                        itemQuantity.setText("");
                        itemName.setText("");
                        itemPlusGrand.setImage(null);
                    }

                    bagGrid.getChildren().clear();
                    genGridPane();
                }
            }

        }

        );
        bagPane.getChildren().add(b_use);

        bagGrid = new GridPane();
        bagGrid.setLayoutX(315);
        bagGrid.setLayoutY(40);
        bagPane.getChildren().add(bagGrid);



        itemName = new Label();
        itemName.setLayoutX(150);
        itemName.setLayoutY(480);
        itemName.setTextFill(Color.BLACK);
        itemName.setStyle("-fx-font-size: 20px;");
        bagPane.getChildren().add(itemName);

        itemDescription = new Label();
        itemDescription.setLayoutX(150);
        itemDescription.setLayoutY(510);
        itemDescription.setTextFill(Color.BLACK);
        itemDescription.setStyle("-fx-font-size: 15px;");
        bagPane.getChildren().add(itemDescription);

        itemQuantity = new Label();
        itemQuantity.setLayoutX(150);
        itemQuantity.setLayoutY(540);
        itemQuantity.setTextFill(Color.BLACK);
        itemQuantity.setStyle("-fx-font-size: 15px;");
        bagPane.getChildren().add(itemQuantity);


        genGridPane();

        animationBagScene = new Timeline(
                new KeyFrame(Duration.millis(100), e -> setBagArea()));
        animationBagScene.setCycleCount(Animation.INDEFINITE);

        bagScene.addEventHandler(BagCloseEvent.BAG_CLOSE, new EventHandler<BagCloseEvent>() {
            @Override
            public void handle(BagCloseEvent event) {

                sensTransition = 1;
                window.setScene(Main.scene);
                Main.player.getCorps().setOpacity(1);
                Main.player.setBougeable(true);
                animationTransition.play();


            }
        });

    }

    }
