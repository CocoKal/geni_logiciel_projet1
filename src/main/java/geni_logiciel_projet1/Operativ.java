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

    /**
     * La partie opérative étant un Thread, cette fonction s'execute en boucle et représente l'automate d'execution.
     */
    @Override
    public void run() {
        while (state != Constante.State.EMERGENCY_STOP) {

            doAction(current_action);

            if (stop_next) {
                try {
                    if (!currentThread().isInterrupted()) {
                        unloading();
                    }
                    else currentThread().interrupt();
                } catch (InterruptedException e) {
                    currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Décharge les passagers de la cabine.
     * @throws InterruptedException
     */
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

    /**
     * Notifie le controlleur de commande que l'étage a changé.
     */
    private void notifyFloorChange() {
        App.updateFloor(getCurrent_floor());
    }

    /**
     * Notifie le controlleur de commande que l'état a changé.
     */
    private void notifyStateChange() {
        App.updateState(state);
    }

    /**
     * Execute une action si l'action courrante n'est pas WAITING.
     * @param action
     */
    public void doAction(int action) {
        if (action != Constante.Actions.WAITING) {
            setState(Constante.State.MOVING);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                currentThread().interrupt();
            }
            if (!currentThread().isInterrupted()) {
                if (action == Constante.Actions.GO_DOWN) cab.goDown();
                else if (action == Constante.Actions.GO_UP) cab.goUp();
            notifyFloorChange();
            }
            else {
                currentThread().interrupt();
                App.setStateEmergencyStoped();
            }
        }
        else {
            boolean test = currentThread().isInterrupted();
            if (test) currentThread().interrupt();
        }
    }

}
