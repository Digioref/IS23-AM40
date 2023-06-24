package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Controller.Controller;
import it.polimi.ingsw.am40.Controller.Lobby;

import java.io.IOException;
import java.util.ArrayList;

/**
 * todo
 */
public abstract class Handlers {
    public static final int NSUGGEST = 8;
    protected String nickname;
    protected Controller controller;
    protected VirtualView virtualView;
    protected int numPlayers;
    protected boolean logged;
    protected Lobby lobby;
    protected LoggingPhase logphase;
    protected MessageAdapter messAd;
    protected int nPingLost;
//    protected ArrayList<String> commands;


    /**
     * Sets the attribute nickname to the parameter passed
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the attribute nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return the attribute controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Sets the attribute controller to the passed parameter
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * @return the valure of the attribute numplayers
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * @return the valure of the attribute lobby
     */
    public Lobby getLobby() {
        return lobby;
    }

    /**
     * Sets the attribute lobby to the parameter passed
     * @param lobby
     */
    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    /**
     * Sets the parameter logged to the parameter passed
     * @param logged
     */
    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    /**
     * todo
     * @return
     */
    public boolean isLogged() {
        return logged;
    }

    /**
     * @param nickname
     * @return true if the nickname passed is already used
     */
    public boolean checkNickname(String nickname) {
        if(nickname.equals("")) {
            return false;
        }
        if (lobby.getNicknameInGame().size() == 0) {
            return false;
        }
        for (String s: lobby.getNicknameInGame()) {
            if (s.toLowerCase().equals(nickname.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the attribute numPlayers to the parameter passed
     * @param numPlayers
     */
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    /**
     * Sets the attribute virtualView to the parameter passed
     * @param v
     */
    public void setVirtualView(VirtualView v) {
        this.virtualView = v;
    }

    /**
     * Sets the attribute logphase to the parameter passed
     * @param logphase
     */
    public void setLogphase(LoggingPhase logphase) {
        this.logphase = logphase;
    }

    /**
     * @return the attribute logphase
     */
    public LoggingPhase getLogphase() {
        return logphase;
    }

    /**
     * todo
     * @param s
     * @throws IOException
     */
    public abstract void sendMessage(String s) throws IOException;

    /**
     * todo
     * @param s
     */
    public abstract void suggestNickname(String s);

//    public ArrayList<String> getCommands() {
//        return commands;
//    }

    /**
     * todo
     * @param at
     * @param arr
     */
    public abstract void executeCommand(ActionType at, ArrayList<Integer> arr);

    /**
     * todo
     * @param message
     * @param name
     */
    public abstract void chat(String message, String name);

    /**
     * todo
     */
    public abstract void getChat();

    /**
     * todo
     * @return
     */
    public MessageAdapter getMessAd() {
        return messAd;
    }

    /**
     * todo
     * @param s
     */
    public abstract void sendChat(String s);

    /**
     * todo
     */
    public abstract void close();

    /**
     * todo
     */
    public abstract void handlePong();

    /**
     * todo
     */
    public abstract void closeGame();
}
