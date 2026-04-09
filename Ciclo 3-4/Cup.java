public class Cup {

    private int id;
    private int height;
    private int width;
    private String color;
    private Rectangle leftWall;
    private Rectangle rightWall;
    private Rectangle bottom;
    private Rectangle inside;
    protected Lid lid; // 👈 protegido para herencia
    private int currentX = 0;
    private int currentY = 0;

    public Cup(int id, int height, int width, String color) {
        this.id = id;
        this.height = height;
        this.width = width;
        this.color = color;
        leftWall = new Rectangle();
        rightWall = new Rectangle();
        bottom = new Rectangle();
        inside = new Rectangle();
        leftWall.changeColor(color);
        rightWall.changeColor(color);
        bottom.changeColor(color);
        inside.changeColor("white");
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

    public boolean hasLid() {
        return lid != null; 
    }

    public Lid getLid() { // 👈 NUEVO
        return lid;
    }

    public int totalHeight() {
        return height + (lid != null ? lid.getHeight() : 0);
    }

    public void setPosition(int x, int y) {
        int hPx = height * Tower.SCALE;
        int wPx = width * Tower.SCALE;
        int wall = 8;
        int floor = 5;

        int adjustX = 70;
        int adjustY = 16;
        
        int finalX = x + adjustX;
        int finalY = y + adjustY;

        int lidHeightPx = (lid != null) ? lid.getHeight() * Tower.SCALE : 0;
        int cupTopY = finalY + lidHeightPx;

        leftWall.changeSize(hPx, wall);
        rightWall.changeSize(hPx, wall);
        bottom.changeSize(floor, wPx);
        inside.changeSize(hPx - floor, wPx - (2 * wall));

        leftWall.changePosition(finalX, cupTopY);
        rightWall.changePosition(finalX + wPx - wall, cupTopY);
        bottom.changePosition(finalX, cupTopY + hPx - floor);
        inside.changePosition(finalX + wall, cupTopY);

        if (lid != null) {
            lid.setPosition(finalX, finalY); 
        }

        this.currentX = x;
        this.currentY = y;
    }

    public void putLid(Lid lid) {
        this.lid = lid;
        leftWall.changeColor("black");
        rightWall.changeColor("black");
        bottom.changeColor("black");
        inside.changeColor("white"); 
    }

    public void removeLid() {
        if (lid != null) {
            lid.hide();
            lid = null;

            leftWall.changeColor(color);
            rightWall.changeColor(color);
            bottom.changeColor(color);
            inside.changeColor("white");
        }
    }

    public void show() {
        leftWall.makeVisible();    
        rightWall.makeVisible();
        bottom.makeVisible();
        inside.makeVisible();     
        if (lid != null) {
            lid.show();
        }
    }   

    public void hide() {
        leftWall.makeInvisible();
        rightWall.makeInvisible();
        bottom.makeInvisible();
        inside.makeInvisible();
        if (lid != null) lid.hide();
    }

    public void enterTower(Tower t) {
        // comportamiento normal
    }

    public boolean canBeRemoved() {
        return true;
    }
}
