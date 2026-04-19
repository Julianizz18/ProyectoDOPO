public class Lid {

    public static final int HEIGHT = 1;
    private Rectangle shape;
    private int currentX = 0;
    private int currentY = 0;
    private int id;

    public Lid(int id, int width, String color) {
        this.id = id; 
        shape = new Rectangle();
        shape.changeSize(HEIGHT * Tower.SCALE, width * Tower.SCALE);
        shape.changeColor(color);
    }

    public int getHeight() {
        return HEIGHT;
    }

    public void setPosition(int x, int y) {
        shape.changePosition(x, y);
        currentX = x;
        currentY = y;
    }

    public int getId() {
        return id;
    }

    public void show() {
        shape.makeVisible();
    }

    public void hide() {
        shape.makeInvisible();
    }



    public boolean canEnter(Tower t, Cup c) {
        return true;
    }

    public boolean canExit(Tower t, Cup c) {
        return true;
    }
}
