package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Controller.Controller;
import it.polimi.ingsw.am40.Controller.Lobby;

import java.io.IOException;
import java.util.ArrayList;

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

    public VirtualView getVirtualViewInstance() {
        return this.virtualView;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getNickname() {
        return nickname;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }
    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public boolean isLogged() {
        return logged;
    }
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
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }
    public void setVirtualView(VirtualView v) {
        this.virtualView = v;
    }


    public void setLogphase(LoggingPhase logphase) {
        this.logphase = logphase;
    }

    public LoggingPhase getLogphase() {
        return logphase;
    }
    public abstract void sendMessage(String s) throws IOException;

    public abstract void suggestNickname(String s);

//    public ArrayList<String> getCommands() {
//        return commands;
//    }

    public abstract void executeCommand(ActionType at, ArrayList<Integer> arr);

    public abstract void chat(String message, String name);

    public abstract void getChat();

    public MessageAdapter getMessAd() {
        return messAd;
    }
    public abstract void sendChat(String s);

    public abstract void close();
    public abstract void handlePong();
}
