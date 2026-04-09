import java.util.List;
import java.util.Iterator;

public class CleanerCup extends Cup {

    public CleanerCup(int id, int height, int width, String color) {
        super(id, height, width, color);
    }

    /**
     * Cuando entra a la torre, elimina todas las tazas con ID menor.
     */
    @Override
    public void enterTower(Tower t) {
        List<Cup> cups = t.getCups();
        Iterator<Cup> it = cups.iterator();
        while (it.hasNext()) {
            Cup c = it.next();
            if (c.getId() < this.getId()) {
                c.hide(); 
                it.remove();
            }
        }
    }
}