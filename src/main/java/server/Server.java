package server;

import server.Util.AuthTokenGenerator;
import server.Util.JSONHandler;
import server.logic.Game;
import server.logic.PlayerHandler;
import server.logic.Profile;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server extends Thread {
    private int serverPort;
    private ServerSocket serverSocket;
    private HashMap<String, PlayerHandler> players;
    private ArrayList<String> waitingList;
    private ArrayList<Game> runningGames;

    private JSONHandler jsonHandler;
    private AuthTokenGenerator authTokenGenerator;

    public Server(int serverPort) {
        this.serverPort = serverPort;
        try {
            this.serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {

        }
        this.players = new HashMap<>();
        this.waitingList = new ArrayList<>();
        this.runningGames = new ArrayList<>();
        this.jsonHandler = new JSONHandler();
        this.authTokenGenerator = new AuthTokenGenerator();
    }

    @Override
    public void run() {

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                PlayerHandler playerHandler = new PlayerHandler(socket, this);
                playerHandler.start();
            } catch (IOException e) {

            }

        }

    }

    // in login and sign up , player must be added to the hashmap
    public synchronized int login(PlayerHandler playerHandler, String username, String password) {
//-1 for not existing, 0 for wrong password , 1 if successful
        if (!jsonHandler.getProfilesName().contains(username + ".JSON"))
            return -1;
        Profile profile = jsonHandler.getProfile(username + ".JSON");
        if (!profile.getPassword().equals(password))
            return 0;
        playerHandler.setProfile(profile);
        playerHandler.setAuthToken(authTokenGenerator.getAuthToken());
        players.put(playerHandler.getAuthToken(), playerHandler);
        return 1;
    }

    public synchronized int signUp(PlayerHandler playerHandler, String username, String password) {
//  0 for account already exist , 1 if successful
        if (jsonHandler.getProfilesName().contains(username + ".JSON"))
            return 0;
        Profile profile = new Profile(username, password);
        jsonHandler.saveProfile(profile);
        playerHandler.setProfile(profile);
        playerHandler.setAuthToken(authTokenGenerator.getAuthToken());
        players.put(playerHandler.getAuthToken(), playerHandler);
        return 1;
    }

    public synchronized void requestGame(String authToken) {
        waitingList.add(authToken);
        if (waitingList.size() > 1) {
            PlayerHandler player1 = players.get(waitingList.get(0)), player2 = players.get(waitingList.get(1));
            Game game = new Game(player1, player2);
            runningGames.add(game);
            waitingList.remove(0);
            waitingList.remove(0);
        }

    }

    public synchronized void saveProfile(String authToken) {
        jsonHandler.saveProfile(players.get(authToken).getProfile());

    }

    public synchronized void terminateGame(Game game) {
        runningGames.remove(game);
    }

    public synchronized void exitPlayer(String authToken) {
        players.get(authToken).interrupt();
        players.remove(authToken);
    }

    public synchronized String requestScoreBoard() {
        ArrayList<String> roughScores =(ArrayList<String>) jsonHandler.getScores().clone();
        ArrayList<String> newScores=new ArrayList<>();
        for (int i = roughScores.size()-1; i >=0 ; i--) {
            String[] record=roughScores.get(i).split("/");
            String newRecord=roughScores.get(i);
            boolean isOnline=false;
            for(PlayerHandler playerHandler:players.values()){
                if(playerHandler.getProfile().getUsername().equals(record[1])) {
                    isOnline = true;
                    break;
                }
            }
            newRecord="\""+newRecord+"/"+(isOnline?"online":"offline")+"\"";
            newScores.add(newRecord);
        }
        System.out.println(newScores.toString());
        return newScores.toString();
    }

    public synchronized String requestProfile() {

        return null;
    }
}
