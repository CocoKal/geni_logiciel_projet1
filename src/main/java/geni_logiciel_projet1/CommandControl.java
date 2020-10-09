package geni_logiciel_projet1;

import java.util.ArrayList;

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

    /**
     * Calcul la direction que la cabine est en train d'emprunter.
     * @return
     */
    public int getDirection() {
        if (!destinationList.isEmpty()) {
            return destinationList.getFirstDestination().getDirection();
        }
        else {
            return Constante.Actions.WAITING;
        }
    }


    public void setCurrent_floor(int current_floor) {
        this.current_floor = current_floor;
    }

    /**
     * Fait une vérification afin de savoir quand activer l'arrêt au prochain étage.
     */
    void checkForStopNext() {
        int value = destinationList.getFirstDestination().getDestination_floor() - current_floor;
        if (value == 0 || value == -0) {
            App.stopNext();
        }
    }

    public void setState(int state) {
        this.state = state;
    }

    /**
     * Supprimme la derniere action.
     */
    void deleteLastDestination() {
        destinationList.removeFirst();
    }


    /**
     * Met à jour l'action de la partie opérative.
     */
    public void updateActionOperativ() {
            if (!destinationList.isEmpty()) {
                int action = getAction(destinationList.getFirstDestination().getDestination_floor());
                App.updateActionOperativ(action);
            }
            else App.updateActionOperativ(Constante.Actions.WAITING);
    }

    /**
     * Vérifie si l'état est sur UNLOADING.
     * @return
     */
    boolean checkForUnloading() {
        return state == Constante.State.UNLOADING;
    }

    /**
     * Donne l'action en fonction du parametre.
     * @param destination Integer de l'étage de destination
     * @return -1 si l'étage courant est plus grand que la destination, 0 si égaux, 1 si l'étage courant est plus petit que la destination
     */
    public int getAction(int destination) {
        return Integer.compare(destination, current_floor);
    }

    /**
     * Ajoute une destination à la liste et tri la liste.
     * @param destination
     */
    public void addDestination(Destination destination) {
        if (destination.getDestination_floor() != current_floor) {
            destinationList.addDestination(destination);
            destinationList.sort(getDirection());
            checkForFirstAction();
        }
    }


    /**
     * Ajoute une destination en déterminant la direction avant.
     * @param destination
     */
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

    ArrayList<Integer> getDestinationOnList() {
        ArrayList<Integer> list = new ArrayList<>();
        for (Destination destination : destinationList.getDestinations_list()) {
            list.add(destination.getDestination_floor());
        }
        return list;
    }

    private void checkForFirstAction() {
        if (state == Constante.State.WAITING){
            App.updateActionOperativ(destinationList.getFirstDestination().getDirection());
        }
    }

}
