public class OpenerCup extends Cup {

    public OpenerCup(int id, int height, int width, String color) {
        super(id, height, width, color);
    }

    @Override
    public void enterTower(Tower t) {
        int[] cupsWithLid = t.lidedCups();
        for (int i : cupsWithLid) {
            t.removeLid(i);
        }
    }
}
