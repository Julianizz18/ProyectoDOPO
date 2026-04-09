import java.util.*;
import javax.swing.JOptionPane;

/**
 * Representa una torre de tazas apilables con diferentes tipos de Cups y Lids.
 * Permite agregar, remover, ordenar y visualizar la torre.
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
     * Constructor que crea una torre vacía con dimensiones específicas.
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
     * Constructor que crea una torre por defecto e inserta n tazas normales.
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
     * Agrega una taza normal a la torre.
     */
    public void pushCup(int i) {
        pushCup(i, "normal");
    }

    /**
     * Agrega una taza indicando su tipo (normal, opener, hierarchical).
     */
    public void pushCup(int id, String type) {
        if (findCup(id) != null) {
            error("La taza " + id + " ya existe");
            return;
        }
    
        int cupHeight = 2 * id - 1;
        Cup cup;
    
        switch(type.toLowerCase()) {
            case "opener":
                cup = new OpenerCup(id, cupHeight, width, randomColor(id));
                break;
            case "hierarchical":
                cup = new HierarchicalCup(id, cupHeight, width, randomColor(id));
                break;
            case "cleaner":
                cup = new CleanerCup(id, cupHeight, width, randomColor(id));
                break;
            default:
                cup = new Cup(id, cupHeight, width, randomColor(id));
        }
    
        cup.enterTower(this);
    
        if (!cups.contains(cup)) {
            if (height() + cup.totalHeight() <= maxHeight) {
                cups.add(cup);
            } else {
                error("No hay espacio en la torre");
                return;
            }
        }
    
        reorganize();
        lastOpOk = true;
    }

    /**
     * Elimina la taza superior.
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
     * Elimina una taza específica si es removible.
     */
    public void removeCup(int i) {
        Cup c = findCup(i);
        if (c != null && c.canBeRemoved()) {
            cups.remove(c);
            c.hide();
            reorganize();
            lastOpOk = true;
        } else {
            lastOpOk = false;
        }
    }

    /**
     * Agrega una tapa normal a una taza.
     */
    public void pushLid(int i) {
        pushLid(i, "normal");
    }

    /**
     * Agrega una tapa indicando su tipo (normal, fearful, crazy).
     */
    public void pushLid(int id, String type) {
        Cup c = findCup(id);
        Lid lid;

        if (c == null) {
            lastOpOk = false;
            return;
        }
    
        if (c.hasLid()) {
            error("La taza " + id + " ya tiene tapa.");
            return;
        }
    
        switch(type.toLowerCase()) {
            case "fearful":
                lid = new FearfulLid(id, width, "black");
                break;
            case "crazy":
                lid = new CrazyLid(id, width, "black");
                break;
            default:
                lid = new Lid(id, width, "black");
        }
    
        if (!lid.canEnter(this, c)) {
            lastOpOk = false;
            return;
        }
    
        if (height() + lid.getHeight() <= maxHeight) {
            c.putLid(lid);
            if (visible) reorganize();
            lastOpOk = true;
        } else {
            error("No hay espacio para la tapa.");
        }
    }

    /**
     * Elimina la tapa superior válida.
     */
    public void popLid() {
        lastOpOk = false;
        for (int i = cups.size() - 1; i >= 0; i--) {
            if (cups.get(i).hasLid()) {
                if (!cups.get(i).getLid().canExit(this, cups.get(i))) {
                    continue;
                }
                cups.get(i).removeLid();
                reorganize();
                lastOpOk = true;
                break;
            }
        }
    }

    /**
     * Elimina la tapa de una taza específica.
     */
    public void removeLid(int i) {
        Cup c = findCup(i);
        if (c != null && c.hasLid()) {
            if (!c.getLid().canExit(this, c)) {
                lastOpOk = false;
                return;
            }
            c.removeLid();
            reorganize();
            lastOpOk = true;
        } else {
            lastOpOk = false;
        }
    }

    /**
     * Intercambia dos tazas.
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
     * Coloca tapas a todas las tazas posibles.
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
     * Busca un intercambio que reduzca la altura.
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
     * Ordena las tazas de mayor a menor.
     */
    public void orderTower() {
        cups.sort((a, b) -> b.getId() - a.getId());
        reorganize();
        lastOpOk = true;
    }

    /**
     * Invierte el orden de la torre.
     */
    public void reverseTower() {
        Collections.reverse(cups);
        reorganize();
        lastOpOk = true;
    }

    /**
     * Calcula la altura total.
     */
    public int height() {
        return cups.stream().mapToInt(Cup::totalHeight).sum();
    }

    /**
     * Retorna IDs de tazas con tapa.
     */
    public int[] lidedCups() {
        return cups.stream()
            .filter(Cup::hasLid)
            .mapToInt(Cup::getId)
            .sorted()
            .toArray();
    }

    /**
     * Retorna todos los elementos apilados.
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
     * Hace visible la torre.
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
     * Oculta la torre.
     */
    public void makeInvisible() {
        visible = false;
        hideFrame();
        for (Cup c : cups) c.hide();
        lastOpOk = true;
    }

    /**
     * Finaliza el programa.
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Indica si la última operación fue exitosa.
     */
    public boolean ok() {
        return lastOpOk;
    }

    /**
     * Inserta una taza en una posición específica.
     */
    public void addCupAt(Cup cup, int index) {
        cups.add(index, cup);
        reorganize();
    }

    /**
     * Retorna la lista interna de tazas.
     */
    public List<Cup> getCups() {
        return cups;
    }

    /**
     * Inserta una tapa loca como base.
     */
    public void insertCrazyLidAsBase(Lid lid) {
        Cup fake = new Cup(lid.getId(), 1, width, "black");
        cups.add(0, fake);
        reorganize();
    }

    /**
     * Busca una taza por ID.
     */
    private Cup findCup(int id) {
        for (Cup c : cups) if (c.getId() == id) return c;
        return null;
    }

    /**
     * Reorganiza visualmente la torre.
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
     * Dibuja el marco de la torre.
     */
    private void drawFrame() {
        int hPx = maxHeight * SCALE;
        int wPx = width * SCALE;
        leftBorder = createRect(X, Y, hPx, 2);
        rightBorder = createRect(X + wPx, Y, hPx, 2);
        base = createRect(X, Y + hPx, 2, wPx + 2);
    }

    /**
     * Crea un rectángulo visual.
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
     * Dibuja marcas de altura.
     */
    private void drawMarks() {
        int baseY = Y + (maxHeight * SCALE);
        for (int i = 1; i <= maxHeight; i++) {
            marks.add(createRect(X, baseY - (i * SCALE), 1, width * SCALE));
        }
    }

    /**
     * Oculta el marco.
     */
    private void hideFrame() {
        if (leftBorder != null) leftBorder.makeInvisible();
        if (rightBorder != null) rightBorder.makeInvisible();
        if (base != null) base.makeInvisible();
        for (Rectangle r : marks) r.makeInvisible();
        marks.clear();
    }

    /**
     * Maneja errores.
     */
    private void error(String msg) {
        lastOpOk = false;
        if (visible) JOptionPane.showMessageDialog(null, msg);
    }

    /**
     * Genera un color basado en el ID.
     */
    private String randomColor(int id) {
        String[] colors = {"red", "blue", "green", "yellow", "magenta"};
        return colors[Math.abs(id) % colors.length];
    }
}