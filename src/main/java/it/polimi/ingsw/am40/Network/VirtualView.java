package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Controller.Controller;

public class VirtualView implements  IGameObserver{

    private String nickname;
    private ClientHandler clientHandler;
    private Controller controller;

    public VirtualView(String nickname, ClientHandler clientHandler, Controller controller) {
        this.nickname = nickname;
        this.clientHandler = clientHandler;
        this.controller = controller;
    }

    @Override
    public void update() {

    }
}
