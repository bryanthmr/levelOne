import javafx.scene.image.ImageView;

public class Monster {
    private int force;
    private int pv;
    private int maxPv;
    private int vitesse;
    private int defense;
    private int niveau;
    private int xp;
    private Capacite[] capacite;

    private String nom;

    protected ImageView spriteFront;
    protected ImageView spriteBack;

    public int realLv;

    public Monster(ImageView spriteFront, int force, int pv, int vitesse, int defense, int niveau, int xp, Capacite[] capacite, String nom) {
        this.spriteFront=spriteFront;
        this.force = force;
        this.pv = pv;
        this.maxPv=pv;
        this.vitesse = vitesse;
        this.defense = defense;
        this.niveau = niveau;
        this.xp=xp;
        this.capacite = capacite;
        this.nom = nom;
        this.realLv=niveau;
    }

    public Monster(ImageView spriteBack, ImageView spriteFront,int force, int pv, int vitesse, int defense, int niveau, int xp, Capacite[] capacite, String nom) {
        this.force = force;
        this.pv = pv;
        this.maxPv=pv;
        this.vitesse = vitesse;
        this.defense = defense;
        this.niveau = niveau;
        this.xp=xp;
        this.capacite = capacite;
        this.nom = nom;
        this.spriteBack=spriteBack;
        this.spriteFront=spriteFront;
        this.realLv=niveau;
    }

//fonction pour attaquer un monstre selon une capacité
    public void attack(Monster m,Capacite c){

        switch(c.getEffet()){
            case ATTAQUEMOINS:
                m.setForce((int)(m.getForce()*0.9));
                break;
            case DEFENSEMOINS:
                m.setDefense((int)(m.getDefense()*0.9));
                break;
            case VITESSEMOINS:
                m.setVitesse((int)(m.getVitesse()*0.9));
                break;
            case ATTAQUEPLUS:
                this.setForce((int)(this.getForce()*1.1));
                break;
            case DEFENSEPLUS:
                this.setDefense((int)(this.getDefense()*1.1));
                break;
            case VITESSEPLUS:
                this.setVitesse((int)(this.getVitesse()*1.1));
                break;
            default:
                System.out.println(m.getPv()+" "+m.getDefense()+" "+c.getDegat()+" "+this.getForce());
                int degat= m.getDefense()-c.getDegat()-this.getForce();
                System.out.println(degat);
                System.out.println(m.getPv());
                if(degat<0){
                    m.setPv(m.getPv()+degat);



                }

                System.out.println(m.getPv());
        }

        c.setLimit(c.getLimit(0)-1);


    }



    public int getForce() {
        return force*getNiveau();
    }

    public void setForce(int force) {
        this.force = force/this.getNiveau();
    }

    public int getPv() {
        return pv*getNiveau();
    }

    public void setPv(int pv) {
        if(pv<0){

            this.pv=0;
        }
        else if(pv>getMaxPv()){


            this.pv=maxPv;
        }
        else{
            this.pv=(pv/this.getNiveau());
        }

    }
    public int getMaxPv() {
        return maxPv*getNiveau();
    }

    public void setMaxPv(int maxPv) {
        this.maxPv = maxPv/this.getNiveau();
    }

    public int getVitesse() {
        return vitesse*getNiveau();
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse/this.getNiveau();
    }

    public int getDefense() {
        return defense*getNiveau();
    }

    public void setDefense(int defense) {
        this.defense = defense/this.getNiveau();
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp=xp;
        while (this.getXp() > this.getNiveau() * 100) {
            this.xp=this.getXp() - this.getNiveau() * 100;
            this.setNiveau(this.getNiveau() + 1);
            this.setForce(this.getForce() + this.getNiveau());
            this.setDefense(this.getDefense() + this.getNiveau());
            this.setVitesse(this.getVitesse() + this.getNiveau());
            this.setMaxPv(this.getMaxPv() + (this.getNiveau() * 10));
            this.setPv(this.getMaxPv());
            System.out.println("Félicitations ! "+this.getNom()+" est passé niveau "+this.getNiveau());
        }
    }

    public Capacite[] getCapacite() {
        return capacite;
    }

    public void setCapacite(Capacite[] capacite) {
        this.capacite = capacite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ImageView getSpriteBack() {
        return spriteBack;
    }

    public ImageView getSpriteFront() {
        return spriteFront;
    }

    public void setSpriteBack(ImageView sprite) {
        this.spriteBack = sprite;
    }
    public void setSpriteFront(ImageView sprite) {
        this.spriteFront = sprite;
    }


}
