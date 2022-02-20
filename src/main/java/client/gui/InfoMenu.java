package client.gui;

import client.util.ClientModerator;
import client.util.ConfigLoader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoMenu extends GameMenu {

    private Actions actions;
    private ClientModerator clientModerator;

    private JButton backButton;
    private JLabel usernameLabel, winsLabel, losesLabel, scoreLabel;

    public InfoMenu(ClientModerator clientModerator) {
        this.clientModerator = clientModerator;
        actions = new Actions();
        configLoader = new ConfigLoader("src//main//java//client//configs//infoMenu.properties");
        initMenu();
    }

    private void initMenu() {
        setBounds(configLoader.getBounds("menuBounds"));
        setLayout(null);
        initComponents();

    }

    private void initComponents() {
        backButton = new JButton("back");
        backButton.setBounds(configLoader.getBounds("backButton_bounds"));
        backButton.addActionListener(actions);
        add(backButton);

        usernameLabel = new JLabel();
        usernameLabel.setBounds(configLoader.getBounds("usernameLabel_bounds"));
        add(usernameLabel);

        winsLabel = new JLabel();
        winsLabel.setBounds(configLoader.getBounds("winsLabel_bounds"));
        add(winsLabel);

        losesLabel = new JLabel();
        losesLabel.setBounds(configLoader.getBounds("losesLabel_bounds"));
        add(losesLabel);

        scoreLabel = new JLabel();
        scoreLabel.setBounds(configLoader.getBounds("scoreLabel_bounds"));
        add(scoreLabel);
    }

    public void refresh(String username, int wins, int loses, int score) {
        usernameLabel.setText("Username: " + username);
        winsLabel.setText("Wins: " + wins);
        losesLabel.setText("Loses: " + loses);
        scoreLabel.setText("Score: " + score);
//        infoLabel.setText("username: " + username + "\n" + "wins: " + wins + "\n" + "loses: " + loses + "\n" + "score: " + score);

    }

    private class Actions implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            clientModerator.gotoMainRequest();
        }
    }
}
