package geni_logiciel_projet1;

public class Operativ extends Thread {

    private int min, max, current_action, state;
    private boolean stop_next;
    private Cabin cab;

    public Operativ(int min, int max, int base_floor) {
        this.min = min;
        this.max = max;
        stop_next = false;
        cab = new Cabin(base_floor);
        current_action = Constante.Actions.WAITING;
        state = Constante.State.WAITING;
    }

    @Override
    public void run() {
        while (current_action != Constante.Actions.EMERGENCY_STOP) {

            doAction(current_action);

            if (stop_next) {
                try {
                    unloading();
                } catch (InterruptedException e) {
                    currentThread().interrupt();
                }
            }
        }
    }

    private void unloading() throws InterruptedException {
        setState(Constante.State.UNLOADING);
        Thread.sleep(2000);
        stop_next = false;
        setState(Constante.State.WAITING);

    }

    public int getCurrent_floor() {
        return cab.getCurrent_floor();
    }

    public int getStateOperativ() {
        return state;
    }

    public void setCurrent_action(int current_action) {
        this.current_action = current_action;
    }

    public void setStop_nextTrue() {
        stop_next = true;
    }

    public void setState(int state) {
        this.state = state;
        notifyStateChange();
    }

    public void updateAction (int action) {
        setCurrent_action(action);
    }

    private void notifyFloorChange() {
        App.updateFloor(getCurrent_floor());
    }

    private void notifyStateChange() {
        App.updateState(state);
    }

    public void doAction(int action) {
        if (action != Constante.Actions.WAITING) {
            setState(Constante.State.MOVING);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                currentThread().interrupt();
            }
            if (action == Constante.Actions.GO_DOWN) cab.goDown();
            else if (action == Constante.Actions.GO_UP) cab.goUp();
            notifyFloorChange();
        }
        else System.out.println();
    }

}
