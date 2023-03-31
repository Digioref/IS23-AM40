package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Controller.Lobby;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer implements Runnable {

    private String hostName;
    private int portNumber;
    private ServerSocket serverSocket;
    private Lobby lobby;

    public GameServer(String hostName, int portNumber) throws IOException {
        this.hostName = hostName;
        this.portNumber = portNumber;
        serverSocket = new ServerSocket(portNumber);
        lobby = new Lobby();
    }

    @Override
    public void run() {
        System.out.println("GameServer started on: " + portNumber);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(lobby);
        while (true) {
            System.out.println("Accepting...");
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Accepted!");
                ClientHandler c = new ClientHandler(clientSocket);
//                PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
//                out.println("Give nickname: ");
//                out.flush();
//                Scanner in = new Scanner(clientSocket.getInputStream());
//                c.setNickname(in.nextLine());
                c.setLobby(lobby);
                executor.submit(c);
//                lobby.addQueue(c);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        executor.shutdown();
    }
}
