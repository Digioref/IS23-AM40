package it.polimi.ingsw.am40.Network.RMI;

import it.polimi.ingsw.am40.Controller.Lobby;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.LoggingPhase;
import it.polimi.ingsw.am40.Network.RMIClientHandler;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements IRMI {
    private Lobby lobby;


    public RMIServer() throws RemoteException {
        super();
    }

    @Override
    public String login(String s) throws RemoteException {
        RMIClientHandler rmiClientHandler;
        if(!checkNickname(s)) {
            rmiClientHandler = new RMIClientHandler();
            rmiClientHandler.setLobby(lobby);
            rmiClientHandler.setNickname(s);
            rmiClientHandler.setLogged(true);
//            lobby.addQueue(rmiClientHandler);
        } else {
            return JSONConverterStoC.normalMessage("The nickname you desire is already in use! Please type another nickname:");

        }
        if (lobby.getQueue().get(0).getNickname().equals(rmiClientHandler.getNickname())) {
            rmiClientHandler.setLogphase(LoggingPhase.SETTING);
            LoggingPhase.setSETPLAYERS(true);
            return JSONConverterStoC.normalMessage("You are logged in! Waiting in the lobby...\n You can set the number of players you want to play with:");
        }
        return JSONConverterStoC.normalMessage("You are logged in! Waiting in the lobby...");
    }

    @Override
    public String setPlayers(int n) throws RemoteException {
        return null;
    }

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
