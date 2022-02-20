package server.logic;

import server.Server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PlayerHandler extends Thread {

    private char mark;
    private PlayerHandler opponentPlayerHandler;
    private Game currentGame;
    private Socket socket;
    private Server server;
    private Profile profile;
    private String authToken;

    private Scanner input;
    private PrintWriter output;

    public PlayerHandler(Socket socket, Server server) {
        this.server = server;
        this.socket = socket;
        try {
            this.input = new Scanner(socket.getInputStream());
            this.output = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (!isInterrupted()) {
            String[] request = input.nextLine().split(":");

            if (request[0].equals("login")) { // login request
                System.out.println("message recieved:" + request[0]);
                int result = server.login(this, request[1], request[2]);
                switch (result) {
                    case -2: {
                        output.println("login:invalid entry");
                        break;
                    }
                    case -1: {
                        output.println("login:no account");
                        break;
                    }
                    case 0: {
                        output.println("login:wrong password");
                        break;
                    }
                    case 1: {
                        output.println("login:successful:" + authToken);
                        break;
                    }
                }
            } else if (request[0].equals("signUp")) { // signUp request
                int result = server.signUp(this, request[1], request[2]);
                switch (result) {
                    case -1: {
                        output.println("login:invalid entry");
                        break;
                    }
                    case 0: {
                        output.println("login:account already exists");
                        break;
                    }
                    case 1: {
                        output.println("login:successful:" + authToken);
                        break;
                    }
                }

            } else if (request[0].equals(authToken)) { // any other request
                handleRequests(request);
            }
        }
    }

    private void handleRequests(String[] request) {
        switch (request[1]) {
            case "start game": {
                server.requestGame(authToken);
                break;
            }
            case "selected": {
                int i = Integer.parseInt(request[2]), j = Integer.parseInt(request[3]);
                handleSelectRequest(i, j);
                break;
            }
            case "exit": {
                server.exitPlayer(authToken);

                break;
            }
            case "score board": {
                String s=server.requestScoreBoard();
                System.out.println(s);
                output.println(authToken + ":score board");
                output.println(s);
                break;
            } case "profile info":{
                String s=profile.getUsername()+":"+profile.getWins()+":"+profile.getLose()+":"+profile.getScore();
                output.println(authToken+":profile info:"+s);
                break;
            }

        }
    }

    private void handleSelectRequest(int i, int j) {
        switch (currentGame.selectCell(this, i, j)) {
            case -1: {
                output.println(authToken + ":not turn:0");
                break;
            }
            case 0: {
                output.println(authToken + ":already selected:1");
                break;
            }
            case 1: {
                output.println(authToken + ":done:" + 0);
                opponentPlayerHandler.gotTurn();
                opponentPlayerHandler.opponentSelected(i, j);

                int a = currentGame.isWinner(this);
                if (a != 0) {
                    gameOver(a);
                    opponentPlayerHandler.gameOver(-a);
                    break;
                } else if (currentGame.isBoardFull()) {
                    gameOver(0);
                    opponentPlayerHandler.gameOver(0);
                    break;
                }
                break;
            }
        }

    }

    public void gameOver(int a) {
        output.println(authToken + ":game over:" + a);
        if (a == 1) {
            profile.setWins(profile.getWins() + 1);
        } else if (a == -1) {
            profile.setLose(profile.getLose() + 1);
        }
        server.saveProfile(authToken);
        server.terminateGame(currentGame);
        this.currentGame = null;
    }

    public void gotTurn() {
        output.println(authToken + ":turn:1");
    }

    public void opponentSelected(int i, int j) {
        output.println(authToken + ":opponent selected:" + i + ":" + j);
    }

    // getters and setters
    public char getMark() {
        return mark;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
        output.println(authToken + ":game started:" + opponentPlayerHandler.getProfile().getUsername() + ":" + mark + ":" +
                (currentGame.getCurrentPlayerHandler() == this ? 1 : 0));
    }

    public void setMark(char mark) {
        this.mark = mark;
    }

    public PlayerHandler getOpponentPlayerHandler() {
        return opponentPlayerHandler;
    }

    public void setOpponentPlayerHandler(PlayerHandler opponentPlayerHandler) {
        this.opponentPlayerHandler = opponentPlayerHandler;
    }

    public Game getCurrentGame() {
        return currentGame;
    }


    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
