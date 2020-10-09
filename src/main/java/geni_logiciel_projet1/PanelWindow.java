package geni_logiciel_projet1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.LayoutStyle.ComponentPlacement.RELATED;

class PanelWindow extends JFrame {

    private JLabel label_current_floor = new JLabel("0");
    private JLabel label_current_state = new JLabel("Waiting");
    private JSpinner spinner;
    private JButton[] button_array = new JButton[7];
    private boolean emegercy_stop = false;

    PanelWindow(){
        super();
        build();
    }

    /**
     * Initialisation de la fenêtre.
     */
    private void build(){
        setTitle("Elevator Panel");
        setSize(300,500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildContentPane();
    }

    /**
     * Construction des éléments de la fenêtre.
     */
    private void buildContentPane() {

        /*Setup de tous les composants*/
            //Display
        label_current_floor.setFont(new Font("Verdana", Font.PLAIN, 50));
        label_current_state = new JLabel("WAITING");

            //Intern commands
        JLabel label_intern = new JLabel("Commandes internes");

        for (int i = 0; i < button_array.length ; i++) {
            button_array[i] = new JButton(""+i);
            changeButtonColor(button_array[i], Color.GRAY);
        }

        JButton button_au = new JButton("AU");

            //Externs commands
        JLabel label_extern = new JLabel("Commandes externes");
        SpinnerNumberModel model1 = new SpinnerNumberModel(0, 0, 6, 1);
        spinner = new JSpinner(model1);
        JButton button_up = new JButton("UP");
        JButton button_down = new JButton("DOWN");

        /*Ajout des ActionListener*/
            //Commandes internes
        button_array[0].addActionListener(listenerForButtonFloor());
        button_array[1].addActionListener(listenerForButtonFloor());
        button_array[2].addActionListener(listenerForButtonFloor());
        button_array[3].addActionListener(listenerForButtonFloor());
        button_array[4].addActionListener(listenerForButtonFloor());
        button_array[5].addActionListener(listenerForButtonFloor());
        button_array[6].addActionListener(listenerForButtonFloor());
        button_au.addActionListener(listenerForEmergencyStop());
            //Commandes externes
        button_up.addActionListener(listenerForExternCommandUp());
        button_down.addActionListener(listenerForExternCommandDown());


        /*Mise en layout des composants*/

            //Panel d'affichage étage+mouvement
                //Initialisation layout et panel
        FlowLayout layout_display = new FlowLayout();
        JPanel panel_display = new JPanel();
        panel_display.setLayout(layout_display);
                //Ajout des composants au panel
        panel_display.add(label_current_floor);
        panel_display.add(label_current_state);

            //Panel de commande interne

                //Initialisation layout et panel
        GridLayout layout_commande_intern = new GridLayout(3,3);
        JPanel panel_command_intern = new JPanel();
        panel_command_intern.setLayout(layout_commande_intern);
                //Ajout des composants au panel
        panel_command_intern.add(button_array[4]);
        panel_command_intern.add(button_array[5]);
        panel_command_intern.add(button_array[6]);
        panel_command_intern.add(button_array[1]);
        panel_command_intern.add(button_array[2]);
        panel_command_intern.add(button_array[3]);
        panel_command_intern.add(button_array[0]);
        panel_command_intern.add(new JLabel());
        panel_command_intern.add(button_au);

            //Panel de commande externe
                //Initialisation layout et panel
        FlowLayout layout_command_extern = new FlowLayout();
        JPanel panel_command_extern = new JPanel();
        panel_command_extern.setLayout(layout_command_extern);
                //Ajout des composants au panel
        panel_command_extern.add(spinner);
        panel_command_extern.add(button_up);
        panel_command_extern.add(button_down);

            //Panel général
        JPanel main_panel = new JPanel();
        GroupLayout main_layout = new GroupLayout(main_panel);
        main_panel.setLayout(main_layout);

        main_layout.setHorizontalGroup(
                main_layout.createSequentialGroup()
                    .addGroup(main_layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(panel_display)
                            .addComponent(label_intern)
                            .addComponent(panel_command_intern, 0 , 250, 300)
                            .addComponent(label_extern)
                            .addComponent(panel_command_extern)
                    )
        );

        main_layout.setVerticalGroup(
                main_layout.createSequentialGroup()
                    .addComponent(panel_display, 0 , 50, 75)
                    .addComponent(label_intern, 0 , 20, 25)
                    .addComponent(panel_command_intern, 0 , 200, 250)
                        .addPreferredGap(RELATED,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_extern, 0 , 20, 25)
                    .addComponent(panel_command_extern, 0 , 30, 50)
        );

        /*Ajout du panel general à la fenetre*/
        add(main_panel);

    }

    public void setEmegercy_stopTrue() {
        this.emegercy_stop = true;
        resetAllButton();
    }

    private void updateLabel(JLabel label, String newString){
        label.setText(newString);
    }

    void updateLabelFloor(int newString) {
        updateLabel(label_current_floor, String.valueOf(newString));
    }

    void updateLabelState(int state) {
        if (!emegercy_stop) {

            switch (state) {
                case Constante.State.MOVING:
                    updateLabel(label_current_state, "MOVING");
                    break;
                case Constante.State.UNLOADING:
                    updateLabel(label_current_state, "UNLOADING");
                    break;
                case Constante.State.WAITING:
                    updateLabel(label_current_state, "WAITING");
                    break;
            }
        }
        else if (!label_current_state.getText().equals("EMERGENCY STOP"))
            updateLabel(label_current_state, "EMERGENCY STOP");

    }

    /**
     *
     * @return Retourne un ActionListener correspondant à l'arrêt d'urgence.
     */
    private ActionListener listenerForEmergencyStop() {
        return e -> {
            App.emergencyStop();
        };
    }

    /**
     *
     * @return Retourne un ActionListener correspondant à une demande sur un étage.
     */
    private ActionListener listenerForButtonFloor() {
        return e -> {
            JButton source = (JButton) e.getSource();
            App.addDestination(Integer.parseInt(source.getText()));
        };
    }

    /**
     *
     * @return Retourne un ActionListener correspondant à une commande externe vers le haut en fonction de la valeur du spinner.
     */
    private ActionListener listenerForExternCommandUp() {
        return e -> {
            int value = (int) spinner.getValue();
            Destination destination = new Destination(value, Constante.Actions.GO_UP);
            if (value < 7 && value > -1) App.addDestination(destination);
        };
    }

    /**
     *
     * @return Retourne un ActionListener correspondant à une commande externe vers le bas en fonction de la valeur du spinner.
     */
    private ActionListener listenerForExternCommandDown() {
        return e -> {
            int value = (int) spinner.getValue();
            Destination destination = new Destination(value, Constante.Actions.GO_DOWN);
            if (value < 7 && value > -1) App.addDestination(destination);
        };
    }

    /**
     * Change la couleur des boutons en fonction de la liste.
     * @param listDestination
     */
    void turnOnButton(ArrayList<Integer> listDestination) {
        for (Integer destination : listDestination) {
            changeButtonColor(button_array[destination], Color.ORANGE);
        }
    }

    /**
     * Met tous les bouttons sur la couleur de base.
     */
    void resetAllButton() {
        for(int i = 0; i < button_array.length; i++) {
            changeButtonColor(button_array[i], Color.GRAY);
        }
    }

    /**
     * Change la couleur d'un boutton.
     * @param button Un boutton de l'objet JButton.
     * @param color Une couleur de l'objet Color.
     */
    private void changeButtonColor(JButton button, Color color) {
        button.setBackground(color);
    }
}
