package geni_logiciel_projet1;

import java.util.Collections;
import java.util.LinkedList;

public class SorterDestinationList {


    /**
     * Tri la liste en fonction de la direction actuelle de sorte que l'assenceur n'interromp pas un mouvement en cours.
     * C'est à dire que si la cabine monte, elle va monter au maximum de ses destination avant d'executer des commandes qui nécessite de descendre.
     * @param destinations_list liste de toutes les destinations.
     * @param action l'action en cours
     * @return La liste initiale triée
     */
    static LinkedList<Destination> sort(LinkedList<Destination> destinations_list, int action) {

        if (destinations_list.isEmpty() || destinations_list.size() == 1) return destinations_list;

        //Initialisation des listes
        LinkedList<Destination> destinations_listFinal = new LinkedList<>();
        LinkedList<Destination> destinations_listUp = new LinkedList<>();
        LinkedList<Destination> destinations_listDown = new LinkedList<>();

        //Séparation des listes par rapport à leurs directions
        for (Destination destination : destinations_list) {
            if (destination.getDirection() == Constante.Actions.GO_UP) {
                destinations_listUp.add(destination);
            }
            else destinations_listDown.add(destination);
        }

        //Trier les deux listes
        Collections.sort(destinations_listUp);
        Collections.sort(destinations_listDown, Collections.reverseOrder());


        //Fusionner les listes dans le bon ordre
        if (action == Constante.Actions.GO_UP) {
            destinations_listFinal.addAll(destinations_listUp);
            destinations_listFinal.addAll(destinations_listDown);
        }
        else {
            destinations_listFinal.addAll(destinations_listDown);
            destinations_listFinal.addAll(destinations_listUp);
        }

        return destinations_listFinal;
    }

}
