package geni_logiciel_projet1;

import java.util.LinkedList;
import java.util.TimerTask;

public class Elevator extends TimerTask {

    private int current_level;
    private State current_state;
    private LinkedList<Integer> destinationsList;

    public Elevator(){
        this.current_level = 0;
        this.current_state = new State();
        this.destinationsList = new LinkedList<>();
    }

    public int getCurrent_level() {
        return current_level;
    }

    public void run(){
        if(!this.destinationsList.isEmpty()) {
            if (this.current_level < this.destinationsList.getFirst())
                Move(Actions.GO_UP);
            else if (this.current_level == this.destinationsList.getFirst())
                Move(Actions.STOP_NEXT);
            else
                Move(Actions.GO_DOWN);
        }
    }

    public void Move(int action){
        switch(action){
            case Actions.GO_UP : {
                if (this.current_level < 6) {
                    this.current_level++;
                    this.current_state.UpdateState(1);
                }
                else
                    this.current_state.UpdateState(0);
            } break;
            case Actions.STOP_NEXT: {
                this.destinationsList.remove();
                this.current_state.UpdateState(0);
            }
            case Actions.GO_DOWN : {
                if(this.current_level > 0) {
                    this.current_level--;
                    this.current_state.UpdateState(-1);
                }
                else
                    this.current_state.UpdateState(0);
            }
        }
    }

    public void addDestination(int destination){
        this.destinationsList.add(destination);
    }


}
