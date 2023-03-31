package it.polimi.ingsw.am40.Controller;

import it.polimi.ingsw.am40.Model.Game;
import it.polimi.ingsw.am40.Model.Player;
import it.polimi.ingsw.am40.Network.ClientHandler;

import java.util.ArrayList;
import java.util.Queue;

public class Lobby implements Runnable {
    private int numPlayers;
    private Queue<ClientHandler> queue;
    private ArrayList<ClientHandler> activePlayers;


    public void removeFromQueue () {
        if (activePlayers.size() == 0) {
            ClientHandler c = queue.remove();
            activePlayers.add(c);
            numPlayers = c.getNumPlayers();
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
        return c.getController().getGame();
    }

    public void addQueue (ClientHandler clientHandler) {
        queue.add(clientHandler);
    }

    public void create() {
        Game g = new Game(numPlayers);
        Controller c = new Controller(g);
        for (ClientHandler cl : activePlayers) {
            g.addPlayer(new Player(cl.getNickname()));
            cl.setController(c);
            g.register(cl.getVirtualViewInstance());
        }
        numPlayers = 0;
        activePlayers.clear();
        g.configureGame();
        g.startGame();
    }

}
