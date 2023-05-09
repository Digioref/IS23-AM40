package it.polimi.ingsw.am40.Network.RMI;

import it.polimi.ingsw.am40.Client.RMIClientInterface;
import it.polimi.ingsw.am40.Controller.Lobby;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.ParsingJSONManager;
import it.polimi.ingsw.am40.Network.LoggingPhase;
import it.polimi.ingsw.am40.Network.RMIClientHandler;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {
    private Lobby lobby;
    private Map<String,RMIClientHandler> clientHandlers;
    private ArrayList<String> commands;


    public RMIServer() throws RemoteException {
        super();
        clientHandlers = new HashMap<>();
        commands = new ArrayList<>();
        ParsingJSONManager.commands(commands);
    }

    @Override
    public void login(String s, RMIClientInterface client) throws RemoteException {
        for (String t: clientHandlers.keySet()) {
            if(clientHandlers.get(t).getRmiClient().equals(client)) {
                client.receive(JSONConverterStoC.normalMessage("You are already logged in!"));
                return;
            }
        }
        RMIClientHandler rmiClientHandler = null;
        if(!checkNickname(s)) {
            rmiClientHandler = new RMIClientHandler();
            rmiClientHandler.setLobby(lobby);
            rmiClientHandler.setNickname(s);
            rmiClientHandler.setLogged(true);
            rmiClientHandler.setRmiClient(client);
            clientHandlers.put(s, rmiClientHandler);
            lobby.addQueue(rmiClientHandler);
            lobby.addNickname(s);
            if (lobby.getQueue().get(0).getNickname().equals(rmiClientHandler.getNickname())) {
                rmiClientHandler.setLogphase(LoggingPhase.SETTING);
                LoggingPhase.setSETPLAYERS(true);
                clientHandlers.get(s).sendMessage(JSONConverterStoC.normalMessage("You are logged in! Waiting in the lobby...\nYou can set the number of players you want to play with:"));
                return;
            }
        } else {
            client.receive(JSONConverterStoC.normalMessage("The nickname you desire is already in use! Please type another nickname:"));
            return;
        }
        clientHandlers.get(s).sendMessage(JSONConverterStoC.normalMessage("You are logged in! Waiting in the lobby..."));
    }

    @Override
    public void setPlayers(String s, String n) throws RemoteException {
//        if(!clientHandlers.get(s).getLogphase().equals(LoggingPhase.SETTING) || LoggingPhase.SETPLAYERS) {
//            clientHandlers.get(s).sendMessage(JSONConverterStoC.normalMessage("You can not set the number of players!"));
//            return;
//        }
//        lobby.setNumPlayers(n);
//        LoggingPhase.setSETPLAYERS(true);
//        clientHandlers.get(s).setLogphase(LoggingPhase.WAITING);
//        clientHandlers.get(s).sendMessage(JSONConverterStoC.normalMessage("Number of players set!"));
        try {
            clientHandlers.get(s).getMessAd().parserMessage(clientHandlers.get(s), n);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void help(RMIClientInterface client, String s) throws RemoteException {
        for (String s1: commands) {
            switch(s1) {
                case "select":
                    client.receive(JSONConverterStoC.normalMessage("- " + s1 + " [int] [int]"));
                    break;
                case "order":
                    client.receive(JSONConverterStoC.normalMessage("- " + s1 + " [int] (at least)"));
                    break;
                case "setplayers", "insert":
                    client.receive(JSONConverterStoC.normalMessage("- " + s1 + " [int]"));
                    break;
                case "login":
                    client.receive(JSONConverterStoC.normalMessage("- " + s1 + " [string]"));
                    break;
                case "chat":
                    client.receive(JSONConverterStoC.normalMessage("- " + s + "#nameofplayer" + "#message"));
                    break;
                case "chatall":
                    client.receive(JSONConverterStoC.normalMessage("- " + s + "#message"));
                    break;
                default:
                    client.receive(JSONConverterStoC.normalMessage("- " + s1));
                    break;
            }
        }
    }

    @Override
    public void gameInfoRequest(String s, String command) throws RemoteException {
        try {
            clientHandlers.get(s).getMessAd().parserMessage(clientHandlers.get(s), command);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gameUpdate(String s, String command) throws RemoteException {
        try {
            clientHandlers.get(s).getMessAd().parserMessage(clientHandlers.get(s), command);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
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
