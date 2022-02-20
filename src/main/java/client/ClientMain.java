package client;

import client.gui.GameScreen;
import client.util.ClientModerator;
import client.util.ConfigLoader;

public class ClientMain {

    private String serverIP = "localhost";
    private int serverPort = 8000;

    public static void main(String[] args) {
        new ClientMain(args);
    }

    public ClientMain(String[] args) {
//        GameScreen gameScreen=new GameScreen(new ClientModerator(serverIP,serverPort));

        if (args.length >= 1) {
            ConfigLoader configLoader = new ConfigLoader(args[0]);
            serverIP = configLoader.getString("serverIP");
            serverPort = configLoader.getInt("serverPort");
        }
        ClientModerator clientModerator=new ClientModerator(serverIP,serverPort);
        clientModerator.start();
    }
}
