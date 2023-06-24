package it.polimi.ingsw.am40.Network.RMI;

import it.polimi.ingsw.am40.Client.RMIClientInterface;
import it.polimi.ingsw.am40.Controller.Lobby;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.ParsingJSONManager;
import it.polimi.ingsw.am40.Network.LoggingPhase;
import it.polimi.ingsw.am40.Network.RMIClientHandler;
import it.polimi.ingsw.am40.Network.VirtualView;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static it.polimi.ingsw.am40.Network.Handlers.NSUGGEST;

/**
 * TODO
 */
public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {
    private Lobby lobby;
    /**
     * Maps the name of the player to its ClientHandler
     */
    private Map<String,RMIClientHandler> clientHandlers;
    /**
     * List of the available commands (to be used with the help command)
     */
    private ArrayList<String> commands;
    private ArrayList<RMIClientHandler> rmiHandlers;


    /**
     * TODO
     * @throws RemoteException
     */
    public RMIServer() throws RemoteException {
        super();
        clientHandlers = new HashMap<>();
        commands = new ArrayList<>();
        ParsingJSONManager.commands(commands);
        rmiHandlers = new ArrayList<>();
    }

    /**
     * todo
     * @param s
     * @param client
     * @throws RemoteException
     */
    @Override
    public void login(String s, RMIClientInterface client) throws RemoteException {
        if (s.equals("")) {
            client.receive(JSONConverterStoC.createJSONError("Incomplete command, you must specify your nickname!"));
            return;
        }
        for (String t: clientHandlers.keySet()) {
            if(clientHandlers.get(t).getRmiClient().equals(client)) {
                client.receive(JSONConverterStoC.createJSONError("You are already logged in!"));
                return;
            }
        }
//        RMIClientHandler rmiClientHandler = null;
        if(!checkNickname(s)) {
//            rmiClientHandler = new RMIClientHandler();
            for (RMIClientHandler r: rmiHandlers) {
                if (r.getRmiClient().equals(client)) {
                    r.setNickname(s);
                    r.setLogged(true);
                    clientHandlers.put(s, r);
                    client.receiveNickname(JSONConverterStoC.createJSONNickname(s));
                    clientHandlers.get(s).sendMessage(JSONConverterStoC.normalMessage("\n" +
                            " ____    ____  ____  ____    ______   ____  ____  ________  _____     ________  _____  ________  \n" +
                            "|_   \\  /   _||_  _||_  _| .' ____ \\ |_   ||   _||_   __  ||_   _|   |_   __  ||_   _||_   __  | \n" +
                            "  |   \\/   |    \\ \\  / /   | (___ \\_|  | |__| |    | |_ \\_|  | |       | |_ \\_|  | |    | |_ \\_| \n" +
                            "  | |\\  /| |     \\ \\/ /     _.____`.   |  __  |    |  _| _   | |   _   |  _|     | |    |  _| _  \n" +
                            " _| |_\\/_| |_    _|  |_    | \\____) | _| |  | |_  _| |__/ | _| |__/ | _| |_     _| |_  _| |__/ | \n" +
                            "|_____||_____|  |______|    \\______.'|____||____||________||________||_____|   |_____||________| \n" +
                            "                                                                                                 \n"));
                    clientHandlers.get(s).sendMessage(JSONConverterStoC.normalMessage("You are logged in!"));
                    clientHandlers.get(s).sendMessage(JSONConverterStoC.normalMessage("Waiting"));
                    lobby.addQueue(r);
                    lobby.addNickname(s);
                    if (!lobby.getQueue().isEmpty() && lobby.getQueue().get(0).getNickname().equals(r.getNickname())) {
                        r.setLogphase(LoggingPhase.SETTING);
                        LoggingPhase.setSETPLAYERS(true);
                        clientHandlers.get(s).sendMessage(JSONConverterStoC.normalMessage("Setplayers"));
                    }
                    rmiHandlers.remove(r);
                    break;
                }
            }
//            rmiClientHandler.setLobby(lobby);
//            rmiClientHandler.setNickname(s);
//            rmiClientHandler.setLogged(true);
//            rmiClientHandler.setRmiClient(client);
//            clientHandlers.put(s, rmiClientHandler);
//            client.receiveNickname(JSONConverterStoC.createJSONNickname(s));
//            clientHandlers.get(s).sendMessage(JSONConverterStoC.normalMessage("You are logged in! Waiting in the lobby..."));
//            lobby.addQueue(rmiClientHandler);
//            lobby.addNickname(s);
//            if (!lobby.getQueue().isEmpty() && lobby.getQueue().get(0).getNickname().equals(rmiClientHandler.getNickname())) {
//                rmiClientHandler.setLogphase(LoggingPhase.SETTING);
//                LoggingPhase.setSETPLAYERS(true);
//                clientHandlers.get(s).sendMessage(JSONConverterStoC.normalMessage("You can set the number of players you want to play with:"));
//            }
        } else {
            for (RMIClientHandler r: rmiHandlers) {
                if (r.getRmiClient().equals(client)) {
                    if ((!(r.getLobby().getGames().containsKey(s)) && r.getLobby().getNicknameInGame().contains(s)) || (r.getLobby().getGames().containsKey(s) && !r.getLobby().getGames().get(s).getGame().getDiscPlayers().contains(s))) {
                        client.receive(JSONConverterStoC.createJSONError("The nickname you desire is already in use! Please type another nickname:"));
//                        client.receive(JSONConverterStoC.normalMessage("You can choose one of the following nicknames, if you want"));
                        suggestNickname(s, client);
                        break;
                    } else if (r.getLobby().getGames().containsKey(s) && r.getLobby().getGames().get(s).getGame().getDiscPlayers().contains(s)) {
                        r.sendMessage(JSONConverterStoC.normalMessage("Welcome back " + s + "!\nReconnecting to the game..."));
                        r.setNickname(s);
                        r.setLogged(true);
                        r.setLogphase(LoggingPhase.INGAME);
                        r.setController(r.getLobby().getGames().get(s).getController());
                        clientHandlers.put(s, r);
                        client.receiveNickname(JSONConverterStoC.createJSONNickname(s));
                        r.setNumPlayers(r.getLobby().getGames().get(s).getGame().getNumPlayers());
                        for (VirtualView v: r.getLobby().getGames().get(s).getGame().getObservers()) {
                            if (v.getNickname().equals(s)) {
                                v.setClientHandler(r);
                            }
                        }
                        r.getController().getGameController().reconnect(s);
                        rmiHandlers.remove(r);
                        break;
                    }
                }
            }

        }

    }

    /**
     * todo
     * @param s
     * @param n
     * @throws RemoteException
     */
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

    /**
     * todo
     * @param client
     * @param s
     * @throws RemoteException
     */
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
                default:
                    client.receive(JSONConverterStoC.normalMessage("- " + s1));
                    break;
            }
        }
    }

    /**
     * todo
     * @param s
     * @param command
     * @throws RemoteException
     */
    @Override
    public void gameInfoRequest(String s, String command) throws RemoteException {
        try {
            clientHandlers.get(s).getMessAd().parserMessage(clientHandlers.get(s), command);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * todo
     * @param s
     * @param command
     * @throws RemoteException
     */
    @Override
    public void gameUpdate(String s, String command) throws RemoteException {
        try {
            clientHandlers.get(s).getMessAd().parserMessage(clientHandlers.get(s), command);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * todo
     * @param s
     * @param command
     * @throws RemoteException
     */
    @Override
    public void chat(String s, String command) throws RemoteException {
        try {
            clientHandlers.get(s).getMessAd().parserMessage(clientHandlers.get(s), command);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * todo
     * @param s
     * @param command
     * @throws RemoteException
     */
    @Override
    public void getChat(String s, String command) throws RemoteException {
        try {
            clientHandlers.get(s).getMessAd().parserMessage(clientHandlers.get(s), command);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * todo
     * @param s
     * @throws RemoteException
     */
    @Override
    public void close(String s) throws RemoteException {
        if (clientHandlers.containsKey(s)) {
            lobby.removeQuit(clientHandlers.get(s));
            RMIClientHandler r = clientHandlers.remove(s);
            r.close();
            System.out.println("Client " + s + " closed!");
            if(lobby.getGames().containsKey(s)) {
                lobby.getGames().get(s).disconnectPlayer(s);
            }
        }
    }

    /**
     * todo
     * @param client
     * @throws RemoteException
     */
    @Override
    public void receivePong(RMIClientInterface client) throws RemoteException {
        for (RMIClientHandler r: rmiHandlers) {
            if (r.getRmiClient().equals(client)) {
                r.handlePong();
                break;
            }
        }
        for (String s: clientHandlers.keySet()) {
            if (clientHandlers.get(s).getRmiClient().equals(client)) {
                clientHandlers.get(s).handlePong();
                break;
            }
        }
    }

    /**
     * todo
     * @param client
     * @throws RemoteException
     */
    @Override
    public void init(RMIClientInterface client) throws RemoteException {
        RMIClientHandler rmiClientHandler = new RMIClientHandler(this);
        rmiClientHandler.setRmiClient(client);
        rmiHandlers.add(rmiClientHandler);
        rmiClientHandler.setLobby(lobby);
    }

    /**
     * todo
     * @param lobby
     */
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

    /**
     * @return the attribute clientHandlers
     */
    public Map<String, RMIClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    /**
     * todo
     * @param nickname
     * @param client
     */
    private void suggestNickname(String nickname, RMIClientInterface client) {
        Random random = new Random();
        for (int i = 0; i < NSUGGEST; i++) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            int z = random.nextInt(10);
            try {
                client.receive(JSONConverterStoC.normalMessage(nickname + x + y + z));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @return the attribute rmiHandlers
     */
    public ArrayList<RMIClientHandler> getRmiHandlers() {
        return rmiHandlers;
    }
}
