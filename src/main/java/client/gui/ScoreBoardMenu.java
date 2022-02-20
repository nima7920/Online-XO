package client.gui;

import client.util.ClientModerator;
import client.util.ConfigLoader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreBoardMenu extends GameMenu {

    private Actions actions;
    private ClientModerator clientModerator;

    private JTable playersTable;
    private JScrollPane playersPane;
    private JButton backButton;

    private String[] playersRow = new String[]{ "Score", "Name", "State"};

    public ScoreBoardMenu(ClientModerator clientModerator) {
        this.clientModerator = clientModerator;
        actions = new Actions();
        configLoader = new ConfigLoader("src//main//java//client//configs//scoreBoardMenu.properties");
        initMenu();
    }

    private void initMenu() {
        setBounds(configLoader.getBounds("menuBounds"));
        setLayout(null);
        initButtons();

    }

    private void initButtons() {
        backButton = new JButton("back");
        backButton.setBounds(configLoader.getBounds("backButton_bounds"));
        backButton.addActionListener(actions);
        add(backButton);

    }

    public void update(String[][] tableContent) {
        playersTable = new JTable(tableContent, playersRow);
        playersPane = new JScrollPane();
        playersTable.setBorder(BorderFactory.createEtchedBorder());
        playersTable.setEnabled(false);
        playersPane.setBounds(configLoader.getBounds("playersPane_bounds"));
        playersPane.setViewportView(playersTable);
        add(playersPane);
    }

    private class Actions implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == backButton) {
                clientModerator.gotoMainRequest();
            }
        }
    }
}
