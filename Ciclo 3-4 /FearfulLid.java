public class FearfulLid extends Lid {

    public FearfulLid(int id, int width, String color) {
        super(id, width, color);
    }

    @Override
    public boolean canEnter(Tower t, Cup c) {
        return c != null;
    }

    @Override
    public boolean canExit(Tower t, Cup c) {
        return false;
    }
}
