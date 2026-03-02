import java.util.*;
import javax.swing.JOptionPane;

/**
 * Representa una torre de tazas apilables con tapas, basada en el problema Stacking Cups.
 * Permite gestionar el apilamiento, ordenamiento y visualización de los elementos.
 * * @author Sara Arteaga - Julián Tinjacá
 */
public class Tower {
    public static final int SCALE = 10;
    private static final int X = 50; 
    private static final int Y = 50;
    private int width;
    private int maxHeight;
    private List<Cup> cups;
    private boolean visible;
    private boolean lastOpOk;
    private Rectangle leftBorder, rightBorder, base;
    private List<Rectangle> marks;

    /**
     * Constructor para crear una torre vacía con dimensiones específicas.
     * @param width Ancho de la torre en unidades.
     * @param maxHeight Altura máxima permitida para la torre.
     */
    public Tower(int width, int maxHeight) {
        this.width = width;
        this.maxHeight = maxHeight;
        this.cups = new ArrayList<>();
        this.marks = new ArrayList<>();
        this.visible = false;
        this.lastOpOk = true;
    }
    
    /**
     * Constructor que crea una torre predeterminada y añade una cantidad inicial de tazas.
     * @param cups Cantidad de tazas a generar e insertar inicialmente.
     */
    public Tower(int cups) {
        this.width = 10;
        this.maxHeight = 20;
        this.cups = new ArrayList<>();
        this.marks = new ArrayList<>();
        this.visible = false;
        this.lastOpOk = true;
    
        for (int i = 1; i <= cups; i++) {
            pushCup(i);
        }
    }

    /**
     * Intenta añadir una nueva taza a la cima de la torre.
     * @param i Identificador único de la taza a añadir.
     */
    public void pushCup(int i) {
        if (findCup(i) != null) {
            error("La taza " + i + " ya existe");
            return;
        }
        int cupHeight = 2 * i - 1;
        Cup newCup = new Cup(i, cupHeight, width, randomColor(i));
        
        if (height() + newCup.totalHeight() <= maxHeight) {
            cups.add(newCup);
            reorganize();
            lastOpOk = true;
        } else {
            error("No hay espacio en la torre");
        }
    }

    /**
     * Elimina la taza que se encuentra en la cima de la torre.
     */
    public void popCup() {
        if (!cups.isEmpty()) {
            cups.remove(cups.size() - 1).hide();
            reorganize();
            lastOpOk = true;
        } else {
            lastOpOk = false;
        }
    }

    /**
     * Elimina una taza específica de la torre según su identificador.
     * @param i Identificador de la taza a remover.
     */
    public void removeCup(int i) {
        Cup c = findCup(i);
        if (c != null) {
            cups.remove(c);
            c.hide();
            reorganize();
            lastOpOk = true;
        } else {
            lastOpOk = false;
        }
    }

    /**
     * Crea y coloca una tapa negra sobre una taza específica.
     * @param i Identificador de la taza a la que se le pondrá la tapa.
     */
    public void pushLid(int i) {
        Cup c = findCup(i);
    
        if (c == null) {
            lastOpOk = false;
            return;
        }
        
        if (c.hasLid()) {
            error("La taza " + i + " ya tiene tapa.");
            return;
        }
    
        Lid newLid = new Lid(i, width, "black"); 
        if (height() + newLid.getHeight() <= maxHeight) {
            c.putLid(newLid); 
            
            if (visible) {
                reorganize(); 
            }
            lastOpOk = true;
        } else {
            error("No hay espacio para la tapa en la torre.");
        }
    }

    /**
     * Elimina la tapa de la taza más alta que tenga una puesta.
     */
    public void popLid() {
        lastOpOk = false;
        for (int i = cups.size() - 1; i >= 0; i--) {
            if (cups.get(i).hasLid()) {
                cups.get(i).removeLid();
                reorganize();
                lastOpOk = true;
                break;
            }
        }
    }

    /**
     * Elimina la tapa de una taza específica.
     * @param i Identificador de la taza a la cual quitar la tapa.
     */
    public void removeLid(int i) {
        Cup c = findCup(i);
        if (c != null && c.hasLid()) {
            c.removeLid();
            reorganize();
            lastOpOk = true;
        } else {
            lastOpOk = false;
        }
    }
    
    /**
     * Intercambia la posición de dos tazas en la torre.
     * @param o1 Arreglo que representa el primer objeto (ej: ["cup", "1"]).
     * @param o2 Arreglo que representa el segundo objeto (ej: ["cup", "2"]).
     */
    public void swap(String[] o1, String[] o2) {
        if (!o1[0].equals("cup") || !o2[0].equals("cup")) {
            lastOpOk = false;
            return;
        }
    
        int id1 = Integer.parseInt(o1[1]);
        int id2 = Integer.parseInt(o2[1]);
    
        int i1 = -1;
        int i2 = -1;
    
        for (int i = 0; i < cups.size(); i++) {
            if (cups.get(i).getId() == id1) i1 = i;
            if (cups.get(i).getId() == id2) i2 = i;
        }
        if (i1 != -1 && i2 != -1) {
            Collections.swap(cups, i1, i2);
            reorganize();
            lastOpOk = true;
        } else {
            lastOpOk = false;
        }
    }   
    
    /**
     * Intenta poner tapas a todas las tazas de la torre que no tengan una.
     * Si no hay espacio para alguna tapa, la operación se detiene.
     */
    public void cover() {
        for (Cup cup : cups) {
            if (!cup.hasLid()) {
                Lid newLid = new Lid(cup.getId(), cup.getWidth(), cup.getColor());
                if (height() + newLid.getHeight() <= maxHeight) {
                    cup.putLid(newLid);
                } else {
                    lastOpOk = false;
                    reorganize();
                    return;
                }
            }
        }
        reorganize();
        lastOpOk = true;
    }
    
    /**
     * Busca un intercambio entre dos tazas que resulte en una disminución de la altura total de la torre.
     * @return Un arreglo bidimensional con los IDs de las tazas a intercambiar, o vacío si no hay mejora.
     */
    public String[][] swapToReduce() {
        int currentHeight = height();
        for (int i = 0; i < cups.size(); i++) {
            for (int j = i + 1; j < cups.size(); j++) {
                Collections.swap(cups, i, j);
                int newHeight = height();
                Collections.swap(cups, i, j);
                if (newHeight < currentHeight) {
                    return new String[][]{
                        {"cup", String.valueOf(cups.get(i).getId())},
                        {"cup", String.valueOf(cups.get(j).getId())}
                    };
                }
            }
        }
        return new String[0][0];
    }

    /**
     * Ordena las tazas de la torre de forma descendente según su identificador.
     */
    public void orderTower() {
        cups.sort((a, b) -> b.getId() - a.getId());
        reorganize();
        lastOpOk = true;
    }

    /**
     * Invierte el orden actual de las tazas en la torre.
     */
    public void reverseTower() {
        Collections.reverse(cups);
        reorganize();
        lastOpOk = true;
    }

    /**
     * Calcula la altura total actual de la torre sumando tazas y tapas.
     * @return Altura total en unidades.
     */
    public int height() {
        return cups.stream().mapToInt(Cup::totalHeight).sum();
    }

    /**
     * Obtiene los identificadores de todas las tazas que tienen tapa actualmente.
     * @return Arreglo de enteros con los IDs ordenados.
     */
    public int[] lidedCups() {
        return cups.stream()
            .filter(Cup::hasLid)
            .mapToInt(Cup::getId)
            .sorted()
            .toArray();
    }

    /**
     * Retorna una lista detallada de todos los elementos (tazas y tapas) en el orden en que están apilados.
     * @return Arreglo bidimensional de Strings con el tipo y ID de cada elemento.
     */
    public String[][] stackingItems() {
        List<String[]> items = new ArrayList<>();
        for (Cup c : cups) {
            items.add(new String[]{"cup", String.valueOf(c.getId())});
            if (c.hasLid()) {
                items.add(new String[]{"lid", String.valueOf(c.getId())});
            }
        }
        return items.toArray(new String[items.size()][]);
    }

    /**
     * Hace visible la torre y todos sus componentes en el Canvas.
     */
    public void makeVisible() {
        if ((maxHeight * SCALE) + Y > 800) {
            lastOpOk = false;
            return;
        }
        Canvas.getCanvas(); 
        visible = true;
        drawFrame();
        drawMarks();
        reorganize();
        lastOpOk = true;
    }

    /**
     * Oculta la torre y todos sus componentes del Canvas.
     */
    public void makeInvisible() {
        visible = false;
        hideFrame();
        for (Cup c : cups) c.hide();
        lastOpOk = true;
    }

    /**
     * Finaliza la ejecución de la aplicación.
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Indica si la última operación realizada fue exitosa.
     * @return true si la operación se completó correctamente, false de lo contrario.
     */
    public boolean ok() {
        return lastOpOk;
    }

    /**
     * Busca una taza en la lista interna mediante su ID.
     * @param id Identificador a buscar.
     * @return El objeto Cup correspondiente o null si no existe.
     */
    private Cup findCup(int id) {
        for (Cup c : cups) if (c.getId() == id) return c;
        return null;
    }

    /**
     * Recalcula la posición física (X, Y) de cada taza y tapa para reflejar el estado actual de la torre.
     */
    private void reorganize() {
        int groundY = Y + (maxHeight * SCALE);
        int currentY = groundY;
        
        for (Cup c : cups) {
            int blockHeightPx = c.totalHeight() * SCALE;
            currentY -= blockHeightPx;
            
            int freeSpacePx = (this.width - c.getWidth()) * SCALE;
            int centeredX = X + (freeSpacePx / 2);
            
            c.setPosition(centeredX, currentY);
            
            if (visible) {
                c.show();
            }
        }
    }

    /**
     * Dibuja los bordes laterales y la base de la torre.
     */
    private void drawFrame() {
        int hPx = maxHeight * SCALE;
        int wPx = width * SCALE;
        leftBorder = createRect(X, Y, hPx, 2);
        rightBorder = createRect(X + wPx, Y, hPx, 2);
        base = createRect(X, Y + hPx, 2, wPx + 2);
    }

    /**
     * Método auxiliar para crear, posicionar y mostrar un rectángulo negro.
     */
    private Rectangle createRect(int x, int y, int h, int w) {
        Rectangle r = new Rectangle();
        r.changeSize(h, w);
        r.changeColor("black");
        r.moveHorizontal(x);
        r.moveVertical(y);
        r.makeVisible();
        return r;
    }

    /**
     * Dibuja las marcas de nivel horizontales dentro de la torre.
     */
    private void drawMarks() {
        int baseY = Y + (maxHeight * SCALE);
        for (int i = 1; i <= maxHeight; i++) {
            marks.add(createRect(X, baseY - (i * SCALE), 1, width * SCALE));
        }
    }

    /**
     * Elimina visualmente el marco y las marcas de la torre del Canvas.
     */
    private void hideFrame() {
        if (leftBorder != null) leftBorder.makeInvisible();
        if (rightBorder != null) rightBorder.makeInvisible();
        if (base != null) base.makeInvisible();
        for (Rectangle r : marks) r.makeInvisible();
        marks.clear();
    }

    /**
     * Gestiona la notificación de errores al usuario y actualiza el estado de la última operación.
     * @param msg Mensaje de error a mostrar.
     */
    private void error(String msg) {
        lastOpOk = false;
        if (visible) JOptionPane.showMessageDialog(null, msg);
    }

    /**
     * Genera un color basado en el ID proporcionado para mantener consistencia visual.
     * @param id Identificador de la taza.
     * @return Nombre del color en String.
     */
    private String randomColor(int id) {
        String[] colors = {"red", "blue", "green", "yellow", "magenta"};
        return colors[Math.abs(id) % colors.length];
    }
}
