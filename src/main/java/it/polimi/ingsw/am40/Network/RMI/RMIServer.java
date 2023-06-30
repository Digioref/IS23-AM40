package it.polimi.ingsw.am40.Network.RMI;

import it.polimi.ingsw.am40.Client.RMIClientInterface;
import it.polimi.ingsw.am40.Controller.Lobby;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.JSONConversion.ParsingJSONManager;
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

import static it.polimi.ingsw.am40.Network.Handlers.NSUGGEST;

/**
 * It is created by the game server to handle the RMI connection. It has all the methods that can be called by the remote client using RMI
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
     * Constructor that initializes the feature of the class
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
     * The method that the player uses to log in
     * @param s nickname of the player
     * @param client remote client
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
                        r.sendMessage(JSONConverterStoC.normalMessage("RECONNECT"));
                        r.getController().getGameController().reconnect(s);
                        rmiHandlers.remove(r);
                        break;
                    }
                }
            }

        }

    }

    /**
     * It sets the number of player
     * @param s nickname of the player
     * @param n number of players in string format
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
            System.out.println("Parse error");
        }
    }

    /**
     * It sends to the client a list of command, with the type of parameters requested by each command
     * @param client remote client
     * @param s nickname of the player
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
     * It is used to request specific information about the game, such as the bookshelves, the current players, etc...
     * @param s nickname of the player
     * @param command the command representing the specific request
     * @throws RemoteException
     */
    @Override
    public void gameInfoRequest(String s, String command) throws RemoteException {
        try {
            clientHandlers.get(s).getMessAd().parserMessage(clientHandlers.get(s), command);
        } catch (IOException | ParseException e) {
            System.out.println("Error in parsing");
        }
    }

    /**
     * It is used to perform an action that changes the game state, such as insert, order, pick, etc...
     * @param s nickname of the player
     * @param command command that represents the action
     * @throws RemoteException
     */
    @Override
    public void gameUpdate(String s, String command) throws RemoteException {
        try {
            clientHandlers.get(s).getMessAd().parserMessage(clientHandlers.get(s), command);
        } catch (IOException | ParseException e) {
            System.out.println("Parse error");
        }
    }

    /**
     * It is used to send a message to other players
     * @param s nickname of the player who sends the message
     * @param command the command to send the message
     * @throws RemoteException
     */
    @Override
    public void chat(String s, String command) throws RemoteException {
        try {
            clientHandlers.get(s).getMessAd().parserMessage(clientHandlers.get(s), command);
        } catch (IOException | ParseException e) {
            System.out.println("Parse error");
        }
    }


    /**
     * It is used to get the chat from the game
     * @param s nickname of the player requesting the chat
     * @param command the command to get the chat
     * @throws RemoteException
     */
    @Override
    public void getChat(String s, String command) throws RemoteException {
        try {
            clientHandlers.get(s).getMessAd().parserMessage(clientHandlers.get(s), command);
        } catch (IOException | ParseException e) {
            System.out.println("Parse error");
        }
    }

    /**
     * It is used to close the handler
     * @param s nickname of the player
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
     * It is used to receive and handle the Pong message
     * @param client remote client that has sent the Pong message
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
     * It is used to initialize the RMI connection between the remote client and its handler
     * @param client remote client
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
     * It sets the lobby
     * @param lobby the lobby
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
     * It returns the handlers of each player mapped to the nickname of the player
     * @return the attribute clientHandlers
     */
    public Map<String, RMIClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    /**
     * It suggests some nicknames to the player because the desired nickname is already in use
     * @param nickname desired nickname
     * @param client remote client
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
                System.out.println("Client not reachable");
            }
        }
    }

    /**
     * It returns the handlers of the players
     * @return the attribute rmiHandlers
     */
    public ArrayList<RMIClientHandler> getRmiHandlers() {
        return rmiHandlers;
    }
}
