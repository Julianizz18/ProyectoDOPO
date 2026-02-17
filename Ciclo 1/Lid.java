public class Lid {

    public static final int HEIGHT= 1;

    private Rectangle shape;
    private int currentX = 0;
    private int currentY = 0;

    public Lid(int id, int width, String color) {
        shape = new Rectangle();
        shape.changeSize(HEIGHT * Tower.SCALE, width * Tower.SCALE);
        shape.changeColor(color);
    }

    public int getHeight() {
        return HEIGHT;
    }

    public void setPosition(int x, int y) {
        shape.moveHorizontal(x - currentX);
        shape.moveVertical(y - currentY);
        currentX = x;
        currentY = y;
    }

    public void show() {
        shape.makeVisible();
    }

    public void hide() {
        shape.makeInvisible();
    }
}
