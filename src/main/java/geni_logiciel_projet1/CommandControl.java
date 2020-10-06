package geni_logiciel_projet1;

public class CommandControl {

    private DestinationList destinationList;
    private int state;
    private int current_floor;

    public CommandControl() {
        destinationList = new DestinationList();
        current_floor = 0;
        state = Constante.State.WAITING;
    }

    public int getCurrent_floor() {
        return current_floor;
    }

    public int getState() {
        return state;
    }

    public void setCurrent_floor(int current_floor) {
        this.current_floor = current_floor;
        checkForStopNext();
    }

    private void checkForStopNext() {
        int value = destinationList.getFirstDestination().getDestination_floor() - current_floor;
        if (value == 0 || value == -0) {
            App.stopNext();
        }
    }

    public void setState(int state) {
        this.state = state;
        checkForUnloading();
    }

    private void checkForUnloading() {
        if (state == Constante.State.UNLOADING) {
            destinationList.removeFirst();
            if (!destinationList.isEmpty()) {
                int action = (destinationList.getFirstDestination().getDirection());
                App.updateActionOperativ(action);
            }
            else App.updateActionOperativ(Constante.Actions.WAITING);
        }
    }

    public void addDestination(Destination destination) {
        destinationList.addDestination(destination);
        checkForFirstAction();
    }


    public void addDestination (int destination) {
        if (!destinationList.isEmpty()) {
            Destination destinationObj = new Destination(destination,
                    Integer.compare(destination, destinationList.getFirstDestination().getDestination_floor()));
            addDestination(destinationObj);
        }
        else {
            Destination destinationObj = new Destination(destination,
                    Integer.compare(destination, current_floor));
            addDestination(destinationObj);
        }
    }

    private void checkForFirstAction() {
        if (state == Constante.State.WAITING){
            App.updateActionOperativ(destinationList.getFirstDestination().getDirection());
        }
    }

}
