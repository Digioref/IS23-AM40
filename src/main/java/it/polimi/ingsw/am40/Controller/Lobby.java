package it.polimi.ingsw.am40.Controller;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.Game;
import it.polimi.ingsw.am40.Model.Player;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.LoggingPhase;
import it.polimi.ingsw.am40.Network.VirtualView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>This is the lobby of the game, where each player enters before the game can be created</p>
 * <p>The game is created only when the number of players specified by the first player to enter the lobby is reached</p>
 */
public class Lobby implements Runnable {
    private int numPlayers;
    private final ArrayList<Handlers> queue;
    private final ArrayList<Handlers> activePlayers;
    private final ArrayList<String> nicknameInGame;
    private final Map<String, GameController> games;

    /**
     * Constructor which initializes all the features of the class
     */
    public Lobby() {
        numPlayers = 0;
        queue = new ArrayList<>();
        activePlayers = new ArrayList<>();
        nicknameInGame = new ArrayList<>();
        games = new HashMap<>();
    }

    /**
     * It removes the first player from the queue and puts him in the active players
     */
    public void  removeFromQueue() {
            synchronized (queue) {
                Handlers c;
                c = queue.remove(0);
                activePlayers.add(c);
                try {
                    if (numPlayers != 0) {
                        c.sendMessage(JSONConverterStoC.normalMessage("You are playing with " + numPlayers + " players!"));
                    }
                } catch (IOException e) {
                    System.out.println("Client not reachable");
                    removeQuit(c);

                }
            }
    }

    /**
     * The run method to run the lobby, which is a Runnable
     */
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

    /**
     * It adds the handler of the player to the queue
     * @param clientHandler the handler of the player, server side
     */
    public void addQueue (Handlers clientHandler) {
        synchronized (queue) {
            queue.add(clientHandler);
            if (queue.size() == 1) {
                queue.get(0).setLogphase(LoggingPhase.SETTING);
            }
        }
    }

    /**
     * It creates the game adding the players, taking them from the array active players
     */
    public void create() {
        System.out.println("Creating game...");
        Game g = new Game(numPlayers);
        Controller c = new Controller(g, this);
        for (Handlers cl : activePlayers) {
            g.addPlayer(new Player(cl.getNickname()));
            cl.setController(c);
            VirtualView v = new VirtualView(cl.getNickname(), cl);
            cl.setVirtualView(v);
            cl.setLogphase(LoggingPhase.INGAME);
            g.register(v);
            games.put(cl.getNickname(), c.getGameController());
            try {
                cl.sendMessage(JSONConverterStoC.normalMessage("Game"));
            } catch (IOException e) {
                System.out.println("Client not reachable");
                removeQuit(cl);
                break;
            }
        }
        numPlayers = 0;
        activePlayers.clear();
        g.configureGame();
        g.startGame();
        LoggingPhase.setSETPLAYERS(false);
        try {
            if (!queue.isEmpty()) {
                queue.get(0).sendMessage(JSONConverterStoC.normalMessage("Setplayers"));
                queue.get(0).setLogphase(LoggingPhase.SETTING);
                LoggingPhase.setSETPLAYERS(true);
            }
        } catch (IOException e) {
            System.out.println("Client not reachable");
        }
    }

    /**
     * It returns the nicknames already used, taken by other players, so they cannot be used again unless the game of that player ends
     * @return the nicknames already used
     */
    public ArrayList<String> getNicknameInGame() {
        return nicknameInGame;
    }

    public ArrayList<Handlers> getActivePlayers() {
        return activePlayers;
    }

    /**
     *It sets the number of player for the next game
     * @param numPlayers number of players
     */
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public int getNumPlayers(){
        return numPlayers;
    }

    /**
     * It returns the queue of the players
     * @return the queue of the lobby
     */
    public ArrayList<Handlers> getQueue() {
        synchronized (queue) {
            return queue;
        }
    }

    /**
     * It adds the nickname in the parameter to the nicknames in use
     * @param s a nickname
     */
    public void addNickname(String s) {
        nicknameInGame.add(s);
    }

    /**
     * It removes the handler specified by the parameter from the queue and from the active players
     * @param c the handler of the player to be removed
     */
    public void removeQuit(Handlers c) {
        synchronized (queue) {
            queue.remove(c);
            if (activePlayers.contains(c)) {
                if (activePlayers.indexOf(c) == 0) {
                    numPlayers = 0;
                    try {
                        if (activePlayers.size() > 1) {
                            activePlayers.get(1).sendMessage(JSONConverterStoC.normalMessage("Setplayers"));
                            LoggingPhase.setSETPLAYERS(true);
                            activePlayers.get(1).setLogphase(LoggingPhase.SETTING);
                        } else {
                            LoggingPhase.setSETPLAYERS(false);
                        }
                    } catch (IOException e) {
                        System.out.println("Client not reachable");
                    }
                }
                activePlayers.remove(c);
                nicknameInGame.remove(c.getNickname());
                for (Handlers cl: activePlayers) {
                    try {
                        cl.sendMessage(JSONConverterStoC.normalMessage("Player " + c.getNickname() + " disconnected!"));
                    } catch (IOException e) {
                        System.out.println("Client not reachable");
                    }
                }
            }
        }
    }

    /**
     * <p>It returns a map between nicknames and the controllers of the games</p>
     * <p>The nickname is the key of the map, and the controller is the controller of the game which has a player with the aforementioned nickname</p>
     * @return a map between nicknames and controllers
     */
    public Map<String, GameController> getGames() {
        return games;
    }

    /**
     * It is a method called when a game is ended, it removes the nickname of the handler from the map between nicknames and controllers and from the active nicknames
     * @param c handler to be removed
     */
    public void closeGame(Handlers c) {
        games.remove(c.getNickname());
        nicknameInGame.remove(c.getNickname());
    }


}
