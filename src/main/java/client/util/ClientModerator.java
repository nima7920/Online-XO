package client.util;

import client.gui.GameScreen;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientModerator extends Thread {

    private Socket socket;
    private String authToken;
    private Scanner in;
    private PrintWriter out;
    private GameScreen gameScreen;
    private JSONReader jsonReader;

    public ClientModerator(String serverIP, int serverPort) {
        try {
            this.socket = new Socket(serverIP, serverPort);

        } catch (IOException e) {

        }
        jsonReader = new JSONReader();
        gameScreen = new GameScreen(this);
    }

    @Override
    public void run() {
        initTransmitters();
        gameScreen.gotoLoginMenu();
        // responses from server will be handled here
        while (in.hasNextLine()) {

            String[] response = in.nextLine().split(":");

            System.out.println("response recieved:" + response[0]);

            if (response[0].equals("login")) { // login response
                handleLogin(response);
//                gameScreen.showMessage("login", "login");

            } else if (response[0].equals("signUp")) { // sign up response
                handleSignUp(response);
//                gameScreen.showMessage("signUp", "signUp");
            } else if (response[0].equals(authToken)) { // other responses
                handleResponses(response);
            }
        }
    }

    private void initTransmitters() {
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // methods for responses
    private void handleLogin(String[] response) {

        switch (response[1]) {
            case "wrong password": {
                gameScreen.showMessage("Error", "Wrong password");
                break;
            }
            case "no account": {
                gameScreen.showMessage("Error", "Account doesn't exist");
                break;
            }
            case "successful": {
                this.authToken = response[2];
                gameScreen.gotoMainMenu();
            }

        }
    }

    private void handleSignUp(String[] response) {

        switch (response[1]) {
            case "account already exists": {
                gameScreen.showMessage("Error", "Account already exists");
                break;
            }
            case "successful": {
                this.authToken = response[2];
                gameScreen.gotoMainMenu();
            }

        }
    }

    private void handleResponses(String[] response) {
        switch (response[1]) {
            case "game started": {
                gameScreen.startGame(response[2], response[3], response[4].equals("1"));
                break;
            }
            case "done": {
                gameScreen.selectSquare();
                gameScreen.updateLabels(false);
                break;
            }
            case "opponent selected": {
                gameScreen.opponentSelected(Integer.parseInt(response[2]), Integer.parseInt(response[3]));
                break;
            }
            case "already selected": {
                gameScreen.showMessage("Error", "Square already selected");
                break;
            }
            case "not turn": {
                gameScreen.showMessage("Error", "Not your turn");
                break;
            }
            case "turn": {
                gameScreen.updateLabels(response[2].equals("1"));
                break;
            }
            case "game over": { // game over case
                int a = Integer.parseInt(response[2]);
                if (a == 1)
                    gameScreen.showMessage("Game Over", "You Won!");
                else if (a == 0)
                    gameScreen.showMessage("Game Over", "Draw!");
                else if (a == -1)
                    gameScreen.showMessage("Game Over", "You Lost!");
                gameScreen.gotoMainMenu();
                break;
            }
            case "score board": {
                gameScreen.updateScoreBoard(jsonReader.getScoreBoardFromJSON(in.nextLine()));
                break;
            } case "profile info":{
                gameScreen.refreshInfoPanel(response[2],Integer.parseInt(response[3]),Integer.parseInt(response[4]),Integer.parseInt(response[5]));
                break;
            }
        }

    }

    // methods for requests
    public void loginRequest(String username, String password) {
        System.out.println("message sent:login");
        out.println("login:" + username + ":" + password);
    }

    public void signUpRequest(String username, String password) {
        out.println("signUp:" + username + ":" + password);
    }

    // main menu
    public void gotoPlayRequest() {
        gameScreen.gotoPlayMenu();
        out.println(authToken + ":start game");
    }

    public void gotoMainRequest() {
        gameScreen.gotoMainMenu();
    }

    public void gotoScoreBoardRequest() {
        gameScreen.gotoScoreBoardMenu();
        out.println(authToken + ":score board");
    }

    public void gotoInfoRequest() {
        gameScreen.gotoInfoMenu();
        out.println(authToken + ":profile info");
    }

    public void exitRequest() {
        out.println(authToken + ":exit");
        System.exit(0);
    }

    // play menu
    public void selectSquare(int i, int j) {
        out.println(authToken + ":selected:" + i + ":" + j);
    }
}
