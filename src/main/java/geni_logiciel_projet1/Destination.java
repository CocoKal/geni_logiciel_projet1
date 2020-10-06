package geni_logiciel_projet1;

public class Destination {

    private int destination_floor;
    private int direction;

    public Destination (int destination_floor, int direction) {
        this.destination_floor = destination_floor;
        this.direction = direction;
    }

    public int getDestination_floor() {
        return destination_floor;
    }

    public int getDirection() {
        return direction;
    }
}
