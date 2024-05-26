import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class World extends GridPane {

    private String[][] tilesTab;
    private String[][] collisionTab;
    public String tilesFilePath;


    public World(String tilesFilePath, String collisionFilePath) {
        this.tilesFilePath=tilesFilePath;
        this.setTranslateX(this.getTranslateX()+32);
        this.setTranslateY(this.getTranslateY()+50);
        this.tilesTab=readCSV(tilesFilePath);
        if(!Objects.equals(collisionFilePath, "")){
            this.collisionTab=readCSV(collisionFilePath);
        }

        this.genMap();
        this.setHgap(0);
        this.setVgap(0);

    }
//génère la map et les collisions en fonction des fichiers csv
    private void genMap(){
        ImageView tile;
        Image image;
        for(int i=0; i<tilesTab.length; i++){
            for(int j=0; j<tilesTab[i].length; j++){

                switch(tilesTab[i][j]){
                    case "1":
                        image = new Image("img/arbre.png");
                        break;
                    case "2":
                        image = new Image("img/haute_herbe.png");

                        break;
                    case "3":
                        image = new Image("img/herbe_fonce.png");

                        break;
                    case "4":
                        image = new Image("img/herbe_claire.png");

                        break;
                    case "5":
                        image = new Image("img/herbe_claire_bas.png");

                        break;

                    case "6":
                        image = new Image("img/herbe_claire_haut.png");

                        break;

                    case "7":
                        image = new Image("img/herbe_claire_droit.png");

                        break;
                    case "8":
                        image = new Image("img/herbe_claire_gauche.png");

                        break;
                    case "9":
                        image = new Image("img/herbe_claire_angle_hautG.png");
                        break;
                    case "10":
                        image = new Image("img/herbe_claire_angle_hautD.png");

                        break;

                    case "11":
                        image = new Image("img/herbe_claire_angle_basD.png");
                        break;
                    case "12":
                        image = new Image("img/herbe_claire_angle_basG.png");
                        break;
                    default:
                        image = new Image("");
                        break;

                }
                tile = new ImageView(image);
                tile.setFitHeight(100);
                tile.setFitWidth(100);
                this.getChildren().add(tile);
                GridPane.setRowIndex(tile,i);
                GridPane.setColumnIndex(tile,j);
            }
        }
    }

    public static String[][] readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toArray(new String[0][]);
    }

    public String[][] getTilesTab() {
        return this.tilesTab;
    }

    public void setTilesTab(String[] tiles) {
        this.tilesTab = new String[][]{tiles};
    }

    public  String[][] getCollisionTab() {
        return this.collisionTab;
    }

    public void setCollisionTab(String[][] collisions) {
        this.collisionTab = collisions;
    }
}
