import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;


public class Player {
    private Node corps;
    private int pv;
    private String nom;
    //private Tenue tenue;
    public Thread battleThread;
    private ArrayList<Item> inventaire;

    private static int compteurTour=0;
    private Monster poke;

    private int currentFrame = 0;

    private boolean isBougeable;
    private double previousX1;
    private double previousY1;
    private double previousX2;
    private double previousY2;

    public static final int TILE_SIZE=2;
    protected Timeline walkingAnimationRight;
    protected Timeline walkingAnimationLeft;
    protected Timeline walkingAnimationUp;
    protected Timeline walkingAnimationDown;
    protected Timeline walkingAnimationRight2;
    protected Timeline walkingAnimationLeft2;
    protected Timeline walkingAnimationUp2;
    protected Timeline walkingAnimationDown2;
    private static final int WALKING_ANIMATION_DURATION=10;

    private Pane mapPane= Main.mapPane;
    private World world;
    private Capacite capaciteChoisie;

    public DoubleProperty pvAdversaireProperty=new SimpleDoubleProperty(0.0);
    public DoubleProperty myPvProperty=new SimpleDoubleProperty(0.0);
    public StringProperty myLevelProperty=new SimpleStringProperty("0");

    public static Monster m;

    public static Timeline timeline;

    private static int a=0;

    private Capacite playerChoice;
    private Capacite monsterChoice;

    public static StringProperty labelProperty=new SimpleStringProperty("TEST");

    private Rectangle2D colliderBox;


    public Player(int pv, String nom, ArrayList<Item> inventaire, Monster poke){


        this.world=((World)mapPane.getChildren().get(0));
        this.pv=pv;
        this.nom=nom;
        this.inventaire=inventaire;
        this.poke=poke;
        this.isBougeable=true;

        battleThread=new Thread(() -> {
            try {
                this.startBattle(m);
            } catch (InterruptedException e) {
                System.out.println("Fin sale du thread mais tqt ça passe");
            }

        });




        Image image = null;
        try {
            image = new Image(new FileInputStream("img/joueur.png"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        corps = new ImageView(image);
        ((ImageView) corps).setViewport(new Rectangle2D(6, 0, 17, 30));
        ((ImageView) corps).setX(0);
        ((ImageView) corps).setY(0);
        ((ImageView) corps).setFitWidth(70);
        ((ImageView) corps).setFitHeight(70);

        walkingAnimationRight = new Timeline(
                new KeyFrame(Duration.millis(WALKING_ANIMATION_DURATION), e -> moveWorld(-TILE_SIZE,0)));
        walkingAnimationRight.setCycleCount(Animation.INDEFINITE);

        walkingAnimationLeft = new Timeline(
                new KeyFrame(Duration.millis(WALKING_ANIMATION_DURATION), e -> moveWorld(TILE_SIZE,0)));
        walkingAnimationLeft.setCycleCount(Animation.INDEFINITE);

        walkingAnimationUp = new Timeline(
                new KeyFrame(Duration.millis(WALKING_ANIMATION_DURATION), e ->moveWorld(0,TILE_SIZE)));
        walkingAnimationUp.setCycleCount(Animation.INDEFINITE);
        walkingAnimationDown = new Timeline(
                new KeyFrame(Duration.millis(WALKING_ANIMATION_DURATION), e -> moveWorld(0,-TILE_SIZE)));
        walkingAnimationDown.setCycleCount(Animation.INDEFINITE);

        walkingAnimationRight2 = new Timeline(
                new KeyFrame(Duration.millis(WALKING_ANIMATION_DURATION*30), e -> walkRight()));
        walkingAnimationRight2.setCycleCount(Animation.INDEFINITE);

        walkingAnimationLeft2 = new Timeline(
                new KeyFrame(Duration.millis(WALKING_ANIMATION_DURATION*30), e -> walkLeft()));
        walkingAnimationLeft2.setCycleCount(Animation.INDEFINITE);

        walkingAnimationUp2 = new Timeline(
                new KeyFrame(Duration.millis(WALKING_ANIMATION_DURATION*30), e ->walkUp()));
        walkingAnimationUp2.setCycleCount(Animation.INDEFINITE);
        walkingAnimationDown2 = new Timeline(
                new KeyFrame(Duration.millis(WALKING_ANIMATION_DURATION*30), e -> walkDown()));
        walkingAnimationDown2.setCycleCount(Animation.INDEFINITE);

        myPvProperty.set((double)this.getPoke().getPv()/this.getPoke().getMaxPv()*163);
        myLevelProperty.set(this.getPoke().getNiveau()+"");

    }



    private Capacite choixCapacite(){
        capaciteChoisie = null;

        Main.battleScene.addEventHandler(AttackChoiceEvent.ATTACK, event -> {
            capaciteChoisie=this.poke.getCapacite()[event.choice-1];
        });



        return capaciteChoisie;

    }


    private void startTour(Monster monster) throws InterruptedException {
        System.out.printf("-----------------------%d-----------------",compteurTour++);
        playerChoice=null;
        AtomicBoolean itemUsed= new AtomicBoolean(false);
        Main.battleScene.addEventHandler(ItemChoiceEvent.ITEM_CHOICE, event -> {

            itemUsed.set(true);

        });

        while(playerChoice==null && !itemUsed.get()) {
            playerChoice = this.choixCapacite();
            //System.out.println(itemUsed.get());
        }

        monsterChoice=monster.getCapacite()[((new Random()).nextInt(4))];

        if(this.poke.getVitesse()>monster.getVitesse()) {
                if(playerChoice!=null) {
                    this.poke.attack(monster, playerChoice);
                }
                if(monster.getPv()>0){
                    Main.battleDialog.setVisible(true);
                    Main.battleLabel.setVisible(true);
                    Platform.runLater(() -> {
                        if(playerChoice!=null) {
                        labelProperty.set(this.poke.getNom() + " utilise " + playerChoice.getNom());
                    }
                    else{
                        labelProperty.set("Vous avez utilisé un objet");
                    }
                    });

                Main.b_attack1.setVisible(false);
                Main.b_attack2.setVisible(false);
                Main.b_attack3.setVisible(false);
                Main.b_attack4.setVisible(false);
                Main.b_retour.setVisible(false);
                synchronized (battleThread){
                    battleThread.wait(3000);
                }
                Main.battleDialog.setVisible(false);
                Main.battleLabel.setVisible(false);
                pvAdversaireProperty.set(((double)monster.getPv()/monster.getMaxPv())*163);
                myPvProperty.set(((double)this.getPoke().getPv()/this.getPoke().getMaxPv())*163);


                System.out.println(monster.getNom() + " utilise " + monsterChoice.getNom());
                monster.attack(this.poke, monsterChoice);
                Main.battleDialog.setVisible(true);
                Platform.runLater(() -> {
                    labelProperty.set(monster.getNom() + " utilise " + monsterChoice.getNom());
                });

                Main.battleLabel.setVisible(true);
                synchronized (battleThread){
                    battleThread.wait(3000);
                }
                Main.battleDialog.setVisible(false);
                Main.battleLabel.setVisible(false);
                Main.b_attack.setVisible(true);
                Main.b_bag.setVisible(true);
                //Main.b_fuite.setVisible(true);

                pvAdversaireProperty.set(((double)monster.getPv()/monster.getMaxPv())*163);
                myPvProperty.set(((double)this.getPoke().getPv()/this.getPoke().getMaxPv())*163);
            }
        }
        else{

            System.out.println(monster.getNom()+" utilise "+monsterChoice.getNom());
            monster.attack(this.poke,monsterChoice);
            if(this.poke.getPv()>0){

                Main.battleDialog.setVisible(true);
                Main.battleLabel.setVisible(true);
                Platform.runLater(() -> {
                    labelProperty.set(monster.getNom() + " utilise " + monsterChoice.getNom());
                });
                Main.b_attack1.setVisible(false);
                Main.b_attack2.setVisible(false);
                Main.b_attack3.setVisible(false);
                Main.b_attack4.setVisible(false);
                Main.b_retour.setVisible(false);
                synchronized (battleThread){
                    battleThread.wait(3000);
                }
                Main.battleDialog.setVisible(false);
                Main.battleLabel.setVisible(false);
                pvAdversaireProperty.set(((double)monster.getPv()/monster.getMaxPv())*163);
                myPvProperty.set(((double)this.getPoke().getPv()/this.getPoke().getMaxPv())*163);

                if(playerChoice!=null){
                    this.poke.attack(monster,playerChoice);}
                Main.battleDialog.setVisible(true);
                Main.battleLabel.setVisible(true);
                Platform.runLater(() -> {
                    if(playerChoice!=null) {
                        labelProperty.set(this.poke.getNom() + " utilise " + playerChoice.getNom());
                    }
                    else{
                        labelProperty.set("Vous avez utilisé un objet");
                    }
                });
                synchronized (battleThread){
                    battleThread.wait(3000);
                }
                Main.battleDialog.setVisible(false);
                Main.battleLabel.setVisible(false);
                Main.b_attack.setVisible(true);
                Main.b_bag.setVisible(true);
                //Main.b_fuite.setVisible(true);
                pvAdversaireProperty.set(((double)monster.getPv()/monster.getMaxPv())*163);
                myPvProperty.set(((double)this.getPoke().getPv()/this.getPoke().getMaxPv())*163);

            }
        }



    }

    public void startBattle(Monster monster) throws InterruptedException {
        /*
        synchronized (battleThread){
            Main.battleScene.addEventHandler(FuiteEvent.FUITE, new EventHandler<FuiteEvent>() {
                @Override
                public void handle(FuiteEvent e) {

                    m.setPv(0);


                }
            });
        }
*/
        if(battleThread.isInterrupted()){
            return;
        }
        System.out.println(this.poke.getPv());

        while(this.poke.getPv()>0 && monster.getPv()>0){

            this.startTour(monster);
        }

        if(this.poke.getPv()==0){
            System.out.println("Vous avez perdu");
            Main.pvBarre.setVisible(false);


                Main.battleDialog.setVisible(true);
                Main.battleLabel.setVisible(true);
                Platform.runLater(() -> {
                    labelProperty.set("Vous avez perdu, GameOver");
                }
                );
                synchronized (battleThread){
                    battleThread.wait(3000);
                }



        }
        else{
            System.out.println("Bravo vous avez gagné !");
            this.getPoke().setXp(this.getPoke().getXp()+(monster.getNiveau()*10));
            Main.pvBarre2.setVisible(false);
            Main.battleDialog.setVisible(true);
            Main.battleLabel.setVisible(true);
            Platform.runLater(() -> {
                labelProperty.set("Vous avez gagné !");
            });
            synchronized (battleThread){
                battleThread.wait(3000);
            }



        }
        if(m.getNom()=="Amogus" || getPoke().getPv()==0) {

            getCorps().fireEvent(new GameOverEvent(false));

        }

        battleThread.interrupt();











    }

    public void updateSpriteRight() {
        switch (this.getCurrentFrame()) {
            case 0:
                ((ImageView) this.corps).setViewport(new Rectangle2D(110.5, 0, 17, 30));
                break;
            case 1:
                ((ImageView) this.corps).setViewport(new Rectangle2D(127.0, 0, 17, 30));
                break;
            case 2:
                ((ImageView) this.corps).setViewport(new Rectangle2D(147.0, 0, 17, 30));
                this.setCurrentFrame(-1);
                break;

        }
    }

    public void updateSpriteLeft() {
        switch (this.getCurrentFrame()) {
            case 0:
                ((ImageView) this.corps).setViewport(new Rectangle2D(161.5, 0, 17, 30));
                break;
            case 1:
                ((ImageView) this.corps).setViewport(new Rectangle2D(181.5, 0, 17, 30));
                break;
            case 2:
                ((ImageView) this.corps).setViewport(new Rectangle2D(201.6, 0, 17, 30));
                this.setCurrentFrame(-1);
                break;

        }
    }

    public void updateSpriteUp() {
        switch (this.getCurrentFrame()) {
            case 0:
                ((ImageView) this.corps).setViewport(new Rectangle2D(57.5, 0, 17, 30));
                break;
            case 1:
                ((ImageView) this.corps).setViewport(new Rectangle2D(75, 0, 17, 30));
                break;

            case 2:
                ((ImageView) this.corps).setViewport(new Rectangle2D(95, 0, 17, 30));
                this.setCurrentFrame(-1);
                break;

        }
    }

    public void updateSpriteDown() {
        switch (this.getCurrentFrame()) {
            case 0:
                ((ImageView) this.corps).setViewport(new Rectangle2D(6, 0, 17, 30));
                break;
            case 1:
                ((ImageView) this.corps).setViewport(new Rectangle2D(25, 0, 17, 30));
                break;
            case 2:
                ((ImageView) this.corps).setViewport(new Rectangle2D(41, 0, 17, 30));
                this.setCurrentFrame(-1);
                break;

        }
    }
    public void walkRight(){

        currentFrame++;
        updateSpriteRight();
    }

    public void walkLeft(){
        currentFrame++;
        updateSpriteLeft();
    }
    public void walkUp(){

        currentFrame++;
        updateSpriteUp();
    }
    public void walkDown(){


        currentFrame++;
        updateSpriteDown();
    }



//permet le déplacement du monde , du joueur et aussi de démarrer les combats
    public void moveWorld(double dx, double dy) {



        this.getCorps().setTranslateX(this.getCorps().getTranslateX() - dx);
        this.getCorps().setTranslateY(this.getCorps().getTranslateY() - dy);
        mapPane.setTranslateX(mapPane.getTranslateX() + dx);
        mapPane.setTranslateY(mapPane.getTranslateY() + dy);


        for(int i =0;i<world.getChildren().size();i++){

            if(this.getCorps().getBoundsInParent().intersects(world.getChildren().get(i).getBoundsInParent())){

                //System.out.println(world.getCollisionTab()[GridPane.getRowIndex(world.getChildren().get(i))][GridPane.getColumnIndex(world.getChildren().get(i))]);
                //System.out.println(i);
                //System.out.println("En collision avec: "+ i +" en position: "+world.getChildren().get(i).getBoundsInParent().getCenterX()+" "+world.getChildren().get(i).getBoundsInParent().getCenterY());
                if(Objects.equals(world.getCollisionTab()[GridPane.getRowIndex(world.getChildren().get(i))][GridPane.getColumnIndex(world.getChildren().get(i))], "0")){
                    System.out.println("collision0");
                    previousX1=this.getCorps().getTranslateX();
                    previousY1=this.getCorps().getTranslateY();
                    previousX2=mapPane.getTranslateX();
                    previousY2=mapPane.getTranslateY();


                }
                else if(Objects.equals(world.getCollisionTab()[GridPane.getRowIndex(world.getChildren().get(i))][GridPane.getColumnIndex(world.getChildren().get(i))], "2")){
                    System.out.println("collision2");
                    Random hasard= new Random();
                    if (hasard.nextInt(500) == 0) {
                        this.setBougeable(false);

                        int[] limit = new int[2];
                        limit[0] = 10;
                        limit[1] = 10;
                        Capacite[] monsterCapacities = new Capacite[4];
                        monsterCapacities[0] = new Capacite("lance-flamme", 20, Effet.NULL, limit, Type.FEU);
                        monsterCapacities[1] = new Capacite("Hurlement", 0, Effet.DEFENSEMOINS, limit, Type.NORMAL);
                        monsterCapacities[2] = new Capacite("Vive attaque", 20, Effet.NULL, limit, Type.NORMAL);
                        monsterCapacities[3] = new Capacite("charge", 10, Effet.NULL, limit, Type.NORMAL);



                        Capacite[] playerCapacities = new Capacite[4];
                        playerCapacities[0] = new Capacite("Ball-ombre", 100, Effet.NULL, limit, Type.FEU);
                        playerCapacities[1] = new Capacite("Hurlement", 0, Effet.DEFENSEMOINS, limit, Type.NORMAL);
                        playerCapacities[2] = new Capacite("Griffe Ombre", 50, Effet.NULL, limit, Type.NORMAL);
                        playerCapacities[3] = new Capacite("charge", 70, Effet.NULL, limit, Type.NORMAL);

                        this.getPoke().setCapacite(playerCapacities);


                        System.out.println("Battle start !");
                        switch(world.tilesFilePath) {
                            case "csv/world1.csv":
                                if(getPoke().getNiveau()!=999) {
                                    switch (new Random().nextInt(13)) {

                                        case 0:
                                            m = new Monster(new ImageView("img/pokemon/chenipan.png"), 1, 10, 1, 1, (new Random().nextInt(20) + 40), 0, monsterCapacities, "Chenipan");
                                            break;
                                        case 1:
                                            m = new Monster(new ImageView("img/pokemon/rattata.png"), 1, 10, 1, 1, (new Random().nextInt(20) + 40), 0, monsterCapacities, "Rattata");
                                            break;
                                        case 2:
                                            m = new Monster(new ImageView("img/pokemon/roucool.png"), 1, 10, 1, 1, (new Random().nextInt(20) + 40), 0, monsterCapacities, "Roucool");
                                            break;
                                        case 3:
                                            m = new Monster(new ImageView("img/pokemon/teddiursa.png"), 1, 10, 1, 1, (new Random().nextInt(20) + 40), 0, monsterCapacities, "Teddiursa");
                                            break;
                                        case 4:
                                            m = new Monster(new ImageView("img/pokemon/simularbre.png"), 1, 10, 1, 1, (new Random().nextInt(20) + 40), 0, monsterCapacities, "Simularbre");
                                        case 5:
                                            m = new Monster(new ImageView("img/pokemon/wattouat.png"), 1, 10, 1, 1, (new Random().nextInt(20) + 40), 0, monsterCapacities, "Wattouat");
                                            break;
                                        case 6:
                                            m = new Monster(new ImageView("img/pokemon/hoothoot.png"), 1, 10, 1, 1, (new Random().nextInt(20) + 40), 0, monsterCapacities, "Hoothoot");
                                            break;
                                        case 7:
                                            m = new Monster(new ImageView("img/pokemon/vigoroth.png"), 1, 10, 1, 1, (new Random().nextInt(20) + 40), 0, monsterCapacities, "Vigoroth");
                                            break;
                                        case 8:
                                            m = new Monster(new ImageView("img/pokemon/charmillon.png"), 1, 10, 1, 1, (new Random().nextInt(20) + 40), 0, monsterCapacities, "Charmillon");
                                            break;
                                        case 9:
                                            m = new Monster(new ImageView("img/pokemon/zigzaton.png"), 1, 10, 1, 1, (new Random().nextInt(20) + 40), 0, monsterCapacities, "Zigzaton");
                                            break;
                                        case 10:
                                            m = new Monster(new ImageView("img/pokemon/bobléponge.png"), 1, 10, 1, 1, (new Random().nextInt(20) + 40), 0, monsterCapacities, "bob");
                                            break;
                                        case 11:
                                            m = new Monster(new ImageView("img/pokemon/multiplat.png"), 1, 10, 1, 1, (new Random().nextInt(20) + 40), 0, monsterCapacities, "Fiat multiplat");
                                            break;

                                        case 12:
                                            m = new Monster(new ImageView("img/items/enfant.png"), 1, 10, 1, 1, 600, 0, monsterCapacities, "Enfant ?");
                                            break;


                                    }
                                }
                                else{
                                    monsterCapacities[0] = new Capacite("SUS", 20, Effet.NULL, limit, Type.FEU);
                                    monsterCapacities[1] = new Capacite("WHAT IMPOSTER IS SUS ?", 0, Effet.DEFENSEMOINS, limit, Type.NORMAL);
                                    monsterCapacities[2] = new Capacite("tasks done", 20, Effet.NULL, limit, Type.NORMAL);
                                    monsterCapacities[3] = new Capacite("emergency alert", 10, Effet.NULL, limit, Type.NORMAL);
                                    m= new Monster(new ImageView("img/pokemon/amogus.png"),1, 10 , 1,1,999, 0, monsterCapacities, "Amogus");
                                    }


                                break;

                        }

                        myPvProperty.set((double)this.getPoke().getPv()/this.getPoke().getMaxPv()*163);
                        pvAdversaireProperty.set((double)m.getPv()/m.getMaxPv()*163);
                        myLevelProperty.set(this.getPoke().getNiveau()+"");
                        this.getCorps().fireEvent(new BattleEvent(m));

                        if(battleThread.isInterrupted()){
                            battleThread=new Thread(() -> {
                                try {
                                    this.startBattle(m);
                                } catch (InterruptedException e) {
                                        System.out.println("BRYAN DU FUTUR NE TOUCHE PAS / Arrêt du thread codé avec les pieds mais ça passe donc on touche pas");
                                }
                            }

                            );
                            battleThread.start();
                        }
                        else{
                            battleThread.start();
                        }




                        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                            if (battleThread.isInterrupted()) {
                                Main.pvBarre.fireEvent(new BattleDoneEvent());

                                timeline.stop();
                            }
                        }));
                        timeline.setCycleCount(Animation.INDEFINITE);
                        timeline.play();





                    } else {
                        previousX1 = this.getCorps().getTranslateX();
                        previousY1 = this.getCorps().getTranslateY();
                        previousX2 = mapPane.getTranslateX();
                        previousY2 = mapPane.getTranslateY();
                    }

                }
                else if(Objects.equals(world.getCollisionTab()[GridPane.getRowIndex(world.getChildren().get(i))][GridPane.getColumnIndex(world.getChildren().get(i))], "d")){
                    this.getCorps().setTranslateX(previousX1);
                    this.getCorps().setTranslateY(previousY1);
                    mapPane.setTranslateX(previousX2);
                    mapPane.setTranslateY(previousY2);
                    if(!Main.dealer.isInDialog()) {

                        Main.dealer.setInDialog(true);

                        this.setBougeable(false);
                        ImageView dialog = new ImageView("img/zone_texte.png");
                        Main.mapPane.getChildren().add(dialog);
                        dialog.setFitWidth(800);
                        dialog.setFitHeight(150);
                        dialog.setLayoutX(0);
                        dialog.setLayoutY(440);
                        dialog.setTranslateX(previousX1);
                        dialog.setTranslateY(previousY1);


                        Label label = new Label(Main.dealer.getName() + ":\n" + Main.dealer.getDialogue()[0] );
                        label.setTranslateX(previousX1);
                        label.setTranslateY(previousY1);
                        label.setLayoutX(30);
                        label.setLayoutY(470);
                        label.setStyle("-fx-font-weight: bold;-fx-font-size: 20");
                        Main.mapPane.getChildren().add(label);
                        dialog.setOnMouseClicked(e -> {
                            a++;
                            if(Main.dealer.getHaveResponse()[a]==1){
                                label.setText(Main.dealer.getName() + ":\n" + Main.dealer.getDialogue()[1]);
                                Button b_oui = new Button(Main.dealer.getResponse()[0]);
                                Button b_non = new Button(Main.dealer.getResponse()[1]);

                                Main.mapPane.getChildren().add(b_oui);
                                Main.mapPane.getChildren().add(b_non);
                                b_oui.setLayoutX(600);
                                b_oui.setLayoutY(300);
                                b_non.setLayoutX(600);
                                b_non.setLayoutY(380);

                                b_oui.setTranslateX(previousX1);
                                b_oui.setTranslateY(previousY1);
                                b_non.setTranslateX(previousX1);
                                b_non.setTranslateY(previousY1);


                                b_oui.getStylesheets().add("css/fuite.css");
                                b_non.getStylesheets().add("css/attack.css");

                                b_oui.setOnMouseClicked(event -> {
                                    //Main.mapPane.getChildren().remove(dialog);
                                    //Main.mapPane.getChildren().remove(label);
                                    Main.mapPane.getChildren().remove(b_oui);
                                    Main.mapPane.getChildren().remove(b_non);
                                    //setBougeable(true);
                                    //a = 0;
                                    //Main.dealer.setInDialog(false);
                                    boolean found=false;
                                    for(int v=0; v<getInventaire().size();v++){
                                        if(getInventaire().get(v).getItemName()=="Cocaïne"){
                                            getInventaire().get(v).setQuantity(getInventaire().get(v).getQuantity()+1);
                                            found=true;
                                        }
                                    }
                                    if(!found){
                                        getInventaire().add(new Item("Cocaïne","Met votre pokémon lv 999",Effet.COCAINED,1,new ImageView("img/items/cocaine.png")));

                                    }
                                    label.setText("Vous avez reçu de la cocaïne");
                                    dialog.setOnMouseClicked( event2 ->{
                                        Main.mapPane.getChildren().remove(dialog);
                                        Main.mapPane.getChildren().remove(label);
                                        setBougeable(true);
                                        a = 0;
                                        Main.dealer.setInDialog(false);
                                    });

                                });
                                b_non.setOnMouseClicked(event -> {
                                    Main.mapPane.getChildren().remove(dialog);
                                    Main.mapPane.getChildren().remove(label);
                                    Main.mapPane.getChildren().remove(b_oui);
                                    Main.mapPane.getChildren().remove(b_non);
                                    setBougeable(true);
                                    a = 0;
                                    Main.dealer.setInDialog(false);
                                }
                                );

                            }
                            else if (a >= Main.dealer.getDialogue().length) {
                                Main.mapPane.getChildren().remove(dialog);
                                Main.mapPane.getChildren().remove(label);
                                setBougeable(true);
                                a = 0;
                                Main.dealer.setInDialog(false);
                            } else {
                                label.setText(Main.dealer.getName() + ":\n" + Main.dealer.getDialogue()[a]);
                            }


                        });
                    }
                }


                else{
                    System.out.println("Collision "+world.getCollisionTab()[GridPane.getRowIndex(world.getChildren().get(i))][GridPane.getColumnIndex(world.getChildren().get(i))]);
                    this.getCorps().setTranslateX(previousX1);
                    this.getCorps().setTranslateY(previousY1);
                    mapPane.setTranslateX(previousX2);
                    mapPane.setTranslateY(previousY2);


                }



            }


        }




    }

    public void setCorps(Node corps) {

        this.corps = corps;
    }
    public Node getCorps() {
        return corps;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Monster getPoke() {
        return poke;
    }

    public void setPoke(Monster poke) {
        this.poke = poke;
    }

    public ArrayList<Item> getInventaire() {
        return inventaire;
    }

    public void setInventaire(ArrayList<Item> inventaire) {
        this.inventaire = inventaire;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public boolean isBougeable() {
        return isBougeable;
    }

    public void setBougeable(boolean bougeable) {
        isBougeable = bougeable;
    }

}