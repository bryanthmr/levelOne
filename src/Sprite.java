public class Sprite {

    private String spritePath;
    private int x;
    private int y;
    private int w;
    private int h;

    public Sprite(String spritePath, int x, int y, int w, int h) {
        this.spritePath = spritePath;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public String getSpritePath() {
        return spritePath;
    }

    public void setSpritePath(String spritePath) {
        this.spritePath = spritePath;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
}
