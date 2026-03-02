/**
 * Representa la tapa de una taza en la torre.
 * Se encarga de gestionar su propia forma, color y ubicación en el lienzo.
 * * @author Sara Arteaga - Julián Tinjacá
 */
public class Lid {

    public static final int HEIGHT = 1;
    private Rectangle shape;
    private int currentX = 0;
    private int currentY = 0;
    private int id;

    /**
     * Constructor de la clase Lid.
     * @param id Identificador único que vincula la tapa con una taza.
     * @param width Ancho de la tapa en unidades.
     * @param color Color de la tapa.
     */
    public Lid(int id, int width, String color) {
        this.id = id; 
        shape = new Rectangle();
        shape.changeSize(HEIGHT * Tower.SCALE, width * Tower.SCALE);
        shape.changeColor(color);
    }

    /**
     * Obtiene la altura constante de la tapa.
     * @return Altura en unidades (definida por la constante HEIGHT).
     */
    public int getHeight() {
        return HEIGHT;
    }

    /**
     * Define la posición física de la tapa en el lienzo.
     * @param x Coordenada horizontal.
     * @param y Coordenada vertical.
     */
    public void setPosition(int x, int y) {
        shape.changePosition(x, y);
        currentX = x;
        currentY = y;
    }

    /**
     * Obtiene el identificador de la tapa.
     * @return id de la tapa.
     */
    public int getId() {
        return id;
    }

    /**
     * Hace visible la representación gráfica de la tapa en el lienzo.
     */
    public void show() {
        shape.makeVisible();
    }

    /**
     * Oculta la representación gráfica de la tapa del lienzo.
     */
    public void hide() {
        shape.makeInvisible();
    }
}