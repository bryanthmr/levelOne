import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Duration;
import javafx.scene.shape.Line;

import java.util.*;

import static javafx.scene.input.KeyCode.*;


public class Main extends Application{



    public static final double W_WIDTH=810;
    public static final double W_HEIGHT=630;

    private int compteur=0;
    //private ImageView overlaySnow;
    public static Player player;

    private final double characterX = W_WIDTH / 2 - 50;
    private final double characterY = W_HEIGHT / 2 - 50;

    public static World world;
    public static final int worldWidth=5000;
    public static final int worldHeight=8000;



    public static Pane mapPane;
    private int i=0;
    private double e=0;
    public static int sensTransition=0;

    public static Timeline animationTransition;

    public static Scene scene;
    public static Scene battleScene;
    private World battleWorld;
    public static Pane OverlayPane;
    private Timeline animationBattleScene;

    public static Button b_attack;
    public static Button b_bag;
    public static Button b_fuite;

    public static Button b_attack1;
    public static Button b_attack2;
    public static Button b_attack3;
    public static Button b_attack4;
    public static Button b_retour;

    public static Line pvBarre;
    public static Line pvBarre2;
    public static ImageView battleDialog;
    public static Label battleLabel;

    public static NPC[] lstNPC;
    public static NPC dealer;
    public static NPC monstre1;
    public static NPC monstre2;


    public static void main(String[] args){
        launch(args);
    }


    public void setBattleArea(){
        if(e<=1){
            e+=0.1;
            for (int i = 0; i < OverlayPane.getChildren().size(); i++) {
                OverlayPane.getChildren().get(i).setOpacity(e);
            }

        }
        else{
            animationBattleScene.stop();
            e=0;

        }
    }

    public void updateSpriteNothing(Stage window){
        if(sensTransition==0) {
            if (i < world.getChildren().size()) {


                world.getChildren().get(i).setOpacity(0);

                i++;
            } else {
                player.getCorps().setOpacity(0);
                i = 0;
                animationBattleScene.play();
                window.setScene(battleScene);
                animationTransition.stop();

            }
        }
        else {
            if (i < world.getChildren().size()) {

                //((ImageView)world.getChildren().get(i)).setImage(nothing);
                world.getChildren().get(i).setOpacity(1);

                i++;
            } else {

                i = 0;
                animationTransition.stop();
                player.setBougeable(true);

            }
        }

    }

    public void transitionScene(Stage window,Monster m){
        System.out.println("Transition");


        animationTransition= new Timeline(
                new KeyFrame(Duration.millis(5), e -> updateSpriteNothing(window)));


        animationTransition.setCycleCount(Animation.INDEFINITE);
        if(i==0){
            sensTransition=0;
            animationTransition.play();
        }


        battleWorld= new World("csv/battleWorld.csv","");
        battleWorld.setPrefSize(worldWidth, worldHeight);
        OverlayPane= new Pane(battleWorld);
        OverlayPane.setPrefSize(worldWidth, worldHeight);
        battleScene= new Scene(OverlayPane, W_WIDTH, W_HEIGHT);
        battleScene.setFill(Color.BLACK);



        ImageView battleAreaGrass=new ImageView("img/battleAreaGrass.png");
        battleAreaGrass.setFitWidth(W_WIDTH);
        battleAreaGrass.setFitHeight(W_HEIGHT);
        OverlayPane.getChildren().add(battleAreaGrass);
        for (int i = 0; i < OverlayPane.getChildren().size(); i++) {
            OverlayPane.getChildren().get(i).setOpacity(0);
        }
        //ImageView passajotablo = new ImageView("img/pokemon/Passajotablo.png");
        player.getPoke().getSpriteBack().setLayoutX(150);
        player.getPoke().getSpriteBack().setLayoutY(400);
        player.getPoke().getSpriteBack().setFitWidth(200);
        player.getPoke().getSpriteBack().setFitHeight(200);
        OverlayPane.getChildren().add(player.getPoke().getSpriteBack());

        //ImageView adversaire = new ImageView(m.getSpriteFront().getImage());
        m.getSpriteFront().setLayoutX(480);
        m.getSpriteFront().setLayoutY(200);
        m.getSpriteFront().setFitWidth(200);
        m.getSpriteFront().setFitHeight(200);
        OverlayPane.getChildren().add(m.getSpriteFront());

        ImageView myBarrePv = new ImageView("img/barrePV.png");
        myBarrePv.setLayoutX(30);
        myBarrePv.setLayoutY(80);
        myBarrePv.setFitWidth(300);
        myBarrePv.setFitHeight(50);
        OverlayPane.getChildren().add(myBarrePv);

        ImageView vsBarrePv = new ImageView("img/barrePV.png");
        vsBarrePv.setLayoutX(30);
        vsBarrePv.setLayoutY(300);
        vsBarrePv.setFitWidth(300);
        vsBarrePv.setFitHeight(50);
        OverlayPane.getChildren().add(vsBarrePv);





        pvBarre = new Line(0, 80, 163, 80);
        pvBarre.setStroke(Color.GREEN);
        pvBarre.setStrokeWidth(10);
        pvBarre.endXProperty().bind(player.myPvProperty);
        OverlayPane.getChildren().add(pvBarre);

        pvBarre2 = new Line(0, 80, 163, 80);
        pvBarre2.setStroke(Color.GREEN);
        pvBarre2.setStrokeWidth(10);
        pvBarre2.endXProperty().bind(player.pvAdversaireProperty);
        OverlayPane.getChildren().add(pvBarre2);

        pvBarre2.setLayoutX(129);
        pvBarre2.setLayoutY(33);
        pvBarre.setLayoutX(129);
        pvBarre.setLayoutY(253);

        Label myName = new Label(player.getPoke().getNom());
        myName.setTextFill(Color.BLACK);
        myName.setLayoutX(60);
        myName.setLayoutY(290);
        myName.setStyle("-fx-font-size: 20;-fx-font-weight:bold;");
        OverlayPane.getChildren().add(myName);

        Label vsName = new Label(m.getNom());
        vsName.setTextFill(Color.BLACK);
        vsName.setLayoutX(60);
        vsName.setLayoutY(70);
        vsName.setStyle("-fx-font-size: 20;-fx-font-weight:bold;");

        OverlayPane.getChildren().add(vsName);

        Label myLv = new Label(player.getPoke().getNiveau()+"");
        myLv.textProperty().bind(player.myLevelProperty);
        myLv.setTextFill(Color.BLACK);
        myLv.setLayoutX(230);
        myLv.setLayoutY(290);
        myLv.setStyle("-fx-font-size: 20;-fx-font-weight:bold;");

        OverlayPane.getChildren().add(myLv);

        Label vsLv = new Label(m.getNiveau()+"");
        vsLv.setTextFill(Color.BLACK);
        vsLv.setLayoutX(230);
        vsLv.setLayoutY(70);
        vsLv.setStyle("-fx-font-size: 20;-fx-font-weight:bold;");

        OverlayPane.getChildren().add(vsLv);

        b_attack = new Button("Attaquer");
        b_attack.setLayoutX(600);
        b_attack.setLayoutY(400);
        b_attack.getStylesheets().add("css/attack.css");

        b_attack.setOnMouseClicked(event -> {
            b_attack.setVisible(false);
            b_bag.setVisible(false);
            b_fuite.setVisible(false);
            b_attack1.setVisible(true);
            b_attack2.setVisible(true);
            b_attack3.setVisible(true);
            b_attack4.setVisible(true);
            b_retour.setVisible(true);
        });
        OverlayPane.getChildren().add(b_attack);

        b_bag = new Button("Sac");
        b_bag.setLayoutX(600);
        b_bag.setLayoutY(460);
        b_bag.getStylesheets().add("css/bag.css");
        b_bag.setOnMouseClicked(event -> {
            BagTransition.transitionScene(window,0);
        });
        OverlayPane.getChildren().add(b_bag);

        b_fuite = new Button("Fuite");
        b_fuite.setLayoutX(600);
        b_fuite.setLayoutY(520);

        b_fuite.setOnMouseClicked(event -> {
            b_fuite.fireEvent(new FuiteEvent());

            Player.timeline.stop();
            sensTransition=1;
            window.setScene(scene);
            player.getCorps().setOpacity(1);
            animationTransition.play();


        });
        b_fuite.getStylesheets().add("css/fuite.css");
        OverlayPane.getChildren().add(b_fuite);

        b_attack1 = new Button(player.getPoke().getCapacite()[0].getNom());
        b_attack1.setLayoutX(600);
        b_attack1.setLayoutY(280);
        b_attack1.getStylesheets().add("css/attack.css");
        b_attack1.setVisible(false);
        b_attack1.setOnMouseClicked(event -> {
            b_attack1.fireEvent(new AttackChoiceEvent(1));
        });
        OverlayPane.getChildren().add(b_attack1);

        b_attack2 = new Button(player.getPoke().getCapacite()[1].getNom());
        b_attack2.setLayoutX(600);
        b_attack2.setLayoutY(340);
        b_attack2.getStylesheets().add("css/attack.css");
        b_attack2.setVisible(false);
        b_attack2.setOnMouseClicked(event -> {
            b_attack2.fireEvent(new AttackChoiceEvent(2));
        });
        OverlayPane.getChildren().add(b_attack2);

        b_attack3= new Button(player.getPoke().getCapacite()[2].getNom());
        b_attack3.setLayoutX(600);
        b_attack3.setLayoutY(400);
        b_attack3.getStylesheets().add("css/attack.css");
        b_attack3.setVisible(false);
        b_attack3.setOnMouseClicked(event -> {
            b_attack3.fireEvent(new AttackChoiceEvent(3));
        });
        OverlayPane.getChildren().add(b_attack3);

        b_attack4 = new Button(player.getPoke().getCapacite()[3].getNom());
        b_attack4.setLayoutX(600);
        b_attack4.setLayoutY(460);
        b_attack4.getStylesheets().add("css/attack.css");
        b_attack4.setVisible(false);
        b_attack4.setOnMouseClicked(event -> {
            b_attack4.fireEvent(new AttackChoiceEvent(4));
        });
        OverlayPane.getChildren().add(b_attack4);

        b_retour = new Button("Retour");
        b_retour.setLayoutX(600);
        b_retour.setLayoutY(520);
        b_retour.getStylesheets().add("css/fuite.css");
        b_retour.setVisible(false);
        b_retour.setOnMouseClicked(event -> {
            b_attack.setVisible(true);
            b_bag.setVisible(true);
            b_fuite.setVisible(true);
            b_attack1.setVisible(false);
            b_attack2.setVisible(false);
            b_attack3.setVisible(false);
            b_attack4.setVisible(false);
            b_retour.setVisible(false);
        });
        OverlayPane.getChildren().add(b_retour);

        battleDialog= new ImageView("img/zone_texte.png");
        battleDialog.setFitWidth(800);
        battleDialog.setFitHeight(150);
        battleDialog.setLayoutX(0);
        battleDialog.setLayoutY(440);
        battleDialog.setVisible(false);
        OverlayPane.getChildren().add(battleDialog);

        battleLabel = new Label("");
        battleLabel.setLayoutX(30);
        battleLabel.setLayoutY(470);
        battleLabel.setVisible(false);
        battleLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 20");
        battleLabel.textProperty().bind(Player.labelProperty);
        OverlayPane.getChildren().add(battleLabel);


        animationBattleScene= new Timeline(
                new KeyFrame(Duration.millis(100), e -> setBattleArea()));
        animationBattleScene.setCycleCount(Animation.INDEFINITE);

        battleScene.addEventHandler(BattleDoneEvent.BATTLE_DONE, new EventHandler<BattleDoneEvent>() {
            @Override
            public void handle(BattleDoneEvent battleDoneEvent) {

                sensTransition=1;
                window.setScene(scene);
                player.getCorps().setOpacity(1);
                animationTransition.play();


            }
        });







    }


    @Override
    public void start(Stage window) throws Exception{



        world = new World("csv/world1.csv","csv/world1Collision.csv");


        mapPane = new Pane(world);
        mapPane.setPrefSize(worldWidth, worldHeight);
        Item[] items= new Item[]{new Item("Potion","normalement ça soigne ton pokémon",Effet.PVPLUS,1,new ImageView("img/items/potion.png")),
                new Item("Bombe Nucléaire","Détruit le monde et arrête le jeu",Effet.PVPLUS,1,new ImageView("img/items/nuclearBomba.png")),
                new Item("J'ai envie de passer en ing2 svp...","Vous rend plus riche que Jeff Bezos",Effet.PVPLUS,1,new ImageView("img/items/GéEnviDePasserEnIng2svp.png")),
                new Item("Cocaïne","Met votre pokémon lv 999",Effet.COCAINED,1,new ImageView("img/items/cocaine.png")),
                new Item("Viagra","Rend votre pokémon bien plus performant",Effet.PVPLUS,1,new ImageView("img/items/viagra.png")),
                new Item("Corde","Au cas où après les partiels...",Effet.PVPLUS,1,new ImageView("img/items/corde.png")),
                new Item("Enfant","Chef ???",Effet.PVPLUS,6,new ImageView("img/items/enfant.png")),
                new Item("4 Mois de deezer premium","5993-9661-1742-2700",Effet.NULL,1,new ImageView("img/items/deezer.png")),
                new Item("Mastercard ****2355","5993 1265 7896 2355 03/26 533",Effet.NULL,1,new ImageView("img/items/mastercard.png")),
                new Item("Bastos","Mort douloureuse et atroce au 1er pokémon qui vient",Effet.BASTOS,1,new ImageView("img/items/bastos.png")),
                new Item("Lunettes de soleil","Annule l'effet de la cocaïne",Effet.UNCOCAINED,1,new ImageView("img/items/lunettesSoleil.png")),};


        player=new Player(900, "Bryan",items,new Monster(new ImageView("img/pokemon/passajotablo.png"),new ImageView("img/pokemon/passajotablo2.png"),1,10,1,1,50,0,new Capacite[]{},"Passajotablo"));



        dealer = new NPC("Le Dit l'heure",new String[]{"...","tu en veux ?","Merci de ton achat !"},false,new ImageView("img/npc/dealer.png"),Effet.DEALER,items,new int[]{0,1,0},false,new String[]{"Oui","Non"});
        dealer.getSprite().setFitWidth(70);
        dealer.getSprite().setFitHeight(70);

        monstre1 = new NPC ("rattata", new String[]{},true,new ImageView("img/pokemon/rattata.png"), Effet.TRAINER, items, new int[]{0,1,0}, true,new String[]{});
        monstre1.getSprite().setFitWidth(100);
        monstre1.getSprite().setFitHeight(100);
        monstre1.getSprite().setTranslateX(1000);
        monstre1.getSprite().setTranslateY(400);

        mapPane.getChildren().add(monstre1.getSprite());

        monstre2 = new NPC ("amogus", new String[]{},true,new ImageView("img/pokemon/amogus.png"), Effet.TRAINER, items, new int[]{0,1,0}, true,new String[]{});
        monstre2.getSprite().setFitWidth(70);
        monstre2.getSprite().setFitHeight(70);
        monstre2.getSprite().setTranslateX(1500);
        monstre2.getSprite().setLayoutY(300);

        lstNPC = new NPC[]{dealer,monstre1,monstre2};

        mapPane.getChildren().add(monstre2.getSprite());

        scene = new Scene(mapPane, worldWidth, worldHeight);
        window.setWidth(W_WIDTH);
        window.setHeight(W_HEIGHT);
        window.setMaxWidth(W_WIDTH);
        window.setMaxHeight(W_HEIGHT);

        window.setTitle("LevelOne");

        window.setScene(scene);



        //overlaySnow = new ImageView("img/neige.gif");
        //overlaySnow.setFitWidth(W_WIDTH);
        //overlaySnow.setFitHeight(W_HEIGHT);
        //overlaySnow.setPreserveRatio(true);

        //mapPane.getChildren().add(overlaySnow);
        //overlaySnow.setLayoutX(850);
        //overlaySnow.setLayoutY(400);

        mapPane.getChildren().add(player.getCorps());
        mapPane.getChildren().add(dealer.getSprite());
        dealer.getSprite().setLayoutX(500);
        dealer.getSprite().setLayoutY(500);
        dealer.getSprite().setTranslateX(1550);
        dealer.getSprite().setTranslateY(-350);


        player.getCorps().setLayoutX(characterX);
        player.getCorps().setLayoutY(characterY);

        player.getCorps().setTranslateX(831);
        player.getCorps().setTranslateY(400);
        mapPane.setTranslateX(-831);
        mapPane.setTranslateY(-400);




        scene.setFill(Color.BLACK);

        scene.addEventHandler(GameOverEvent.GAME_OVER, new EventHandler<GameOverEvent>() {
            @Override
            public void handle(GameOverEvent gameOverEvent) {
                System.out.println("Game Over");
                Platform.runLater(() -> {
                    window.close();
                });

            }
        });

        scene.addEventHandler(BattleEvent.BATTLE, new EventHandler<BattleEvent>() {
            @Override
            public void handle(BattleEvent battleEvent) {
                player.walkingAnimationDown.stop();
                player.walkingAnimationDown2.stop();
                player.walkingAnimationUp.stop();
                player.walkingAnimationUp2.stop();
                player.walkingAnimationLeft.stop();
                player.walkingAnimationLeft2.stop();
                player.walkingAnimationRight.stop();
                player.walkingAnimationRight2.stop();
                transitionScene(window,battleEvent.m);



            }
        });



        scene.setOnKeyPressed(event -> {
            if (Objects.requireNonNull(event.getCode()) == S){
                BagTransition.transitionScene(window,1);
            }

            else if (Objects.requireNonNull(event.getCode()) == RIGHT && player.isBougeable()) {

                player.updateSpriteRight();
                player.walkingAnimationRight2.play();
                player.walkingAnimationRight.play();




            } else if (event.getCode() == LEFT && player.isBougeable()) {
                player.updateSpriteLeft();
                player.walkingAnimationLeft2.play();
                player.walkingAnimationLeft.play();


            } else if (event.getCode() == UP && player.isBougeable()) {

                player.updateSpriteUp();
                player.walkingAnimationUp2.play();
                player.walkingAnimationUp.play();


            } else if ((event.getCode() == DOWN) &&  player.isBougeable()) {

                player.updateSpriteDown();
                player.walkingAnimationDown2.play();
                player.walkingAnimationDown.play();


            }



        });
        scene.setOnKeyReleased(event -> {
            compteur=0;
            if (event.getCode() == RIGHT) {
                player.walkingAnimationRight.stop();
                player.walkingAnimationRight2.stop();
                player.setCurrentFrame(0);
                ((ImageView) player.getCorps()).setViewport(new Rectangle2D(110.5, 0, 17, 30));


            } else if (event.getCode() == LEFT) {
                player.walkingAnimationLeft.stop();
                player.walkingAnimationLeft2.stop();
                player.setCurrentFrame(0);
                player.updateSpriteLeft();
            } else if (event.getCode() == UP) {
                player.walkingAnimationUp.stop();
                player.walkingAnimationUp2.stop();
                player.setCurrentFrame(0);
                player.updateSpriteUp();
            } else if (event.getCode() == DOWN) {
                player.walkingAnimationDown.stop();
                player.walkingAnimationDown2.stop();
                player.setCurrentFrame(0);
                player.updateSpriteDown();
            }

        });



        window.show();


    }




}