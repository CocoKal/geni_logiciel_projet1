package geni_logiciel_projet1;

public class CommandControl {

    private DestinationList destinationList;
    private int current_floor;

    public CommandControl() {
        destinationList = new DestinationList();
        current_floor = 0;
    }

    public void setCurrent_floor(int current_floor) {
        this.current_floor = current_floor;
    }

    public int getCurrent_floor() {
        return current_floor;
    }

    public void addDestination(Destination destination) {
        destinationList.addDestination(destination);
    }

    public void addDestination (int destination) {
        Destination destinationObj = new Destination(destination,
                Integer.compare(destination, current_floor));
        addDestination(destinationObj);
    }
}
