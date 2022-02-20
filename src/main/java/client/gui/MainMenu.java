package client.gui;

import client.util.ClientModerator;
import client.util.ConfigLoader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends GameMenu {

    private ClientModerator clientModerator;
    private Actions actions;

    private JButton multiPlayButton, scoreBoardButton, infoButton, exitButton;


    public MainMenu(ClientModerator clientModerator) {
        this.clientModerator = clientModerator;
        actions = new Actions();
        configLoader = new ConfigLoader("src//main//java//client//configs//mainMenu.properties");
        initMenu();
    }

    private void initMenu() {
        setLayout(null);
        setBounds(configLoader.getBounds("menuBounds"));
        initButtons();

    }

    private void initButtons() {
        multiPlayButton = new JButton("Multi Play");
        multiPlayButton.setBounds(configLoader.getBounds("multiPlayButton_bounds"));
        multiPlayButton.addActionListener(actions);
        add(multiPlayButton);

        scoreBoardButton = new JButton("Score Board");
        scoreBoardButton.setBounds(configLoader.getBounds("scoreBoardButton_bounds"));
        scoreBoardButton.addActionListener(actions);
        add(scoreBoardButton);

        infoButton = new JButton("Info");
        infoButton.setBounds(configLoader.getBounds("infoButton_bounds"));
        infoButton.addActionListener(actions);
        add(infoButton);

        exitButton = new JButton("exit");
        exitButton.setBounds(configLoader.getBounds("exitButton_bounds"));
        exitButton.addActionListener(actions);
        add(exitButton);

    }

    private class Actions implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == multiPlayButton) {
                clientModerator.gotoPlayRequest();
            } else if (actionEvent.getSource() == scoreBoardButton) {
                clientModerator.gotoScoreBoardRequest();
            } else if (actionEvent.getSource() == infoButton) {
                clientModerator.gotoInfoRequest();
            } else if (actionEvent.getSource() == exitButton) {
                clientModerator.exitRequest();
            }
        }
    }
}
