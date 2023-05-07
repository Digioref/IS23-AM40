package it.polimi.ingsw.am40.Network.RMI;

import it.polimi.ingsw.am40.Client.RMIClientInterface;
import it.polimi.ingsw.am40.Controller.Lobby;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.LoggingPhase;
import it.polimi.ingsw.am40.Network.RMIClientHandler;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {
    private Lobby lobby;
    private Map<String,RMIClientHandler> clientHandlers;


    public RMIServer() throws RemoteException {
        super();
        clientHandlers = new HashMap<>();
    }

    @Override
    public String login(String s, RMIClientInterface client) throws RemoteException {
        RMIClientHandler rmiClientHandler;
        if(!checkNickname(s)) {
            rmiClientHandler = new RMIClientHandler();
            rmiClientHandler.setLobby(lobby);
            rmiClientHandler.setNickname(s);
            rmiClientHandler.setLogged(true);
            rmiClientHandler.setRmiClient(client);
            clientHandlers.put(s, rmiClientHandler);
            lobby.addQueue(rmiClientHandler);
        } else {
            return JSONConverterStoC.normalMessage("The nickname you desire is already in use! Please type another nickname:");

        }
        if (lobby.getQueue().get(0).getNickname().equals(rmiClientHandler.getNickname())) {
            rmiClientHandler.setLogphase(LoggingPhase.SETTING);
            LoggingPhase.setSETPLAYERS(true);
            return JSONConverterStoC.normalMessage("You are logged in! Waiting in the lobby...\nYou can set the number of players you want to play with:");
        }
        return JSONConverterStoC.normalMessage("You are logged in! Waiting in the lobby...");
    }

    @Override
    public String setPlayers(String s, int n) throws RemoteException {
        if(!clientHandlers.get(s).getLogphase().equals(LoggingPhase.SETTING)) {

        }
        lobby.setNumPlayers(n);
        return JSONConverterStoC.normalMessage("Number of players set!");
    }
//
//    @Override
//    public String help() throws RemoteException {
//        return null;
//    }
//
//    @Override
//    public String insert(int c) throws RemoteException {
//        return null;
//    }
//
//    @Override
//    public String order(ArrayList<Integer> n) throws RemoteException {
//        return null;
//    }
//
//    @Override
//    public String pick() throws RemoteException {
//        return null;
//    }
//
//    @Override
//    public String quit() throws RemoteException {
//        return null;
//    }
//
//    @Override
//    public String remove() throws RemoteException {
//        return null;
//    }
//
//    @Override
//    public String select(ArrayList<Integer> n) throws RemoteException {
//        return null;
//    }
//
//    @Override
//    public String getBoard() throws RemoteException {
//        return null;
//    }
//
//    @Override
//    public String getBook() throws RemoteException {
//        return null;
//    }
//
//    @Override
//    public String getBookAll() throws RemoteException {
//        return null;
//    }
//
//    @Override
//    public String getCommGoals() throws RemoteException {
//        return null;
//    }
//
//    @Override
//    public String getCurrent() throws RemoteException {
//        return null;
//    }
//
//    @Override
//    public String getCurScore() throws RemoteException {
//        return null;
//    }
//
//    @Override
//    public String getHiddenScore() throws RemoteException {
//        return null;
//    }
//
//    @Override
//    public String getPersGoal() throws RemoteException {
//        return null;
//    }
//
//    @Override
//    public String getPlayers() throws RemoteException {
//        return null;
//    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }
    private boolean checkNickname(String nickname) {
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
}
