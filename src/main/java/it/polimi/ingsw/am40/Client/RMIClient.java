package it.polimi.ingsw.am40.Client;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterCtoS;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.RMI.RMIServerInterface;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class RMIClient implements RMIClientInterface {
    private BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    private RMIServerInterface stub;
    private Thread rmiThread;
    private boolean stop;
    private String nickname;
    private String Ipaddress;

    public RMIClient(String serverIp) throws RemoteException {
        super();
        stop = false;
        Ipaddress = serverIp;
    }

    public void connect() {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(Ipaddress,5000);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            stub = (RMIServerInterface) registry.lookup("RMIRegistry");

        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
        rmiThread = new Thread(() -> {
            do {
                String line;
                try {
                    line = stdIn.readLine();
                    if (line.equals("chat")) {
                        boolean quit = false;
                        while (!quit) {
                            LaunchClient.getView().printMessage("You are in the Chat!\nWrite the message: ");
                            try {
                                line = stdIn.readLine();
                                if (line.toLowerCase().equals("q")) {
                                    quit = true;
                                } else {
                                    LaunchClient.getView().printMessage("to [playerName] (leave it blank if it is a broadcast message): ");
                                    String receiver = stdIn.readLine();
                                    if (receiver.length() == 0)
                                        receiver = null;
                                    JSONConverterCtoS jconv = new JSONConverterCtoS();
                                    jconv.toJSONChat(receiver, line);
                                    chat(jconv.toString());
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else {
                        parseMessage(line);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } while(!stop);
        });
        rmiThread.start();
    }

    public void parseMessage(String line) {
        String[] command = line.split("\\s");
        JSONConverterCtoS jconv = new JSONConverterCtoS();
        jconv.toJSON(line);
        switch (command[0]) {
            case "login":
                try {
//                    nickname = command[1];
                    stub.login(command[1], this);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                };
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
            default:
                try {
                    receive(JSONConverterStoC.normalMessage("Your command is wrong, please retype:"));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }
    @Override
    public void receive(String s) throws RemoteException {
        try {
            SocketClient.parseMessage(s);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receiveNickname(String s) throws RemoteException {
        JSONParser jsonParser = new JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) jsonParser.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        nickname = (String) object.get("Nickname");
    }

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

    public void chat(String command) {
        try {
            stub.chat(nickname, command);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedReader getStdIn() {
        return stdIn;
    }

    public String getNickname() {
        return nickname;
    }
}
