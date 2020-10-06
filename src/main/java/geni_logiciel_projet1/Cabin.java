package geni_logiciel_projet1;

public class Cabin {

    private int current_floor;

    public Cabin(int base_floor) {
        current_floor = base_floor;
    }

    public int getCurrent_floor() {
        return current_floor;
    }

    public void goUp () {
        current_floor++;
    }

    public void goDown () {
        current_floor--;
    }
}
