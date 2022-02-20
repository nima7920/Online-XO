package client.gui;

import client.util.ClientModerator;
import client.util.ConfigLoader;

import javax.swing.*;
import java.awt.event.*;

public class LoginMenu extends GameMenu {
    private ClientModerator clientModerator;

    private JButton loginButton, createButton;
    private JTextField userNameField, passwordField;
    private JLabel welcomeLabel, usernameLabel, passwordLabel;
    private Actions actions;

    public LoginMenu(ClientModerator clientModerator) {
        this.clientModerator = clientModerator;
        // setting buttons:
        configLoader = new ConfigLoader("src//main//java//client//configs//loginMenu.properties");
        actions = new Actions();
        initMenu();
    }

    private void initMenu() {
        setBounds(configLoader.getBounds("menuBounds"));
        setLayout(null);
        initButtons();
        initLabels();
        initTextFields();

        requestFocus();
    }

    private void initButtons() {
        loginButton = new JButton("Login");
        loginButton.setBounds(configLoader.getBounds("loginButton_bounds"));
        loginButton.addActionListener(actions);
        add(loginButton);

        createButton = new JButton("Sign up");
        createButton.setBounds(configLoader.getBounds("createButton_bounds"));
        createButton.addActionListener(actions);
        add(createButton);

    }

    private void initLabels() {
        welcomeLabel = new JLabel("Welcome to Tic Tac Toe!!");
        welcomeLabel.setBounds(configLoader.getBounds("welcomeLabel_bounds"));
        welcomeLabel.setFont(configLoader.getFont("welcomeLabel_font"));
        add(welcomeLabel);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(configLoader.getBounds("usernameLabel_bounds"));
        usernameLabel.setFont(configLoader.getFont("usernameLabel_font"));
        add(usernameLabel);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(configLoader.getBounds("passwordLabel_bounds"));
        passwordLabel.setFont(configLoader.getFont("passwordLabel_font"));
        add(passwordLabel);

    }

    private void initTextFields() {
        userNameField = new JTextField();
        userNameField.setBounds(configLoader.getBounds("usernameField_bounds"));
        add(userNameField);

        passwordField = new JTextField();
        passwordField.setBounds(configLoader.getBounds("passwordField_bounds"));
        add(passwordField);

    }

    private class Actions implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == loginButton) {
                if(userNameField.getText().trim().equals("") || passwordField.getText().trim().equals("")){
                    return;
                }
                clientModerator.loginRequest(userNameField.getText().trim(), passwordField.getText().trim());

            } else if (actionEvent.getSource() == createButton) {
                if(userNameField.getText().trim().equals("") || passwordField.getText().trim().equals("")){
                    return;
                }
                clientModerator.signUpRequest(userNameField.getText().trim(), passwordField.getText().trim());
            }

        }
    }
}