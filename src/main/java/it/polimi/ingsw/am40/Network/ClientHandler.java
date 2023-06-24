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
    }

    public synchronized void sendMessage(String s) throws IOException {
        out.println(s);
        out.flush();
    }



    public MessageAdapter getMessAd() {
        return messAd;
    }

    @Override
    public void sendChat(String s) {
        try {
            sendMessage(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
                System.out.println("Client disconnected for having lost " + NUMLOST +  " PING!");
                close();
            }
        };
        synchronized (this){
            waitPing = Executors.newScheduledThreadPool(1);
            waitPing.schedule(task,WAIT_PING,TimeUnit.MILLISECONDS);
        }
    }

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

    @Override
    public void chat(String message, String name) {
        controller.getGameController().chat(name, message, nickname);
    }

    @Override
    public void getChat() {
        controller.getGameController().getChat(nickname);
    }

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

    @Override
    public synchronized void handlePong() {
        waitPing.shutdownNow();
        nPingLost = 0;
    }

    @Override
    public void closeGame() {
        close();
        virtualView = null;
        controller = null;
        lobby.closeGame(this);
    }
}
