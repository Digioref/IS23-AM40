package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Client.RMIClientInterface;
import it.polimi.ingsw.am40.Controller.Controller;
import it.polimi.ingsw.am40.Controller.Lobby;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.Position;
import it.polimi.ingsw.am40.Network.RMI.RMIServer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * It's the handler of the player using the RMI communication
 */
public class RMIClientHandler extends Handlers {
    private RMIServer server;
    private RMIClientInterface rmiClient;
    private ScheduledExecutorService sendPing;
    private ScheduledExecutorService waitPing;
    private final static int WAIT_PING = 10000;
    private final static int SEND_PING = 4000;
    private final static int NUMLOST = 3;

    /**
     * Constructor that creates the handler initializing the features of the class
     * @param server the RMI server
     */
    public RMIClientHandler(RMIServer server) {
        this.server = server;
        logged = false;
        lobby = new Lobby();
        setLogphase(LoggingPhase.LOGGING);
//        commands = new ArrayList<>();
//        ParsingJSONManager.commands(commands);
        messAd= new MessageAdapter();
        messAd.configure();
        nPingLost = 0;
        disconnected = false;
        startPing();
    }


    private void startPing() {
        if(sendPing != null)
            sendPing.shutdownNow();
        if(waitPing != null)
            waitPing.shutdownNow();
        waitPing = Executors.newSingleThreadScheduledExecutor();
        sendPing = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            ping();
        };
        sendPing.scheduleAtFixedRate(task, 0, SEND_PING, TimeUnit.MILLISECONDS);
    }


    private void ping() {
        sendMessage(JSONConverterStoC.createJSONPing());
        Runnable task = () -> {
            nPingLost++;
            if(nPingLost >= NUMLOST){
                if (!disconnected) {
                    disconnected = true;
                    System.out.println("Client disconnected for having lost " + NUMLOST +  " PING!");
                    close();
                }
            }
        };
        synchronized (this){
            waitPing = Executors.newScheduledThreadPool(1);
            waitPing.schedule(task,WAIT_PING,TimeUnit.MILLISECONDS);
        }
    }

    /**
     * It sends a message to the client calling a method on the client using the Remote interface of the client
     * @param s message to be sent
     */
    @Override
    public void sendMessage(String s) {
        try {
            rmiClient.receive(s);
        } catch (RemoteException e) {
            System.out.println("Client not reachable... Disconnecting it...");
            close();
//            throw new RuntimeException(e);
        }
    }

    /**
     * Suggests some nicknames to the client
     * @param s desired nickname of the player
     */
    @Override
    public void suggestNickname(String s) {
        Random random = new Random();
        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < NSUGGEST; i++) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            int z = random.nextInt(10);
            arr.add(nickname + x + y + z);
        }
        try {
            rmiClient.receive(JSONConverterStoC.createJSONNicknames(arr));
        } catch (RemoteException e) {
            System.out.println("Client not reachable");
            close();
        }
    }

    /**
     * It executes the game command by calling the corresponding method of the game controller
     * @param at the action to be performed on the game
     * @param arr parameters necessary to perform the action; they are integers
     */
    @Override
    public void executeCommand(ActionType at, ArrayList<Integer> arr) {
        if (logphase.equals(LoggingPhase.INGAME)) {
            switch(at) {
                case SELECT:
                    Position p = new Position(arr.get(0), arr.get(1));
                    controller.getGameController().selectTile(virtualView, p);
                    break;
                case REMOVE:
                    controller.getGameController().notConfirmSelection(virtualView);
                    break;
                case PICK:
                    controller.getGameController().pickTiles(virtualView);
                    break;
                case ORDER:
                    controller.getGameController().order(virtualView, arr);
                    break;
                case INSERT:
                    controller.getGameController().insert(virtualView, arr.get(0));
                    break;

            }
        } else {
            sendMessage(JSONConverterStoC.createJSONError("You are not playing in any game yet!"));
        }
    }

    /**
     * It adds to the game chat a new message
     * @param message the message
     * @param name the receiver of the message
     */
    @Override
    public void chat(String message, String name) {
        controller.getGameController().chat(name, message, nickname);
    }

    /**
     * It allows the player to get the chat of the game
     */
    @Override
    public void getChat() {
        controller.getGameController().getChat(nickname);
    }

    /**
     * It sends a chat message
     * @param s the message to be sent
     */
    @Override
    public void sendChat(String s) {
        try {
            rmiClient.receiveChat(s);
        } catch (RemoteException e) {
            System.out.println("Client not reachable");
            close();
        }
    }

    /**
     * It closes the handler, stopping the ping pong
     */
    @Override
    public void close() {
        rmiClient = null;
        if (virtualView != null) {
            virtualView.setClientHandler(null);
        }
        waitPing.shutdownNow();
        sendPing.shutdownNow();
        if (nickname == null && server.getRmiHandlers().contains(this)) {
            server.getRmiHandlers().remove(this);
            System.out.println("Client closed!");
        }
        if (nickname != null && server.getClientHandlers().containsKey(nickname)) {
            lobby.removeQuit(this);
            server.getClientHandlers().remove(nickname);
            System.out.println("Client " + nickname + " closed!");
            if (lobby.getGames().containsKey(nickname)) {
                lobby.getGames().get(nickname).disconnectPlayer(nickname);
            }
        }
    }

    /**
     * It handles the Pong message received from the client
     */
    @Override
    public void handlePong() {
        waitPing.shutdownNow();
        nPingLost = 0;
        waitPing = Executors.newScheduledThreadPool(1);
//        startPing();
    }

    /**
     * It closes the handler because the game has ended
     */
    @Override
    public void closeGame() {
        close();
        controller = null;
        virtualView = null;
        lobby.closeGame(this);
    }

    /**
     * Sets the attribute rmiClient to the parameter passed
     * @param rmiClient the client using rmi communication
     */
    public void setRmiClient(RMIClientInterface rmiClient) {
        this.rmiClient = rmiClient;
    }

    /**
     * It returns the client using rmi communication
     * @return the attribute rmiClient
     */
    public RMIClientInterface getRmiClient() {
        return rmiClient;
    }
}
