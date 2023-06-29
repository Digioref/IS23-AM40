package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Controller.Controller;
import it.polimi.ingsw.am40.Controller.Lobby;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The abstract class representing a generic handler. It is extended by the socket handler and rmi handler
 */
public abstract class Handlers {
    /**
     * The number of nicknames suggested to the player if he wants to log in with a nickname already in use
     */
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
    protected boolean disconnected;
//    protected ArrayList<String> commands;


    /**
     * Sets the attribute nickname to the parameter passed
     * @param nickname a nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * It returns the nickname of the player associated with this handler
     * @return the attribute nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * It returns the controller of the game
     * @return the attribute controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Sets the attribute controller to the passed parameter
     * @param controller controller of the game
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * It returns the number of th players in game
     * @return the value of the attribute numplayers
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * It returns the lobby
     * @return the value of the attribute lobby
     */
    public Lobby getLobby() {
        return lobby;
    }

    /**
     * Sets the attribute lobby to the parameter passed
     * @param lobby the lobby
     */
    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    /**
     * Sets the parameter logged to the parameter passed
     * @param logged a boolean
     */
    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    /**
     * it returns true if the player is logged in
     * @return true if the player is logged in, false otherwise
     */
    public boolean isLogged() {
        return logged;
    }

    /**
     * It checks if the provided nickname is already in use
     * @param nickname a nickname
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
     * @param numPlayers an integer
     */
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    /**
     * Sets the attribute virtualView to the parameter passed
     * @param v a virtual view
     */
    public void setVirtualView(VirtualView v) {
        this.virtualView = v;
    }

    /**
     * Sets the attribute logphase to the parameter passed
     * @param logphase the phase of the log in process
     */
    public void setLogphase(LoggingPhase logphase) {
        this.logphase = logphase;
    }

    /**
     * It returns the logging phase of the player
     * @return the attribute logphase
     */
    public LoggingPhase getLogphase() {
        return logphase;
    }

    /**
     * It sends the provided string to the client
     * @param s a string to be sent
     * @throws IOException
     */
    public abstract void sendMessage(String s) throws IOException;

    /**
     * It sends to the client some suggested nicknames, created by joining the nickname chosen by the user with some numbers
     * @param s nickname desired by the user
     */
    public abstract void suggestNickname(String s);

//    public ArrayList<String> getCommands() {
//        return commands;
//    }

    /**
     * It executes the game command by calling the corresponding method of the game controller
     * @param at the action to be performed on the game
     * @param arr parameters necessary to perform the action; they are integers
     */
    public abstract void executeCommand(ActionType at, ArrayList<Integer> arr);

    /**
     * It adds to the game chat a new message
     * @param message the message
     * @param name the receiver of the message
     */
    public abstract void chat(String message, String name);

    /**
     * It allows the player to get the chat of the game
     */
    public abstract void getChat();

    /**
     * It returns the message adapter, which parses the message from the client
     * @return the message adapter
     */
    public MessageAdapter getMessAd() {
        return messAd;
    }

    /**
     * It sends a chat message
     * @param s the message to be sent
     */
    public abstract void sendChat(String s);

    /**
     * It closes the handler
     */
    public abstract void close();

    /**
     * It handles the Pong message received from the client
     */
    public abstract void handlePong();

    /**
     * It closes the handler because the game has ended
     */
    public abstract void closeGame();
}
