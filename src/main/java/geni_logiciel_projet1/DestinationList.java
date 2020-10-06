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

    public void addDestination(Destination destination) {
        destinations_list.add(destination);
    }
}
