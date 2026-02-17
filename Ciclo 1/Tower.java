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
     * Crea una torre dados el ancho y el alto máximo[cite: 87].
     * @param width Ancho de la torre en cm.
     * @param maxHeight Altura máxima permitida para la torre en cm.
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
     * Adiciona una taza a la torre. Solo puede existir una taza por cada número.
     * @param i El identificador/número de la taza.
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
     * Elimina la última taza agregada a la cima de la torre.
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
     * Elimina una taza específica de la torre según su número.
     * @param i El identificador de la taza a eliminar.
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
     * Adiciona una tapa a una taza existente. Solo puede existir una tapa por cada número[cite: 104].
     * @param i El identificador de la taza a la que se le pondrá tapa.
     */
    public void pushLid(int i) {
        Cup c = findCup(i);
        if (c != null && !c.hasLid() && (height() + 1 <= maxHeight)) {
            c.putLid(new Lid(i, c.getWidth(), c.getColor()));
            reorganize();
            lastOpOk = true;
        } else {
            lastOpOk = false;
        }
    }

    /**
     * Elimina la tapa del elemento que esté más arriba en la torre y que tenga tapa.
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
     * @param i El identificador de la taza cuya tapa se eliminará.
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
     * Ordena los elementos de mayor a menor. El número menor siempre queda en la cima. 
     * Si hay taza y tapa del mismo número, la tapa se coloca sobre la taza.
     */
    public void orderTower() {
        cups.sort((a, b) -> b.getId() - a.getId());
        reorganize();
        lastOpOk = true;
    }

    /**
     * Invierte el orden actual de los elementos apilados en la torre.
     */
    public void reverseTower() {
        Collections.reverse(cups);
        reorganize();
        lastOpOk = true;
    }

    /**
     * Consulta la altura total de los elementos apilados en centímetros.
     * @return Altura total en cm.
     */
    public int height() {
        return cups.stream().mapToInt(Cup::totalHeight).sum();
    }

    /**
     * Retorna los números de las tazas tapadas por sus tapas, ordenados de menor a mayor[cite: 107].
     * @return Arreglo de enteros con los IDs de las tazas con tapa.
     */
    public int[] lidedCups() {
        return cups.stream()
                   .filter(Cup::hasLid)
                   .mapToInt(Cup::getId)
                   .sorted()
                   .toArray();
    }

    /**
     * Retorna la información de los elementos ordenados de base a cima en minúsculas.
     * Ej: {{"cup", "4"}, {"lid", "4"}}
     * @return Matriz de String con tipo y número del elemento.
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
     * Hace visible el simulador. Si la imagen no cabe en la pantalla, no se hace visible.
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
     * Oculta el simulador y todos sus elementos visuales[cite: 101].
     */
    public void makeInvisible() {
        visible = false;
        hideFrame();
        for (Cup c : cups) c.hide();
        lastOpOk = true;
    }

    /**
     * Termina la ejecución del simulador[cite: 102].
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Indica si se logró realizar con éxito la última operación solicitada[cite: 110].
     * @return true si la última operación fue exitosa, false de lo contrario.
     */
    public boolean ok() {
        return lastOpOk;
    }

    /**
     * metodos de ayuda 
     */
    private Cup findCup(int id) {
        for (Cup c : cups) if (c.getId() == id) return c;
        return null;
    }

    private void reorganize() {
        int groundY = Y + (maxHeight * SCALE);
        int currentY = groundY;
        for (Cup c : cups) {
            currentY -= c.totalHeight() * SCALE;
            c.setPosition(X, currentY);
            if (visible) c.show();
        }
    }

    private void drawFrame() {
        int hPx = maxHeight * SCALE;
        int wPx = width * SCALE;
        leftBorder = createRect(X, Y, hPx, 2);
        rightBorder = createRect(X + wPx, Y, hPx, 2);
        base = createRect(X, Y + hPx, 2, wPx + 2);
    }

    
    private Rectangle createRect(int x, int y, int h, int w) {
        Rectangle r = new Rectangle();
        r.changeSize(h, w);
        r.changeColor("black");
        r.moveHorizontal(x);
        r.moveVertical(y);
        r.makeVisible();
        return r;
    }

    private void drawMarks() {
        int baseY = Y + (maxHeight * SCALE);
        for (int i = 1; i <= maxHeight; i++) {
            marks.add(createRect(X, baseY - (i * SCALE), 1, width * SCALE));
        }
    }

    private void hideFrame() {
        if (leftBorder != null) leftBorder.makeInvisible();
        if (rightBorder != null) rightBorder.makeInvisible();
        if (base != null) base.makeInvisible();
        for (Rectangle r : marks) r.makeInvisible();
        marks.clear();
    }

    private void error(String msg) {
        lastOpOk = false;
        if (visible) JOptionPane.showMessageDialog(null, msg);
    }

    private String randomColor(int id) {
        String[] colors = {"red", "blue", "green", "yellow", "magenta"};
        return colors[Math.abs(id) % colors.length];
    }
}
