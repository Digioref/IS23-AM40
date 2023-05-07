package it.polimi.ingsw.am40.Controller;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.Game;
import it.polimi.ingsw.am40.Model.Player;
import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.LoggingPhase;
import it.polimi.ingsw.am40.Network.VirtualView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.jar.JarEntry;

public class Lobby implements Runnable {
    private int numPlayers;
    private ArrayList<Handlers> queue;
    private ArrayList<Handlers> activePlayers;
    private ArrayList<String> nicknameInGame;

    public Lobby() {
        numPlayers = 0;
        queue = new ArrayList<>();
        activePlayers = new ArrayList<>();
        nicknameInGame = new ArrayList<>();
    }

    public void  removeFromQueue() {
//        if (activePlayers.size() == 0) {
            Handlers c;
            synchronized (queue) {
                c = queue.remove(0);
            }
            activePlayers.add(c);
//            numPlayers = c.getNumPlayers();
            //nicknameInGame.add(c.getNickname());
//        }
//        else if (numPlayers != 0 && activePlayers.size() != 0) {
//            ClientHandler c;
//            synchronized (queue) {
//                c = queue.remove(0);
//            }
//            activePlayers.add(c);
//            nicknameInGame.add(c.getNickname());
//        }
    }

    public void run() {
        System.out.println("Lobby is running...");
        while (true) {
            synchronized (queue) {
                if (numPlayers != 0 && LoggingPhase.SETPLAYERS) {
                    if (!queue.isEmpty()) {
                        removeFromQueue();
                    }
                    if (activePlayers.size() == numPlayers) {
                        create();
                    }
                }
            }
        }
    }

    public Game findGame(ClientHandler c) {
        return c.getController().getGame();
    }

    public void addQueue (Handlers clientHandler) {
        synchronized (queue) {
            queue.add(clientHandler);
        }
//        System.out.println("Added " + clientHandler.getNickname());
    }

    public void create() {
        System.out.println("Creating game...");
        Game g = new Game(numPlayers);
        Controller c = new Controller(g);
        for (Handlers cl : activePlayers) {
            g.addPlayer(new Player(cl.getNickname()));
            cl.setController(c);
            VirtualView v = new VirtualView(cl.getNickname(), cl, c);
            cl.setVirtualView(v);
            cl.setLogphase(LoggingPhase.INGAME);
            g.register(cl.getVirtualViewInstance());
        }
        numPlayers = 0;
        activePlayers.clear();
        g.configureGame();
        g.startGame();
        LoggingPhase.setSETPLAYERS(false);
        try {
            queue.get(0).sendMessage(JSONConverterStoC.normalMessage("The number of players you want to play with:"));
            queue.get(0).setLogphase(LoggingPhase.SETTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getNicknameInGame() {
        return nicknameInGame;
    }

    public ArrayList<Handlers> getActivePlayers() {
        return activePlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public ArrayList<Handlers> getQueue() {
        synchronized (queue) {
            return queue;
        }
    }
}
