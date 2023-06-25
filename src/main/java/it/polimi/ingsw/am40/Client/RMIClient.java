package it.polimi.ingsw.am40.Client;

import it.polimi.ingsw.am40.CLI.CliView;
import it.polimi.ingsw.am40.CLI.Colors;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterCtoS;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.RMI.RMIServerInterface;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class representing the client. It uses the RMI connection
 */
public class RMIClient extends Client implements RMIClientInterface {
    private int WAIT_PING_2 = 6000;
    private BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    private RMIServerInterface stub;
    private Thread rmiThread;
    private boolean stop;
    private String Ipaddress;
    private Colors color;
    private boolean quitchat;
    private ScheduledExecutorService sendPing;

    private Queue<String> message;


    /**
     * Constructor that initializes the features
     * @param serverIp ip address of the server
     * @throws RemoteException
     */
    public RMIClient(String serverIp) throws RemoteException {
        super();
        stop = false;
        Ipaddress = serverIp;
        inChat = false;
        state = new ClientState(this);
        color = new Colors();
        message = new ArrayDeque<>();
    }

    /**
     * It connects the client to the server
     */
    public void connect() {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(Ipaddress,5000);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            stub = (RMIServerInterface) registry.lookup("RMIRegistry");
            stub.init(this);

        } catch (RemoteException | NotBoundException e) {
            System.out.println("Server not reachable. Closing...");
            close();
            return;
//            throw new RuntimeException(e);
        }
        startPing();
        startParsing();
        if (LaunchClient.getView() instanceof CliView) {
            rmiThread = new Thread(() -> {
                do {
                    try {
                        String line;
                        if (stdIn.ready()) {
                            try {
                                line = stdIn.readLine();
                                if (line.equals("chat")) {
                                    startChat();
                                } else {
                                    sendMessage(line);
                                }
                            } catch (IOException e) {
                                close();
                                break;
//                                throw new RuntimeException();
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } while(!stop);
            });
            rmiThread.start();
        }

    }

    /**
     * It sends the message to the server calling remotely a method on the server
     * @param s the message to be sent
     */
    @Override
    public void sendMessage(String s) {
        String[] command = s.split("\\s");
        JSONConverterCtoS jconv = new JSONConverterCtoS();
        jconv.toJSON(s);
        switch (command[0]) {
            case "login":
                String s1 = "";
                try {
                    for (int i = 1; i < command.length; i++) {
                        if (s1.equals("")) {
                            s1 = command[i];
                        } else {
                            s1 = s1 + " " + command[i];
                        }
                    }
                    stub.login(s1, this);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "setplayers":
                try {
                    stub.setPlayers(nickname, jconv.toString());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "help":
                try {
                    stub.help(this, jconv.toString());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "getboard", "getbook", "getbookall", "getcommgoals", "getcurrent", "getcurscore", "gethiddenscore", "getpersgoal", "getplayers":
                try {
                    stub.gameInfoRequest(nickname, jconv.toString());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "insert", "order", "pick", "remove", "select":
                if (command[0].equals("insert")) {
                    state.setPickedtiles(null);
                    state.setSelectedtiles(null);
                }
                try {
                    stub.gameUpdate(nickname, jconv.toString());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "viewchat":
                try {
                    stub.getChat(nickname, jconv.toString());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "quit":
                try {
                    if (nickname != null) {
                        stub.close(nickname);
                    }
                    close();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                try {
                    receive(JSONConverterStoC.normalMessage("Your command is wrong, please retype:"));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    /**
     * It starts the chat
     */
    public void startChat() {
        quitchat = false;
        inChat = true;
        LaunchClient.getView().printMessage(color.cyanBg() + " You are in the Chat!" + color.rst() + "\nWrite the message(-q to quit): ");
        while (!quitchat) {
            try {
                String line = null;
                if (stdIn.ready()) {
                    line = stdIn.readLine();
                    if (line.toLowerCase().equals("-q")) {
                        quitchat = true;
                    } else {
                        LaunchClient.getView().printMessage("to [playerName] (leave it blank if it is a broadcast message): ");
                        String receiver = stdIn.readLine();
                        if (receiver.length() == 0)
                            receiver = null;
                        JSONConverterCtoS jconv = new JSONConverterCtoS();
                        jconv.toJSONChat(receiver, line);
                        chat(jconv.toString());
                        LaunchClient.getView().printMessage(color.cyanBg() + " You are in the Chat!" + color.rst() + "\nWrite the message(-q to quit): ");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        inChat =false;
        state.refresh();
    }

    /**
     * Method used to close the corresponding client handler.
     */
    @Override
    public void close() {
        if (ping != null) {
            ping.shutdownNow();
            parse.interrupt();
        }
        stop = true;
        if (rmiThread != null) {
            rmiThread.interrupt();
        }
        if (LaunchClient.getView() != null) {
            LaunchClient.getView().quit(nickname);
        }
        quitchat = true;
        try {
            stdIn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }

    /**
     * Method used to send the Pong message, answering a Ping message received by the Server.
     */
    @Override
    public void sendPong() {
        try {
            stub.receivePong(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method used to start a timer waiting for a Ping message from the Server.
     */
    @Override
    public void startPing() {
        Runnable task = () -> {
            numPing++;
            if (numPing == NUMPINGLOST) {
                close();
            }
        };
        ping = Executors.newSingleThreadScheduledExecutor();
        ping.scheduleAtFixedRate(task, WAIT_PING_2, WAIT_PING_2, TimeUnit.MILLISECONDS);
    }

    /**
     * It creates a thread that extracts the messages in the queue one by one and parses them
     */
    private void startParsing(){
        parse= new Thread( ()-> {
            do{
                synchronized (message){
                    if(!message.isEmpty()){
                        try {
                            parseMessage(message.poll());
                        } catch (ParseException e) {
                            System.out.println("Error in parsing");
                            break;
                        }
                    }
                }
            }while (!stop);
        });
        parse.setName("PARSING MESSAGE");
        parse.start();
    }


    /**
     * It receives a message from the server
     * @param s message received
     * @throws RemoteException
     */
    @Override
    public void receive(String s) throws RemoteException {
        message.add(s);
        /*
        try {
            parseMessage(s);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        */
    }


    /**
     * It receives the nickname of the player
     * @param s nickname of the player
     * @throws RemoteException
     */
    @Override
    public void receiveNickname(String s) throws RemoteException {
        try {
            parseMessage(s);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * It receives the chat
     * @param s string which contains the chat, it's a JSON string
     * @throws RemoteException
     */
    @Override
    public void receiveChat(String s) throws RemoteException {
        JSONParser jsonParser = new JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) jsonParser.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray arr13 = (JSONArray) object.get("Authors");
        JSONArray arr14 = (JSONArray) object.get("Receivers");
        JSONArray arr15 = (JSONArray) object.get("Messages");
        ArrayList<String> array1 = new ArrayList<>(arr13);
        ArrayList<String> array2 = new ArrayList<>(arr14);
        ArrayList<String> array3 = new ArrayList<>(arr15);
        LaunchClient.getView().showChat(array1, array2, array3, nickname);
    }

    /**
     * It sends a chat message to the server
     * @param command chat message to be sent
     */
    public void chat(String command) {
        try {
            stub.chat(nickname, command);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * It returns the nickname of the player
     * @return the attribute nickname
     */
    public String getNickname() {
        return nickname;
    }
}
