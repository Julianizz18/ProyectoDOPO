public class Cup {
    private int id;
    private int height;
    private int width;
    private String color;
    private Rectangle shape;
    private Lid lid;
    private int currentY;
    private int currentX;

    public Cup(int id, int height, int width, String color) {
        this.id = id;
        this.height = height;
        this.width = width;
        this.color = color;
        this.shape = new Rectangle();
        this.shape.changeSize(height * Tower.SCALE, width * Tower.SCALE);
        this.shape.changeColor(color);
        this.currentX = 50; 
        this.shape.moveHorizontal(currentX);
    }

    public int getId() { 
        return id; 
    }
    
    public String getColor() {
        return color; 
    }
    
    public int getWidth() { 
        return width; 
    }
    
    public int getCupHeight() {
        return height; 
    }
    
    public int totalHeight() {
        return height + (lid != null ? lid.getHeight() : 0);
    }
    
    public boolean hasLid() { 
        return lid != null; 
    }

    public void putLid(Lid lid) { 
        this.lid = lid; 
    }
    
    public void removeLid() {
        if (lid != null) {
            lid.hide();
            lid = null;
        }
    }

    public void setPosition(int x, int y) {
        shape.moveVertical(y - currentY);
        currentY = y;
        if (lid != null) {
            lid.setPosition(x, y - (1 * Tower.SCALE));
        }
    }

    public void show() {
        shape.makeVisible();
        if (lid != null) lid.show();
    }

    public void hide() {
        shape.makeInvisible();
        if (lid != null) lid.hide();
    }
}
