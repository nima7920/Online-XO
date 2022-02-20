package server;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ServerMain {

    private int serverPort = 8000;

    public static void main(String[] args) {
        new ServerMain(args);
    }

    public ServerMain(String[] args) {
        if (args.length >= 1) {
            serverPort = getPort(args[0]);
        }
        Server server=new Server(serverPort);
        server.start();
    }

    private int getPort(String configPath) {
        Properties configFile=new Properties();
        try {
            configFile.load(new FileReader(configPath));
            return Integer.parseInt(configFile.getProperty("serverPort"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 8000;
    }
}
