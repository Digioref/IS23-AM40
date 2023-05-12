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


public class ClientHandler extends Handlers implements Runnable {
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private boolean stop;
    private GameServer gameServer;


    public ClientHandler(Socket socket, GameServer gameServer) {
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

    public void sendMessage(String s) throws IOException {
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
            messAd.configure();
            messAd.startMessage(this);
// Leggo e scrivo nella connessione finche' non ricevo "quit"
            while (!stop) {
//                out.println("Give nickname: ");
//                out.flush();
//
//                out.println("Give command: ");
//                out.flush();
                sendMessage(JSONConverterStoC.normalMessage("Type your command here: "));
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

    public void suggestNickname(String nickname) {
        Random random = new Random();
        for (int i = 0; i < NSUGGEST; i++) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            int z = random.nextInt(10);
            try {
                sendMessage(JSONConverterStoC.normalMessage(nickname + x + y + z));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
        //TODO impact on game
        try {
            sendMessage(JSONConverterStoC.normalMessage("Quit"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stop = true;
        in.close();
        out.close();
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gameServer.shutdownHandler(this);
    }
}
