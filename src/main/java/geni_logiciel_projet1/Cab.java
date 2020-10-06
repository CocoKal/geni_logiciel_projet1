package geni_logiciel_projet1;

public class Cab {

    private int current_floor;

    public Cab(int base_floor) {
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
