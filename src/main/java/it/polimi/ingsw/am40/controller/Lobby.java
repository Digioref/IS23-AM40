package it.polimi.ingsw.am40.controller;

import it.polimi.ingsw.am40.model.Game;
import it.polimi.ingsw.am40.model.Player;

import java.util.Map;
import java.util.Queue;

public class Lobby {
    private int numPlayers;
    private Queue<ClientHandler> queue;
    private Map<ClientHandler, Game> map;

    public Lobby getInstanceLobby (int i) {
        if (i == numPlayers) {
            return this;
        }
        return null;
    }

    public void removeFromQueue (Game game) {
        map.put(queue.remove(), game);
    }

    public void run() {

    }

    public Game findGame(ClientHandler c) {
        return map.get(c);
    }

}
