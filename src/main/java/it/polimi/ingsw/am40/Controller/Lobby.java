package it.polimi.ingsw.am40.Controller;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.Game;
import it.polimi.ingsw.am40.Model.Player;
import it.polimi.ingsw.am40.Network.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.jar.JarEntry;

public class Lobby implements Runnable {
    private int numPlayers;
    private final ArrayList<Handlers> queue;
    private ArrayList<Handlers> activePlayers;
    private ArrayList<String> nicknameInGame;
    private Map<String, GameController> games;

    public Lobby() {
        numPlayers = 0;
        queue = new ArrayList<>();
        activePlayers = new ArrayList<>();
        nicknameInGame = new ArrayList<>();
        games = new HashMap<>();
    }

    public void  removeFromQueue() {
//        if (activePlayers.size() == 0) {
            synchronized (queue) {
                Handlers c;
                c = queue.remove(0);
                activePlayers.add(c);
                try {
                    c.sendMessage(JSONConverterStoC.normalMessage("You are playing with " + numPlayers + " players!"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
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
//            nicknameInGame.add(clientHandler.getNickname());
            if (queue.size() == 1) {
                queue.get(0).setLogphase(LoggingPhase.SETTING);
            }
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
            g.register(v);
            games.put(cl.getNickname(), c.getGameController());
        }
        numPlayers = 0;
        activePlayers.clear();
        g.configureGame();
        g.startGame();
        LoggingPhase.setSETPLAYERS(false);
        try {
            if (!queue.isEmpty()) {
                queue.get(0).sendMessage(JSONConverterStoC.normalMessage("The number of players you want to play with:"));
                queue.get(0).setLogphase(LoggingPhase.SETTING);
                LoggingPhase.setSETPLAYERS(true);
            }
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
    public void addNickname(String s) {
        nicknameInGame.add(s);
    }

    public void removeQuit(Handlers c) {
        synchronized (queue) {
            if (queue.contains(c)) {
                queue.remove(c);
            }
            if (activePlayers.contains(c)) {
                activePlayers.remove(c);
                nicknameInGame.remove(c.getNickname());
                for (Handlers cl: activePlayers) {
                    try {
                        cl.sendMessage(JSONConverterStoC.normalMessage("Player " + c.getNickname() + " disconnected!"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public Map<String, GameController> getGames() {
        return games;
    }

    public void closeGame(Handlers c) {
        games.remove(c);
        nicknameInGame.remove(c.getNickname());
    }
}
