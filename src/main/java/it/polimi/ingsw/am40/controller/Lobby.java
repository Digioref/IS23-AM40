package it.polimi.ingsw.am40.controller;

import it.polimi.ingsw.am40.model.Game;
import it.polimi.ingsw.am40.model.Player;

import java.util.ArrayList;
import java.util.Queue;

public class Lobby {
    private int numPlayers;
    private Queue<ClientHandler> queue;
    private ArrayList<ClientHandler> activePlayers;


    public void removeFromQueue () {
        if (activePlayers.size() == 0) {
            ClientHandler c = queue.remove();
            activePlayers.add(c);
            numPlayers = c.getPlayerNumbers();
        }
        if (numPlayers != 0 && activePlayers.size() != 0) {
            ClientHandler c = queue.remove();
            activePlayers.add(c);
        }
    }

    public void run() {
        while (true) {
            if (!queue.isEmpty()) {
                removeFromQueue();
            }
            if (numPlayers != 0 && activePlayers.size() == numPlayers) {
                create();
            }
        }
    }

    public Game findGame(ClientHandler c) {
        return c.getRemoveViewInstance();
    }

    public void addQueue (ClientHandler clientHandler) {
        queue.add(clientHandler);
    }

    public void create() {
        Game g = new Game(numPlayers);
        for (ClientHandler c : activePlayers) {
            g.addPlayer(new Player(c.getNickname()));
        }
        Controller c = new Controller(g);
        numPlayers = 0;
        activePlayers.clear();
    }

}
