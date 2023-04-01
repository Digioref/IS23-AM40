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
    private ArrayList<String> nicknameInGame;

    public Lobby() {
        numPlayers = 0;
        queue = new ArrayList();
        activePlayers = new ArrayList<>();
        nicknameInGame = new ArrayList<>();
    }

    public void  removeFromQueue() {
        if (activePlayers.size() == 0) {
            ClientHandler c;
            synchronized (queue) {
                c = queue.remove(0);
            }
            activePlayers.add(c);
            numPlayers = c.getNumPlayers();
            nicknameInGame.add(c.getNickname());
        }
        else if (numPlayers != 0 && activePlayers.size() != 0) {
            ClientHandler c;
            synchronized (queue) {
                c = queue.remove(0);
            }
            activePlayers.add(c);
            nicknameInGame.add(c.getNickname());
        }
    }

    public void run() {
        System.out.println("Lobby is running...");
        while (true) {
            synchronized (queue) {
                if (!queue.isEmpty()) {
                    removeFromQueue();
                }
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
        synchronized (queue) {
            queue.add(clientHandler);
        }
//        System.out.println("Added " + clientHandler.getNickname());
    }

    public void create() {
        System.out.println("Creating game...");
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

    public ArrayList<String> getNicknameInGame() {
        return nicknameInGame;
    }

    public ArrayList<ClientHandler> getActivePlayers() {
        return activePlayers;
    }
}
