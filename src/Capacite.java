import java.lang.reflect.Array;


public class Capacite {

    private String nom;
    private int degat;
    private Effet effet;

    private int[] limit;

    private Type type;

    public Capacite(String nom,int degat,Effet effet,int[] limit, Type type) {
        this.nom=nom;
        this.degat = degat;
        this.effet = effet;
        this.limit = limit;
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public int getDegat() {
        return degat;
    }

    public void setDegat(int degat) {
        this.degat = degat;
    }



    public int getLimit(int i) {
        if (i == 0) {
            return limit[0];
        } else if (i == 1) {
            return limit[1];
        } else {
            return 0;
        }
    }

    public void setLimit(int limit) {
        if(limit < 0){
            this.limit[0] = 0;
        }
        if(limit > this.limit[1]){
             this.limit[0]=this.limit[1];
        }
        else{
            this.limit[0] = limit;
        }

    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Effet getEffet() {
        return effet;
    }

    public void setEffet(Effet effet) {
        this.effet = effet;
    }
}
