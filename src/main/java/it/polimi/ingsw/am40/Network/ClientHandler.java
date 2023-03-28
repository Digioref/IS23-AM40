package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Controller.Controller;

import java.net.ServerSocket;

public class ClientHandler {

    private String nickname;
    private ServerSocket socket;
    private Controller controller;
    private VirtualView virtualView;
    private int numPlayers;

    public ClientHandler(ServerSocket socket) {
        this.socket = socket;
    }

    public VirtualView getVirtualViewInstance() {
        return this.virtualView;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getNickname() {
        return "return";
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
}
