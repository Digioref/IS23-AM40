package it.polimi.ingsw.am40.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer implements Runnable {

    private String hostName;
    private int portNumber;
    private ServerSocket serverSocket;

    public GameServer(String hostName, int portNumber) throws IOException {
        this.hostName = hostName;
        this.portNumber = portNumber;
        serverSocket = new ServerSocket(portNumber);
    }

    @Override
    public void run() {
        System.out.println("GameServer started on: " + portNumber);
        while (true) {
            System.out.println("Accepting...");
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Accepted!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
