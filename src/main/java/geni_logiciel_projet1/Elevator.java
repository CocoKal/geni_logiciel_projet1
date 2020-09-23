package geni_logiciel_projet1;

import java.util.LinkedList;
import java.util.TimerTask;

public class Elevator extends TimerTask {

    private int curent_level;
    private int curent_state;
    private LinkedList<Integer> destinations_list;

    Elevator(){
        this.curent_level = 0;
        this.curent_state = Constante.State.WAITING;
        this.destinations_list = new LinkedList<>();
    }

    int getCurrent_level() {
        return curent_level;
    }

    public void run(){
            move(getAction());
    }

    private int getAction() {
        return (curent_level < destinations_list.getFirst()) ? Constante.Actions.GO_UP :
                (curent_level > destinations_list.getFirst()) ? Constante.Actions.GO_DOWN :
                        Constante.Actions.STOP_NEXT;
    }

    private void move(int action){

        if (action == 0) {
            destinations_list.removeFirst();
            updateState(Constante.State.UNLOADING);
        }
        else {
            curent_level = curent_level + action;
            updateState(action);
        }
    }

    void addDestination(int destination){
        this.destinations_list.add(destination);
    }

    boolean destination_listIsEmpty() {
        return destinations_list.isEmpty();
    }

    void updateState(int state) {
        curent_state = state;
        App.updateLabelState(state);
    }


}
