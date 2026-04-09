import java.util.List;

public class HierarchicalCup extends Cup {

    private boolean locked = false;

    public HierarchicalCup(int id, int height, int width, String color) {
        super(id, height, width, color);
    }

    @Override
    public void enterTower(Tower t) {
    
        List<Cup> cups = t.getCups();
    
        int position = 0;
    
        for (int i = cups.size() - 1; i >= 0; i--) {
            if (cups.get(i).getId() > this.getId()) {
                position = i + 1;
                break;
            }
        }
    
        t.addCupAt(this, position);
    
        locked = true;
    }

    @Override
    public boolean canBeRemoved() {
        return !locked;
    }
}
