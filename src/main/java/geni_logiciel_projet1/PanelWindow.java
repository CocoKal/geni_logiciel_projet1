package geni_logiciel_projet1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.LayoutStyle.ComponentPlacement.RELATED;

class PanelWindow extends JFrame {

    private JLabel label_current_floor = new JLabel("0");
    private JLabel label_current_move = new JLabel("Waiting");
    private JSpinner spinner;

    PanelWindow(){
        super();
        build();
    }

    private void build(){
        setTitle("Elevator Panel");
        setSize(300,450);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildContentPane();
    }

    private void buildContentPane() {

        /*Setup de tous les composants*/
            //Display
        label_current_floor.setFont(new Font("Verdana", Font.PLAIN, 50));
        label_current_move = new JLabel("WAITING");

            //Intern commands
        //JLabel label_intern = new JLabel("Commandes internes");
        JButton button_1 = new JButton("1");
        JButton button_2 = new JButton("2");
        JButton button_3 = new JButton("3");
        JButton button_4 = new JButton("4");
        JButton button_5 = new JButton("5");
        JButton button_6 = new JButton("6");
        JButton button_rc = new JButton("0");
        JButton button_au = new JButton("AU");

            //Externs commands
        //JLabel label_extern = new JLabel("Commandes externes");
        SpinnerNumberModel model1 = new SpinnerNumberModel(0, 0, 6, 1);
        spinner = new JSpinner(model1);
        JButton button_up = new JButton("UP");
        JButton button_down = new JButton("DOWN");

        /*Ajout des ActionListener*/
            //Commandes internes
        button_rc.addActionListener(listenerForButtonFloor());
        button_1.addActionListener(listenerForButtonFloor());
        button_2.addActionListener(listenerForButtonFloor());
        button_3.addActionListener(listenerForButtonFloor());
        button_4.addActionListener(listenerForButtonFloor());
        button_5.addActionListener(listenerForButtonFloor());
        button_6.addActionListener(listenerForButtonFloor());
            //Commandes externes
        button_up.addActionListener(listenerForExternCommand());
        button_down.addActionListener(listenerForExternCommand());


        /*Mise en layout des composants*/

            //Panel d'affichage étage+mouvement
                //Initialisation layout et panel
        FlowLayout layout_display = new FlowLayout();
        JPanel panel_display = new JPanel();
        panel_display.setLayout(layout_display);
                //Ajout des composants au panel
        panel_display.add(label_current_floor);
        panel_display.add(label_current_move);

            //Panel de commande interne

                //Initialisation layout et panel
        GridLayout layout_commande_intern = new GridLayout(3,3);
        JPanel panel_command_intern = new JPanel();
        panel_command_intern.setLayout(layout_commande_intern);
                //Ajout des composants au panel
        panel_command_intern.add(button_4);
        panel_command_intern.add(button_5);
        panel_command_intern.add(button_6);
        panel_command_intern.add(button_1);
        panel_command_intern.add(button_2);
        panel_command_intern.add(button_3);
        panel_command_intern.add(button_rc);
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
                    .addGroup(main_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(panel_display)
                            .addComponent(panel_command_intern, 0 , 250, 300)
                            .addComponent(panel_command_extern)
                    )
        );

        main_layout.setVerticalGroup(
                main_layout.createSequentialGroup()
                    .addComponent(panel_display, 0 , 50, 75)
                    .addComponent(panel_command_intern, 0 , 200, 250)
                        .addPreferredGap(RELATED,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_command_extern, 0 , 30, 50)
        );

        /*Ajout du panel general à la fenetre*/
        add(main_panel);

    }

    private void updateLabel(JLabel label, String newString){
        label.setText(newString);
    }

    void updateLabelFloor(String newString) {
        updateLabel(label_current_floor, newString);
    }

    void updateLabelState(int state) {

        switch (state) {
            case Constante.State.GO_DOWN:
                updateLabel(label_current_move,"DOWN");
                break;
            case Constante.State.GO_UP:
                updateLabel(label_current_move,"UP");
                break;
            case Constante.State.UNLOADING:
                updateLabel(label_current_move,"UNLOADING");
                break;
            case Constante.State.WAITING:
                updateLabel(label_current_move,"WAITING");
                break;
        }

    }

    private ActionListener listenerForButtonFloor() {
        return e -> {
            JButton source = (JButton) e.getSource();
            App.addDestination(Integer.parseInt(source.getText()));
        };
    }

    private ActionListener listenerForExternCommand() {
        return e -> {
            int value = (int) spinner.getValue();
            if (value < 7 && value > -1) App.addDestination(value);
        };
    }
}
