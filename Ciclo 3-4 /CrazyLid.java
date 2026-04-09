public class CrazyLid extends Lid {

    public CrazyLid(int id, int width, String color) {
        super(id, width, color);
    }

    @Override
    public boolean canEnter(Tower t, Cup c) {
        t.insertCrazyLidAsBase(this);
        return false;
    }
}
