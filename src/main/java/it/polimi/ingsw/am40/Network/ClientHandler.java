package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Controller.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private String nickname;
    private Socket socket;
    private Controller controller;
    private VirtualView virtualView;
    private int numPlayers;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void send(String s) {

    }
    public void receive() {}

    public VirtualView getVirtualViewInstance() {
        return this.virtualView;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getNickname() {
        return nickname;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    @Override
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
// Leggo e scrivo nella connessione finche' non ricevo "quit"
            while (true) {
                out.println("Give command: ");
                out.flush();
                String line = in.nextLine();
                if (line.equals("quit")) {
                    break;
                } else {
                    out.println("Received: " + line);
                    out.flush();
                }
            }
// Chiudo gli stream e il socket
            in.close();
            out.close();
            System.out.println("ClientHandler closed!");
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
