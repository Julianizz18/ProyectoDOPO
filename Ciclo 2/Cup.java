/**
 * Representa una taza individual que forma parte de la torre.
 * Puede cambiar su apariencia, posición y gestionar una tapa asociada.
 * * @author Sara Arteaga - Julián Tinjacá
 */
public class Cup {

    private int id;
    private int height;
    private int width;
    private String color;
    private Rectangle leftWall;
    private Rectangle rightWall;
    private Rectangle bottom;
    private Rectangle inside;
    private Lid lid;
    private int currentX = 0;
    private int currentY = 0;

    /**
     * Constructor de la clase Cup.
     * @param id Identificador único de la taza.
     * @param height Altura de la taza en unidades.
     * @param width Ancho de la taza en unidades.
     * @param color Color inicial de la taza.
     */
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

    /**
     * Obtiene el identificador de la taza.
     * @return id de la taza.
     */
    public int getId() { 
        return id; 
    }

    /**
     * Obtiene el color base de la taza.
     * @return nombre del color.
     */
    public String getColor() { 
        return color; 
    }

    /**
     * Obtiene el ancho de la taza.
     * @return ancho en unidades.
     */
    public int getWidth() { 
        return width; 
    }

    /**
     * Obtiene la altura propia de la taza sin contar la tapa.
     * @return altura en unidades.
     */
    public int getCupHeight() { 
        return height; 
    }

    /**
     * Verifica si la taza tiene una tapa asignada actualmente.
     * @return true si tiene tapa, false de lo contrario.
     */
    public boolean hasLid() {
        return lid != null; 
    }

    /**
     * Calcula la altura total del bloque (taza + tapa si existe).
     * @return altura total en unidades.
     */
    public int totalHeight() {
        return height + (lid != null ? lid.getHeight() : 0);
    }

    /**
     * Define la posición de la taza y sus componentes en el lienzo.
     * Ajusta automáticamente la ubicación de la taza si hay una tapa presente.
     * @param x Coordenada horizontal base.
     * @param y Coordenada vertical base (techo del bloque).
     */
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

    /**
     * Asigna una tapa a la taza y cambia el color de la estructura a negro.
     * @param lid Objeto Lid que se colocará sobre la taza.
     */
    public void putLid(Lid lid) {
        this.lid = lid;
        leftWall.changeColor("black");
        rightWall.changeColor("black");
        bottom.changeColor("black");
        inside.changeColor("white"); 
    }

    /**
     * Remueve la tapa de la taza y restaura el color original de la estructura.
     */
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

    /**
     * Hace visibles todos los componentes de la taza y su tapa en el orden correcto.
     */
    public void show() {
        leftWall.makeVisible();    
        rightWall.makeVisible();
        bottom.makeVisible();
        inside.makeVisible();     
        if (lid != null) {
            lid.show();
        }
    }   

    /**
     * Oculta todos los componentes de la taza y su tapa del lienzo.
     */
    public void hide() {
        leftWall.makeInvisible();
        rightWall.makeInvisible();
        bottom.makeInvisible();
        inside.makeInvisible();
        if (lid != null) lid.hide();
    }
}