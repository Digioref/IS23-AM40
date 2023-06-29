package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Controller.Lobby;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.Position;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>It represents the server side handler of the player; it uses the socket communication.</p>
 * <p>It handles the communication with the client, both incoming and outcoming</p>
 */
public class ClientHandler extends Handlers implements Runnable {
    private final static int WAIT_PING = 10000;
    private final static int SEND_PING = 4000;
    private final static int NUMLOST = 3;
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private boolean stop;
    private GameServer gameServer;
    private ScheduledExecutorService sendPing;
    private ScheduledExecutorService waitPing;

    /**
     * The constructor that takes as parameters the socket used for communication and the server of the game
     * @param socket socket used to communicate with the client
     * @param gameServer the server of the game
     */
    public ClientHandler(Socket socket, GameServer gameServer) {
        nPingLost = 0;
        stop = false;
        this.gameServer = gameServer;
        this.socket = socket;
        logged = false;
        messAd = new MessageAdapter();
        lobby = new Lobby();
        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setLogphase(LoggingPhase.LOGGING);
        disconnected = false;
    }

    /**
     * It sends the message to the client
     * @param s the string (as a JSON string) that has to be sent
     * @throws IOException
     */
    public synchronized void sendMessage(String s) throws IOException {
        out.println(s);
        out.flush();
    }


    /**
     * It returns the message adapter, which takes the incoming message and parses it
     * @return the message adapter
     */
    public MessageAdapter getMessAd() {
        return messAd;
    }

    /**
     * It sends a chat message
     * @param s the message to be sent
     */
    @Override
    public void sendChat(String s) {
        try {
            sendMessage(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The run method because the ClientHandler is a runnable, so this method starts the handler
     */
    @Override
    public void run() {
        try {
            startPing();
            messAd.configure();
            messAd.startMessage(this);
// Leggo e scrivo nella connessione finche' non ricevo "quit"
            while (!stop) {
//                out.println("Give nickname: ");
//                out.flush();
//
//                out.println("Give command: ");
//                out.flush();
//                sendMessage(JSONConverterStoC.normalMessage("Type your command here: "));
                String line = in.nextLine();
                messAd.parserMessage(this, line);
//                if (line.equals("quit")) {
//                    break;
//                }
//                else {
//                    out.println("Received: " + line);
//                    out.flush();
//                }

            }
// Chiudo gli stream e il socket
//            in.close();
////            out.close();
//            System.out.println("ClientHandler " + nickname + " closed!");
//            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void startPing() {
        if(sendPing != null)
            sendPing.shutdownNow();
        if(waitPing != null)
            waitPing.shutdownNow();
        sendPing = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            ping();
        };
        sendPing.scheduleAtFixedRate(task, 0, SEND_PING, TimeUnit.MILLISECONDS);
    }


    private void ping() {
        try {
            sendMessage(JSONConverterStoC.createJSONPing());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Runnable task = () -> {
            nPingLost++;
            if (nPingLost >= NUMLOST) {
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
     * It sends to the client some suggested nicknames, created by joining the nickname chosen by the user with some numbers
     * @param nickname nickname desired by the user
     */
    public void suggestNickname(String nickname) {
        Random random = new Random();
        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < NSUGGEST; i++) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            int z = random.nextInt(10);
            arr.add(nickname + x + y + z);
        }
        try {
            sendMessage(JSONConverterStoC.createJSONNicknames(arr));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * It executes the game command by calling the corresponding method of the game controller
     * @param at the action to be performed on the game
     * @param arr parameters necessary to perform the action; they are integers
     */
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
            try {
                sendMessage(JSONConverterStoC.normalMessage("You are not playing in any game yet!"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
     * It closes the handler, closing the socket and stopping the ping pong
     */
    public void close() {
        nPingLost = 0;
        waitPing.shutdown();
        sendPing.shutdown();
        if (virtualView != null) {
            virtualView.setClientHandler(null);
        }
        if (controller != null) {
            controller.getGameController().disconnectPlayer(nickname);
        }
        lobby.removeQuit(this);
//        try {
//            sendMessage(JSONConverterStoC.normalMessage("Quit"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        stop = true;
        gameServer.shutdownHandler(this);
//        in.close();
//        out.close();
//        try {
//            socket.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    /**
     * Default constructor
     */
    public ClientHandler(){
    }

    /**
     * It handles the Pong message received from the client
     */
    @Override
    public synchronized void handlePong() {
        waitPing.shutdownNow();
        nPingLost = 0;
    }

    /**
     * It closes the handler because the game has ended
     */
    @Override
    public void closeGame() {
        close();
        virtualView = null;
        controller = null;
        lobby.closeGame(this);
    }

    /**
     * Sets the attribute out to the parameter passed
     * @param out a printwriter used to send messages to the client
     */
    public void setOut(PrintWriter out){
        this.out = out;
    }


}
