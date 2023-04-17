package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Controller.Controller;
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


public class ClientHandler implements Runnable {
    private static final int NSUGGEST = 8;
    private String nickname;
    private Socket socket;
    private Controller controller;
    private VirtualView virtualView;
    private int numPlayers;
    private boolean logged;
    private MessageAdapter messAd;
    private Lobby lobby;
    private LoggingPhase logphase;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        logged = false;
        messAd = new MessageAdapter();
        lobby = new Lobby();
        setLogphase(LoggingPhase.LOGGING);
    }

    public void sendMessage(String s) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(s);
        out.flush();
    }
    public void receiveMessage() {}

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

    public MessageAdapter getMessAd() {
        return messAd;
    }

    @Override
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
//            PrintWriter out = new PrintWriter(socket.getOutputStream());
            messAd.configure();
            messAd.startMessage(this);
// Leggo e scrivo nella connessione finche' non ricevo "quit"
            while (true) {
//                out.println("Give nickname: ");
//                out.flush();
//
//                out.println("Give command: ");
//                out.flush();
                sendMessage(JSONConverterStoC.normalMessage("Type your command here: "));
                String line = in.nextLine();
                messAd.parserMessage(this, line);
                if (line.equals("quit")) {
                    break;
                }
//                else {
//                    out.println("Received: " + line);
//                    out.flush();
//                }

            }
// Chiudo gli stream e il socket
            in.close();
//            out.close();
            System.out.println("ClientHandler " + nickname + " closed!");
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public boolean isLogged() {
        return logged;
    }

    public Socket getSocket() {
        return socket;
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

    public void suggestNickname(String nickname) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        Random random = new Random();
        for (int i = 0; i < NSUGGEST; i++) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            int z = random.nextInt(10);
            sendMessage(JSONConverterStoC.normalMessage(nickname + x + y + z));
        }
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
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

    public void setVirtualView(VirtualView v) {
        this.virtualView = v;
    }


    public void setLogphase(LoggingPhase logphase) {
        this.logphase = logphase;
    }

    public LoggingPhase getLogphase() {
        return logphase;
    }

}
