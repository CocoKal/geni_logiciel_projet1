package geni_logiciel_projet1;

import com.sun.jdi.ThreadReference;

public class Operativ extends Thread{

    private int min, max, current_action;
    private Cab cab;

    public Operativ(int min, int max, int base_floor) {
        this.min = min;
        this.max = max;
        cab = new Cab(base_floor);
        current_action = Constante.State.WAITING;
    }

    @Override
    public void run() {
        while (current_action != Constante.Actions.EMERGENCY_STOP) {
            if (current_action == Constante.Actions.GO_UP) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    currentThread().interrupt();
                    break;
                }
                goUp();
                notifyUpdateFloor();

            }
            if (current_action == Constante.Actions.GO_DOWN) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    currentThread().interrupt();
                    break;
                }
                goDown();
                notifyUpdateFloor();

            }
        }
    }

    private void notifyUpdateFloor() {

    }

    public int getCurrent_floor() {
        return cab.getCurrent_floor();
    }

    public void setCurrent_action(int current_action) {
        this.current_action = current_action;
    }

    public void goUp() {
        cab.goUp();
    }

    public void goDown() {
        cab.goDown();
    }

}
