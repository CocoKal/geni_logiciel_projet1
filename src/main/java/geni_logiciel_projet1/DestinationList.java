package geni_logiciel_projet1;

import java.util.LinkedList;

public class DestinationList {

    private LinkedList<Destination> destinations_list;

    public DestinationList () {
        destinations_list = new LinkedList<>();
    }

    public LinkedList<Destination> getDestinations_list() {
        return destinations_list;
    }

    public Destination getFirstDestination () {
        return destinations_list.getFirst();
    }

    public void setDestinations_list(LinkedList<Destination> destinations_list) {
        this.destinations_list = destinations_list;
    }

    public void removeFirst() {
        destinations_list.removeFirst();
    }

    public boolean isEmpty() {
        return destinations_list.isEmpty();
    }

    public void addDestination(Destination destination) {
        destinations_list.add(destination);
    }

    void sort(int action) {
        setDestinations_list(SorterDestinationList.sort(destinations_list, action));
    }
}
