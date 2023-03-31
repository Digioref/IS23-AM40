package it.polimi.ingsw.am40.Controller;

import it.polimi.ingsw.am40.Model.Game;
import it.polimi.ingsw.am40.Model.Player;
import it.polimi.ingsw.am40.Network.ClientHandler;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Lobby implements Runnable {
    private int numPlayers;
    private ArrayList<ClientHandler> queue;
    private ArrayList<ClientHandler> activePlayers;

    public Lobby() {
        numPlayers = 0;
        queue = new ArrayList();
        activePlayers = new ArrayList<>();
    }

    public synchronized void  removeFromQueue() {
        if (activePlayers.size() == 0) {
            ClientHandler c = queue.remove(0);
            activePlayers.add(c);
            numPlayers = c.getNumPlayers();
        }
        if (numPlayers != 0 && activePlayers.size() != 0) {
            ClientHandler c = queue.remove(0);
            activePlayers.add(c);
        }
    }

    public void run() {
        System.out.println("Lobby is running...");
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

    public synchronized void addQueue (ClientHandler clientHandler) {
        queue.add(clientHandler);
        System.out.println("Added " + clientHandler.getNickname());
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
